#!/bin/bash

usage() {
  cat <<EOF
usage: $0 options

OPTIONS:
   -s      Skip application build
EOF
}

## Assuming that the private build has run before them all the images already exist for this
## Using -s will not use docker images based on infrastructure/Dockerfile
skip_build=0
while getopts ":s" OPTION; do
  case $OPTION in
  s)
    skip_build=1
    echo "Skipping application build"
    ;;
  ?)
    usage
    exit
    ;;
  esac
done

if [[ -z "$PROJECT_HOME" ]]; then
  echo "PROJECT_HOME environment variable is not set"
  exit 1
fi
if [[ -z "$AWS_DEFAULT_PROFILE" ]]; then
  echo "AWS_DEFAULT_PROFILE environment variable is not set"
  exit 1
fi
if [[ -z "$DOCKER_REGISTRY" ]]; then
  echo "DOCKER_REGISTRY environment variable is not set"
  exit 1
fi

if [[ "$(stat -c '%a' /var/run/docker.sock)" != "666" ]]; then
  printf "Set '/var/run/docker.sock' with write permissions for others:\n > sudo chmod o+rw /var/run/docker.sock\n"
  exit 1
fi

python3 "$PROJECT_HOME/tools/private-build/sso_credentials"
if [[ $? -eq 1 ]]; then
  exit 1
fi

aws ecr get-login-password --profile "$AWS_DEFAULT_PROFILE" | \
  docker login --username AWS --password-stdin "$DOCKER_REGISTRY"
if [[ $? -eq 1 ]]; then
  exit 1
fi

reports() {
  replacer="s/\/opt\/app\/src/$(echo "$PROJECT_HOME" | sed 's/\//\\\//g')/g"
  find "$PROJECT_HOME" -path "*/build/**/acceptanceTest/*.xml" -exec sed -i -e "$replacer" {} \;
  find "$PROJECT_HOME" -path "*/build/**/acceptanceTest/*.html" -exec sed -i -e "$replacer" {} \;

  output_file="$PROJECT_HOME/app/build/qa-tests-output.html"

  echo "<!DOCTYPE html><html><body>" > "$output_file"

  find "$PROJECT_HOME" \
    -path "*/build/reports/acceptanceTest/**/*.html" \
    -name "index.html" \
    -type f \
    -exec echo '<a href="file://{}">{}</a><br/>' \; \
    >> "$output_file"

  echo "</body></html>" >> "$output_file"

  echo "List of reports at file://$output_file"
}

stop_and_exit() {
    is_to_report=$1

    echo "ERROR"
    $is_to_report && reports
    "${PROJECT_HOME}/tools/docker/dev/stop-dockers.sh" || exit 1
    exit 1
}

DOCKER_WORKDIR=/opt/app/src

echo "### CREATING BUILDER IMAGE ###"
docker build \
  --network host \
  -t -builder:private-build \
  -f "$PROJECT_HOME/infrastructure//Dockerfile" \
  --target build .

if [[ $? -ne 0 ]]; then
  echo "ERROR"
  exit 1
fi

echo "### RUNNING APPLICATION AND DEPENDENCIES ###"
APP_OPTS="--spring.profiles.active=docker,qa-tests" \
ACL_INSTANCES="http://-mock-application-dev:1080/" \
"${PROJECT_HOME}/tools/docker/dev/start-dockers-full-environment.sh" "$([[ skip_build -eq 0 ]] && echo "" || echo "-s")"

if [[ $? -ne 0 ]]; then
  stop_and_exit false
fi

read -n 1 -s -r -p "Environment is up. Press any key to execute the tests"

## Run acceptance tests
docker run \
  --rm \
  -i \
  -v "$PROJECT_HOME":"$DOCKER_WORKDIR" \
  -v "${HOME}/.gradle":"$DOCKER_WORKDIR/.gradle" \
  -v /var/run/docker.sock:/var/run/docker.sock:rw \
  --env "PROJECT_HOME=$DOCKER_WORKDIR" \
  --env-file "$PROJECT_HOME/tools/private-build/qa-env" \
  --user="$(id -u):$(id -g)" \
  --network host \
  --name qa-tests \
  -builder:private-build \
  ./gradlew --no-daemon --info acceptanceTest
if [[ $? -ne 0 ]]; then
  stop_and_exit true
fi

docker run \
  --rm \
  -i \
  -v "$PROJECT_HOME":"$DOCKER_WORKDIR" \
  -v "${HOME}/.gradle":"$DOCKER_WORKDIR/.gradle" \
  -v /var/run/docker.sock:/var/run/docker.sock:rw \
  --env "PROJECT_HOME=$DOCKER_WORKDIR" \
  --env-file "$PROJECT_HOME/tools/private-build/qa-env" \
  --user="$(id -u):$(id -g)" \
  --network host \
  --name qa-tests \
  -builder:private-build \
  ./gradlew --no-daemon --info jacocoAcceptanceTestReport -x acceptanceTest
if [[ $? -ne 0 ]]; then
  stop_and_exit true
fi

reports

"${PROJECT_HOME}/tools/docker/dev/stop-dockers.sh" || exit 1

exit 0

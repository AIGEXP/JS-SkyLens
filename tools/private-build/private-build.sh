#!/bin/bash

BASE_BRANCH=$1
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

reports() {
  replacer="s/\/opt\/app\/src/$(echo "$PROJECT_HOME" | sed 's/\//\\\//g')/g"
  find "$PROJECT_HOME" -path "*/build/*.xml" -exec sed -i -e "$replacer" {} \;
  find "$PROJECT_HOME" -path "*/build/*.html" -exec sed -i -e "$replacer" {} \;

  output_file="$PROJECT_HOME/app/build/output.html"

  echo "<!DOCTYPE html><html><body>" > "$output_file"

  find "$PROJECT_HOME" \
    -path "*/build/reports/*.html" \
    -regex ".*\(index\|main\).html" \
    -type f \
    -exec echo '<a href="file://{}">{}</a><br/>' \; \
    >> "$output_file"

  echo "</body></html>" >> "$output_file"

  echo "List of reports at file://$output_file"
}

echo "### VALIDATING SCRIPT DEPENDENCIES ###"
array=( "docker" "git" "python3" )
for i in "${array[@]}"
do
    command -v $i >/dev/null 2>&1 || {
        echo >&2 "Cannot commit: Could not find <$i>, that is needed for validation executions. Please install it!"
        exit 1;
    }
done

echo "### VALIDATING COMMITS ###"
git fetch origin "$BASE_BRANCH"
CURRENT_BRANCH=$(git rev-parse --abbrev-ref HEAD)

git log --format="%s" "$BASE_BRANCH".."$CURRENT_BRANCH" |
while read -r LINE; do
  echo "${LINE//\\n/ }" > file.tmp

  source "$PROJECT_HOME/tools/git-hooks/commit-msg" file.tmp
  retVal=$?
  if [[ $retVal -ne 0 ]]; then
    rm -rf file.tmp
    exit 1
  fi

  rm -rf file.tmp
done
if [[ $? -eq 1 ]]; then
  exit 1
fi

rm -rf file.tmp

python3 "$PROJECT_HOME/tools/private-build/sso_credentials"
if [[ $? -eq 1 ]]; then
  exit 1
fi

aws ecr get-login-password --profile "$AWS_DEFAULT_PROFILE" | \
  docker login --username AWS --password-stdin "$DOCKER_REGISTRY"
if [[ $? -eq 1 ]]; then
  exit 1
fi

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

echo "### BUILDING APPLICATION ###"
docker run \
  --rm \
  -i \
  -v "$PROJECT_HOME":"$DOCKER_WORKDIR" \
  -v "${HOME}/.gradle":"$DOCKER_WORKDIR/.gradle" \
  --env "PROJECT_HOME=$DOCKER_WORKDIR" \
  --user="$(id -u):$(id -g)" \
  --network host \
  --name private-build \
  -builder:private-build \
  ./gradlew --no-daemon --info --parallel clean assemble
if [[ $? -ne 0 ]]; then
  echo "ERROR"
  exit 1
fi

echo "### RUNNING UT TESTS ###"
docker run \
  --rm \
  -i \
  -v "$PROJECT_HOME":"$DOCKER_WORKDIR" \
  -v "${HOME}/.gradle":"$DOCKER_WORKDIR/.gradle" \
  -v /var/run/docker.sock:/var/run/docker.sock:rw \
  --env "PROJECT_HOME=$DOCKER_WORKDIR" \
  --user="$(id -u):$(id -g)" \
  --network host \
  --name private-build \
  -builder:private-build \
  ./gradlew --no-daemon --info --parallel test -x integrationTest -x check -x jacocoTestCoverageVerification
if [[ $? -ne 0 ]]; then
  reports
  echo "ERROR"
  exit 1
fi

echo "### RUNNING IT TESTS ###"
docker run \
  --rm \
  -i \
  -v "$PROJECT_HOME":"$DOCKER_WORKDIR" \
  -v "${HOME}/.gradle":"$DOCKER_WORKDIR/.gradle" \
  -v /var/run/docker.sock:/var/run/docker.sock:rw \
  --env "PROJECT_HOME=$DOCKER_WORKDIR" \
  --user="$(id -u):$(id -g)" \
  --network host \
  --name private-build \
  -builder:private-build \
  ./gradlew --no-daemon --info integrationTest -x check -x jacocoTestCoverageVerification
if [[ $? -ne 0 ]]; then
  reports
  echo "ERROR"
  exit 1
fi

echo "### RUNNING STATIC ANALYSIS ###"
docker run \
  --rm \
  -i \
  -v "$PROJECT_HOME":"$DOCKER_WORKDIR" \
  -v "${HOME}/.gradle":"$DOCKER_WORKDIR/.gradle" \
  --env "PROJECT_HOME=$DOCKER_WORKDIR" \
  --user="$(id -u):$(id -g)" \
  --network host \
  --name private-build \
  -builder:private-build \
  ./gradlew --no-daemon --info check jacocoTestCoverageVerification jacocoTestReport -x test -x integrationTest
if [[ $? -ne 0 ]]; then
  reports
  echo "ERROR"
  exit 1
fi

echo "### BUILDING APPLICATION IMAGE ###"
docker build --network host -t :local -f "$PROJECT_HOME/infrastructure//Dockerfile" --target production .
if [[ $? -ne 0 ]]; then
  echo "ERROR"
  exit 1
fi

echo "### BUILDING POSTGRESQL-MIGRATIONS IMAGE ###"
docker build --network host -t -postgresql-migrations:local -f "$PROJECT_HOME/infrastructure//Dockerfile" --target postgresql-migrations .
if [[ $? -ne 0 ]]; then
  echo "ERROR"
  exit 1
fi

reports

exit 0

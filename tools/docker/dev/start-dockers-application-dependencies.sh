#!/usr/bin/env bash

usage() {
  cat <<EOF
usage: $0 options

OPTIONS:
   -s      Skip application build
EOF
}

skip_build=0
while getopts ":s" OPTION; do
  case $OPTION in
  s)
    skip_build=1
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

array=( "docker" "cut" )
for i in "${array[@]}"
do
    command -v $i >/dev/null 2>&1 || {
        echo >&2 "Cannot commit: Could not find <$i>, that is needed for validation executions. Please install it!"
        exit 1;
    }
done

DOCKER_COMPOSE_HOME="$PROJECT_HOME/tools/docker/dev"

cd "${DOCKER_COMPOSE_HOME}" || exit

for var1 in `cat "${DOCKER_COMPOSE_HOME}/.env" | cut -d= -f1`
do
  [[ ! -v "${var1}" ]] && export "$var1=`cat "${DOCKER_COMPOSE_HOME}/.env" | grep "$var1=" | cut -d= -f2-`"
  echo "${var1}=$(eval "echo \$$var1")"
done

if [[ skip_build -eq 0 ]]; then

  "$PROJECT_HOME/tools/docker/dev/build-docker-images.sh" || exit 1
fi

export PROJECT_NAME="${ENVIRONMENT}"

DEPENDENCY_CONTAINERS="skylens-rabbitmq skylens-zookeeper skylens-kafka skylens-postgresql-migrations"

docker compose -f "${DOCKER_COMPOSE_HOME}/compose.yaml" pull ${DEPENDENCY_CONTAINERS}
docker compose -f "${DOCKER_COMPOSE_HOME}/compose.yaml" up -d ${DEPENDENCY_CONTAINERS} skylens-mock-application

exit $?

#!/usr/bin/env bash

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

. "${DOCKER_COMPOSE_HOME}/.env"

export $(cut -d= -f1 "${DOCKER_COMPOSE_HOME}/.env")

docker compose --project-name "${ENVIRONMENT}" -f "${DOCKER_COMPOSE_HOME}/compose.yaml" stop

#!/usr/bin/env bash

if [[ -z "$PROJECT_HOME" ]]; then
  echo "PROJECT_HOME environment variable is not set"
  exit 1
fi

DOCKER_COMPOSE_HOME="$PROJECT_HOME/tools/docker/dev"

cd "${DOCKER_COMPOSE_HOME}" || exit

. "${DOCKER_COMPOSE_HOME}/.env"

export $(cut -d= -f1 "${DOCKER_COMPOSE_HOME}/.env")

docker compose --project-name "${ENVIRONMENT}" -f "${DOCKER_COMPOSE_HOME}/compose.yaml" rm -f

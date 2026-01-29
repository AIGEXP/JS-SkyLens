#!/usr/bin/env bash

if [[ -z "$PROJECT_HOME" ]]; then
  echo "PROJECT_HOME environment variable is not set"
  exit 1
fi

DOCKER_COMPOSE_HOME="$PROJECT_HOME/tools/docker/dev"

usage() {
  cat <<EOF
usage: $0 options

This script starts the necessary containers to boot the project.
It can only start dependencies or start dependencies+application

OPTIONS:
   -b      Create dependencies' containers plus application container
EOF
}

with_api=0

while getopts ":ba" OPTION; do
  case $OPTION in
  b)
    with_api=1
    ;;
  ?)
    usage
    exit
    ;;
  esac
done

if [[ with_api -eq 1 ]]; then
  echo "Application container WILL BE started"
  "${DOCKER_COMPOSE_HOME}"/start-dockers-full-environment.sh
else
  echo "Application container WON'T BE started"
  "${DOCKER_COMPOSE_HOME}"/start-dockers-application-dependencies.sh
fi

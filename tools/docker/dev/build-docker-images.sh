#!/usr/bin/env bash

if [[ -z "$PROJECT_HOME" ]]; then
  echo "PROJECT_HOME environment variable is not set"
  exit 1
fi

cd "${PROJECT_HOME}" || exit 1

bash "${PROJECT_HOME}/tools/docker/dev/check-java-version.sh" || exit 1

build_app() {

  "./gradlew" -b "build.gradle.kts" clean assemble bootJar -x compileTestJava -x test -x check -x jacocoTestCoverageVerification
}

static_analysis() {

  "./gradlew" -b "build.gradle.kts" clean -x bootJar compileTestJava -x test check jacocoTestCoverageVerification
}

test_app() {

  "./gradlew" -b "build.gradle.kts" clean -x bootJar compileTestJava -x test check jacocoTestCoverageVerification
}

prepare_images() {
  docker build -t skylens --target production --network host --force-rm -f infrastructure/skylens/Dockerfile . && \
  docker tag skylens skylens:local && \
  docker build -t skylens-postgresql-migrations --target postgresql-migrations --network host --force-rm -f infrastructure/skylens/Dockerfile . && \
  docker tag skylens-postgresql-migrations skylens-postgresql-migrations:local
}

build_app

while getopts ":at" OPTION; do
  case $OPTION in
  a)
    static_analysis
    ;;
  t)
    test_app
    ;;
  ?)
    usage
    exit
    ;;
  esac
done

prepare_images

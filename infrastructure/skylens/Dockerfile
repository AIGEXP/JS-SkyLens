# syntax=docker/dockerfile:1

ARG JAVA_BUILDER_IMAGE=089879264256.dkr.ecr.eu-west-1.amazonaws.com/jumiaservices/builder-java25:0.0.1
ARG JAVA_IMAGE=089879264256.dkr.ecr.eu-west-1.amazonaws.com/jumiaservices/base-java25:0.0.1
ARG FLYWAY_IMAGE=flyway/flyway:11-alpine

FROM ${JAVA_BUILDER_IMAGE} AS base
ENV NEXUS_URL=http://builder-01.dev.js:8083
ENV NEXUS_USERNAME=""
ENV NEXUS_PASSWORD=""
ENV NEXUS_DOWNLOAD_PATH=/repository/maven-public
ENV NEXUS_UPLOAD_RELEASE_PATH=/repository/releases
ENV NEXUS_UPLOAD_SNAPSHOT_PATH=/repository/snapshots
ENV DOCKER_REGISTRY=089879264256.dkr.ecr.eu-west-1.amazonaws.com

WORKDIR /opt/app/src
COPY . .

FROM base AS build

RUN mkdir -p /opt/app-build
RUN chmod -R 777 /opt/app-build

ENV GRADLE_USER_HOME=/opt/app/src/.gradle
ENV BUILD_LOG_LEVEL="--warn"
ENV HOME=/opt/app-build
ENV JAVA_OPTS="-Djava.util.prefs.systemRoot=/opt/app-build/.java -Djava.util.prefs.userRoot=/opt/app-build/.java/.userPrefs"
ENV PROJECT_HOME=/opt/app/src

VOLUME "/var/run/docker.sock"

SHELL ["/bin/bash", "-c"]

FROM $FLYWAY_IMAGE AS postgresql-migrations
COPY driven-adapters/infrastructure/persistence/migrations-jpa/build/libs/migrations-jpa.jar /flyway/jars/

FROM ${JAVA_IMAGE} AS service-mocks

# https://blog.phusion.nl/2015/01/20/docker-and-the-pid-1-zombie-reaping-problem/
RUN apk update && apk add dumb-init curl
RUN addgroup --system javauser && adduser -S -s /bin/false -G javauser javauser

# server.port
EXPOSE 8080

# management.server.port
EXPOSE 8081

ENV APP_OPTS=""
ENV JAVA_OPTS=""

COPY mockserver/build/libs/mockserver.jar /opt/app/bin/app.jar

USER javauser
WORKDIR /opt/app/src

CMD dumb-init java -jar $JAVA_OPTS /opt/app/bin/app.jar $APP_OPTS

FROM ${JAVA_IMAGE} AS production

# https://blog.phusion.nl/2015/01/20/docker-and-the-pid-1-zombie-reaping-problem/
RUN apk update && apk add dumb-init curl
RUN addgroup --system javauser && adduser -S -s /bin/false -G javauser javauser

# server.port
EXPOSE 8080

# management.server.port
EXPOSE 8081

ENV APP_OPTS=""
ENV JAVA_OPTS=""

COPY app/build/libs/app.jar /opt/app/bin/app.jar

USER javauser
WORKDIR /opt/app/src

CMD dumb-init java -jar $JAVA_OPTS /opt/app/bin/app.jar $APP_OPTS

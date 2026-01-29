## Requirements
To run the scripts you need to set **PROJECT_HOME** variable with your APP root path on the environment where you are invoking the scripts.

### [SSO credentials and docker login](https://confluence.jumia.com/display/AFRLMT/Kubernetes)
Check 
> Configure helm development

Follow instructions for AWS Configuration, SSO Credentials
And do a docker login (the AWS ECR URL might be different depending on the vertical):
`aws ecr get-login-password --profile 089879264256_AWS-ro-All | docker login --username AWS --password-stdin 089879264256.dkr.ecr.eu-west-1.amazonaws.com`

Base docker images URLs might need to be updated:
Search for `JAVA_IMAGE`, `JAVA_BUILDER_IMAGE`, `FLYAWAY_IMAGE` (arguments in Dockerfiles) and update it accordingly.

Example:
```shell
export PROJECT_HOME="$(pwd | sed 's/\/tools.*//')"
````

## Create all docker containers in one script

This will start:
* App api and dependencies (if requested)

It will create a docker image using the current checked out code and will start the docker containers using those images (tag `local`):
- Migrations images
  - PostgreSQL
  - ACL
- Redis
- RabbitMQ
- Zookeeper
- Kafka
- App
- Mocks App

```shell script
./build-docker-images.sh
```

```shell script
./start-dockers.sh -b
```
* `-b: create dockers with App container, otherwise only application dependencies will start`

INFO: It is possible to debug the App using a java debugger on port 5006

### Create dockers to run App
##### Start App docker containers dependencies (without App)

```shell script
./start-dockers-application-dependencies.sh
```

##### Start docker containers running App
This will start application dependencies and App

```shell script
./start-dockers-full-environment.sh
```

## Dockers' Configs
Use env file to set up your configurations. This will configure docker-compose.

```shell script
ENVIRONMENT=dev
REDIS_PORT=6379
DATABASE_PORT=5432
JAVA_OPTS=-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005
APP_OPTS=--spring.profiles.active=docker
APP_API_PORT=8080
APP_MANAGEMENT_PORT=8081
DATABASE_LOGS=none
APP_BACKEND_HOST=http://localhost:8080/api/
MOCKS_PORT=8083
ACL_INSTANCES=https://api-acl-staging.jumia.services
ACL_USERNAMES=acl.jenkins
ACL_PASSWORDS=
INTERNAL_KAFKA_BROKER_PORT=9094
EXTERNAL_KAFKA_BROKER_PORT=9092
ZOOKEEPER_PORT=2181
```

The Application configurations by default in docker can be found at:
`app/docker/dev/application-docker.yml` 

If you change any configurations at `.env` file when creating the dockers,
then you must update the configurations file.

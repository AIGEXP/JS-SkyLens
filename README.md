# Java-Boot

## Create a new project from this bootstrap app
Execute `init-app/init.sh "appname"`
Take in consideration that `appname` will be used in all the packages of the app,
meaning it should respect the java package naming rules.

### Cloning the repository

```bash
# Generate a ssh key with this command and add it in your jumia github account
ssh-keygen -t rsa -b 4096

# Clone the repository
git clone git@github.com:Jumia/Java-Boot.git
cd Java-Boot
```

## Service Documentation

[Confluence Documentation](https://confluence.jumia.com)

## SOX

Are all the core of this repository is relevant for SOX?
TODO

## Building and running the application (for Ubuntu 22.04)

### Installing some dependencies
Start by updating the list of packages available:
```bash
sudo apt update
```
* Git
  * ```bash
    sudo apt install git
    ```
* cURL
  * ```bash
      sudo apt install curl
      ```
* Docker engine 
  * Check official installation guide for [Docker Engine on Ubuntu](https://docs.docker.com/engine/install/ubuntu/#install-using-the-repository) and install:
    * docker-ce 
    * [docker-compose](https://docs.docker.com/compose/install/standalone/)

    #### Login on docker (access to the private network is required)
    [Install aws-cli v2](https://docs.aws.amazon.com/cli/latest/userguide/getting-started-install.html#getting-started-install-instructions)
    ```bash
    curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip"
    unzip awscliv2.zip
    sudo ./aws/install
    ````
    [Setup AWS cli configs](https://confluence.jumia.com/display/AFRLMT/Kubernetes?preview=/138386089/138386119/pSRE.pdf)
    ```bash
    aws ecr get-login-password --profile 089879264256_AWS-ro-All | docker login --username AWS --password-stdin 089879264256.dkr.ecr.eu-west-1.amazonaws.com
    ```
    After it, credentials will be defined at: `~/.docker/config.json` and will have a ttl.
* SDKMAN
    > In order to manage dependencies for this project and being able to quickly change dependency versions we assume _SDKMAN_ as a requisite.
    > *SDKMAN* is available at https://sdkman.io
* java
  #### Corretto JVM
        
    This project uses `Java 21` with the recommended JDK being `21.0.2-amzn`.
        
    As mentioned above, SDKMAN will be used to switch between JDK versions.
        
    To list current Java version and other available versions
        
    ```sh
    sdk list java 21
    ```
        
    To install openjdk-11
        
    ```sh
    sdk install java 21.0.2-amzn (check the correct version name with sdk list java)
    ```
        
    To switch JDK versions for the current shell.
        
    ```sh
    sdk use java <version>
    ```
        
    Or to make a Java version the default version for all shells opened after.
        
    ```shell
    sdk default java <version>
    ```

    *SDKMAN* JDKs are installed in *$SDKMAN_DIR/candidates/java*. **Remember to set these in your choice IDE.**
  * gradle
    ### Gradle
    
    Gradle 8.6 or higher is necessary.
    _SDKMAN_ can also be used to manage this dependency.
    
    List available gradle versions
    
    ```sh
    sdk ls gradle
    ```
    
    Installing, **for example**, Gradle 8.6
    
    ```sh
    sdk install gradle 8.6
    ```
    
    Choose yes when asked if it should be set as default. Otherwise, set the installed version as default using the command
    
    ```sh
    sdk default gradle 8.6
    ```

### Nexus repository

Nexus repository is the internal Jumia services repository for dependencies and common artifacts. The project expects some gradle properties
to be available. Copy and adapt the following to `~/.gradle/gradle.properties` (`gradle.properties` file in the project root):

```shell
echo \
"
NEXUS_URL=http://builder-01.dev.js:8083
NEXUS_DOWNLOAD_PATH=/repository/maven-public
NEXUS_UPLOAD_RELEASE_PATH=/repository/releases
NEXUS_UPLOAD_SNAPSHOT_PATH=/repository/snapshots
NEXUS_USERNAME=<username>
NEXUS_PASSWORD=<password>
DOCKER_REGISTRY=089879264256.dkr.ecr.eu-west-1.amazonaws.com
PROJECT_HOME=\"$(pwd)\"

org.gradle.jvmargs=
project.version = '1.0.0'" \
| tee -a gradle.properties
```

or/and
```shell
echo \
"NEXUS_URL=http://builder-01.dev.js:8083
NEXUS_DOWNLOAD_PATH=/repository/maven-public
NEXUS_UPLOAD_RELEASE_PATH=/repository/releases
NEXUS_UPLOAD_SNAPSHOT_PATH=/repository/snapshots
NEXUS_USERNAME=<username>
NEXUS_PASSWORD=<password>
DOCKER_REGISTRY=089879264256.dkr.ecr.eu-west-1.amazonaws.com
PROJECT_BACKEND_URL=http://localhost:8080
PROJECT_MANAGEMENT_URL=http://localhost:8081
PROJECT_MOCKAPP_URL=http://localhost:8087"  \
| sudo tee -a .env
```

Check the up-to-date credentials in the [Confluence documentation](https://confluence.jumia.com/display/AFREXP/Maven+Nexus+Setup)
Check the instructions on how to setup docker registry for Jumia Services
Ireland [Confluence documentation](https://confluence.jumia.com/display/AFREXP/Nexus+Docker+Private+Registry)

----

## How to build

This project uses gradle as a build tool.

### Build & Test

To compile, test and package the project, simply run (in the project root)

```sh
./gradlew build
```

Since this also runs integration tests, they may fail since they require certain dependencies to be up, these are set up in
the [Private build section](#Private-build).

### Build with a version

```sh
./gradlew build -Pversion=1.0.0
```

## Developing on IntelliJ IDEA

If you want to run the app in IntelliJ you need to edit the Application configuration to have active profile: local, and set `PROJECT_HOME`
environment variable.

You should also configure checkstyle and code-style as [described here](https://confluence.jumia.com/display/AFRLMT/JSRMA+-+Code+Style) in
order to match our coding standards.

## Testing

Testing app
`./gradlew test`

A list of gradle tasks can be seen with
`./gradlew tasks`
----

## Private build

The dockers to run (and develop) the application can be found at:
`tools/docker/dev`

### Requirements

#### Environment variables

The scripts need `PROJECT_HOME` variable to be set. This is where the code is stored.

You can add this manually to the initialization script for your shell (`~/.bashrc` for a bash shell).
Or `export PROJECT_HOME=$(pwd)` in the root of the project.

```shell
export PROJECT_HOME=/path/to/repo
```

Reload the shell (reopen it) and test the new environment variable.

```shell
echo PROJECT_HOME
/path/to/repo
```
### Start dockers for development (only dependencies)

`./tools/docker/dev/start-dockers-application-dependencies.sh`

**Note** If there's a problem regarding docker registry please
follow [this page](https://confluence.jumia.com/display/AFREXP/Nexus+Docker+Private+Registry)

And to stop run
```bash
./tools/docker/dev/stop-dockers.sh
```

### Start full docker environment (application + dependencies)

```bash
./tools/docker/dev/start-dockers-full-environment.sh
```

And to stop run
```bash
./tools/docker/dev/stop-dockers-app.sh
```

### Executing docker containers necessary to run the project
```bash
# Set the JAVA_HOME environment variable to point to the java installed with sdk man
echo "JAVA_HOME=$HOME/.sdkman/candidates/java/current" | sudo tee -a /etc/environment

# Run the script
# Set the PROJECT_HOME environment variable if not set (replace the username and App address)
sudo PROJECT_HOME="$(pwd)/Java-Boot" ./tools/docker/dev/start-dockers-application-dependencies.sh
```

### Running the application

```bash
# Set this environment variable to false to disable the authentication 
echo 'ACL_SERVICE_ENABLE=false' | sudo tee -a /etc/environment

# Reload the environment variables (if this does not work restart the computer)
source /etc/environment

# Run the application
./gradlew bootRun
```

With the application running swagger can be accessed through this link

```
http://localhost:8080/api/documentation/swagger-ui.html
```

## Local Database

```
databases: -database-dev
port: 5432
username: postgres
password: postgres
```

## Flyway Migrations
https://documentation.red-gate.com/fd/migrations-184127470.html#the-createschemas-option-and-the-schema-history-table


# Example of Rest Controllers
[controllers](driver-adapters%2Fhttp-in%2Fsrc%2Fmain%2Fjava%2Fcom%2Fjumia%2F%2Fhttp%2Fin%2Fcontrollers)

## Auth/Authz tokens to use on these APIs
These tokens and users are mocked in [initializations](tools%2Fdocker%2Fdev%2Fmockserver%2Finitializations)

### Internal Admin
User: internal.admin@gmail.com 
Has Admin permission - check [acl-expectations.json](tools%2Fdocker%2Fdev%2Fmockserver%2Finitializations%2Facl-expectations.json)
```shell
TOKEN="Bearer eyJhbGciOiJIUzI1NiJ9.eyJnb29nbGVJZCI6IjExMTExOTQ0MjgwMTYyNjQ3NDk0NyIsInN1YiI6InRlc3QiLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODMiLCJuYW1lIjoiVXNlciBOYW1lIiwiZXhwIjoxNzIzNTc5MTAzMCwidHlwZSI6IkxPR0lOIiwiZW1haWwiOiJpbnRlcm5hbC5hZG1pbkBnbWFpbC5jb20iLCJwaWN0dXJlIjoiaHR0cHM6Ly9saDMuZ29vZ2xldXNlcmNvbnRlbnQuY29tL2EtL0FMVi1ValV6a0ZQN0RfYnAwNzhFOXFtZUR4a1NxMTF5c3RrYkdRbVdfVmprR1dKNl9nb19Ddy12PXM5Ni1jIiwic2lkIjoiZjhmMTlkMTItODcyNS00M2RiLThhNmEtMmQzYjlmZDM1MTJlIiwidXNlcm5hbWUiOiJ1c2VybmFtZSJ9.0CTiSlPDCxKsb9omi6S7CkANfZVXCsTX6TIKFYCIcCY"
```
### External user
User: external.user@gmail.com
Has dummy permissions - check [acl-expectations.json](tools%2Fdocker%2Fdev%2Fmockserver%2Finitializations%2Facl-expectations.
json)
```shell
TOKEN="Bearer eyJhbGciOiJIUzI1NiJ9.eyJnb29nbGVJZCI6IjExMTExOTQ0MjgwMTYyNjQ3NDk0NyIsInN1YiI6InRlc3QiLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODMiLCJuYW1lIjoiVXNlciBOYW1lIiwiZXhwIjoxNzIzNTc5MTAzMCwidHlwZSI6IkxPR0lOIiwiZW1haWwiOiJleHRlcm5hbC51c2VyQGdtYWlsLmNvbSIsInBpY3R1cmUiOiJodHRwczovL2xoMy5nb29nbGV1c2VyY29udGVudC5jb20vYS0vQUxWLVVqVXprRlA3RF9icDA3OEU5cW1lRHhrU3ExMXlzdGtiR1FtV19WamtHV0o2X2dvX0N3LXY9czk2LWMiLCJzaWQiOiJmOGYxOWQxMi04NzI1LTQzZGItOGE2YS0yZDNiOWZkMzUxMmIiLCJ1c2VybmFtZSI6InVzZXJuYW1lIn0.Vbk1gVp8akbpXz-_HkTMymFBeNqt8MvGhIPkQC9UUN8"
```
##  `GET /api/me`
```shell
curl -X POST http://localhost:8080/api/v1/examples \
-H "X-TenantID: NG" \
-H "Authorization: $TOKEN" \
-d '{"field": "value"}' \
-H 'Content-Type: application/json'
```

## `POST /api/v1/examples`
Executes an HTTP out call to an ACL service (it can be mocked or not)
Executes an HTTP out call to an HMT mocked (it does not exist) service

```shell
curl -X POST http://localhost:8080/api/v1/examples \
  -H "X-TenantID: NG" \
  -H "Authorization: $TOKEN" \
  -H 'Content-Type: application/json' \
  -d '{"field": "value"}'
```

## `GET /api/v1/examples`
Executes an HTTP out call to an ACL service (it can be mocked or not)
Applies a filter based on permissions

```shell
curl -X GET http://localhost:8080/api/v1/examples\?field=value \
  -H "X-TenantID: NG" \
  -H "Authorization: $TOKEN" \
  -H 'Content-Type: application/json'
```

## `PUT /api/v1/examples/1`
Requires ADMIN permission on skylens (executes an HTTP out call to an ACL service (it can be mocked or not))
Executes an AMQP/Kafka out message and updates a user

```shell
curl -X PUT http://localhost:8080/api/v1/examples/f8f19d12-8725-43db-8a6a-2d3b9fd3512e \
  -H "X-TenantID: NG" \
  -H "Authorization: $TOKEN" \
  -H 'Content-Type: application/json' \
  -d '{"field": "value"}'
```


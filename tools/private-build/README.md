# Private build

## Requirements
* Python 3
* AWS Cli
* Docker & docker compose
* Curl

Start by running SSO credentials - for that we will need:

### AWS Configuration
After installing [aws cli](https://docs.aws.amazon.com/cli/latest/userguide/getting-started-install.html) create a file under ~/.aws/config with the following content

[profile 089879264256_AWS-ro-All]
sso_start_url = https://d-99672be65a.awsapps.com/start
sso_region = eu-central-1
sso_account_id = 089879264256
sso_role_name = AWS-rw-All
region = eu-west-1
output = json

## SSO Credentials

pip install boto3

# Run script
## Environment variables
* PROJECT_HOME=<PROJECT PATH>
* AWS_DEFAULT_PROFILE=<AWS ~/.aws/config PROFILE>
* DOCKER_REGISTRY=<DOCKER_REGISTRY_URL>
* JUMIA_JIRA_TOKEN=<GET TOKEN FROM JIRA>

```shell
DOCKER_REGISTRY=089879264256.dkr.ecr.eu-west-1.amazonaws.com \
AWS_DEFAULT_PROFILE=089879264256_AWS-ro-All \
JUMIA_JIRA_TOKEN=<GET TOKEN FROM JIRA> \
PROJECT_HOME=$(pwd) \
bash tools/private-build/private-build.sh main
```

# Acceptance Tests
It orchestrates the creation of docker necessary containers and then application execution.
It also validates that docker images produced in the build process are in a working state.

## Process of execution
Build docker images and start up all the necessary docker containers:
* API Mocks service
  * Starts a container with it
* Application API
  * Starts a container with it using the correct profile
* Application builder (java)
* And last, executes Application qa-tests from the `app` module.

How tests can identify what are the host/port for each service?
They are defined at [qa-env](qa-env).

```shell
DOCKER_REGISTRY=089879264256.dkr.ecr.eu-west-1.amazonaws.com \
AWS_DEFAULT_PROFILE=089879264256_AWS-ro-All \
PROJECT_HOME=$(pwd) \
bash tools/qa-tests/qa-tests.sh -b

# > Use "-b" as parameter to build the application before run tests
```
**Note:** `-b` will build images using the local environment - not following the process that is followed by the private-build.sh
In a similar process as CI does, first [private-build.sh](private-build.sh) is executed, and then [qa-tests.sh](qa-tests.sh) are executed.


# Common issues
* Docker socket is not listening
  * user:group (`$(id -u):$(id -g)`) used to execute `docker run` might fail to access to `/var/run/docker.sock`
    * It can be fixed using a `sudo chmod 666 /var/run/docker.sock`

# Jenkinsfile
This pipeline depends on the Jenkins library defined at [here](https://github.com/JumiaOPS/jumia-business-lib-jenkins)

The output of CI pipeline needs to be:
* proofs of execution
  * tests execution on JIRA
* artifact: docker [images versioned](https://confluence.jumia.com/pages/viewpage.action?pageId=138395484)

## Stages
* Checkout Repository
* Commits & Jira Validation
  * Get list of commit messages
    * validate messages have a JIRA issue ID
    * validate that JIRA issue is not in Backlog or Resolved
* Build app
  * build application/sources
    * migrations
    * other auxiliary applications
* Tests & Code inspection
  * run tests
  * generate reports
  * notify JIRA task about results (?)
* Push docker image
  * tag then with a version + commit hash ID
  * push all images necessary to production environments
* Deploy to staging
  * In case on development/main branches start deployment to staging environment
    * otherwise skip

# Dockerfile
## How to build

### Production by default
```shell
cd $(pwd)/../.. # Go to the root of project
docker build --progress=plain --network host -t  --file=infrastructure//Dockerfile .;
```

### Targets
* **build** - image with builtin application's sources
  * ```bash
    cd $(pwd)/../.. # Go to the root of project
    docker build \
      --progress=plain \
      --network host \
      --target build \
      --file=infrastructure//Dockerfile \
      -t build \
      .
    ```
* **production** - creates a production ready image
  * ```bash
    cd $(pwd)/../.. # Go to the root of project
    docker build \
      --progress=plain \
      --network host \
      --target production \
      --file=infrastructure//Dockerfile \
      -t production \
      .
    ```
* **postgresql-migrations** - creates a production ready image with postgresql-migrations
  * ```bash
    cd $(pwd)/../..
    docker build \
      --progress=plain \
      --network host \
      --target postgresql-migrations \
      --file=infrastructure//Dockerfile \
      -t postgresql-migrations \
      .
    ```
## Run
### Run production
```shell
cd $(pwd)/../.. # Go to the root of project
docker run --rm --network host --name  -i 
```

### Run tests
```shell
cd $(pwd)/../.. # Go to the root of project
docker run \
  --rm \
  -i \
  -v "$(pwd)":/opt/app/src/ \
  -v "$HOME/.gradle":/opt/app/src/.gradle \
  --user="$UID:$GID" \
  --network host \
  --name test \
  build \
  ./gradlew --no-daemon --warn --stacktrace --parallel test check
```

### Run app in docker
It can be tested with [swagger endpoint](http://localhost:8080/api/documentation/swagger-ui/index.html#/)
```shell
cd $(pwd)/../.. # Go to the root of project
docker run \
  --rm \
  -i \
  -v "$(pwd)":/opt/app/src/ \
  -v "$HOME/.gradle":/opt/app/src/.gradle \
  --user="$UID:$GID" \
  --network host \
  --name development \
  build \
  ./gradlew --no-daemon --warn --stacktrace --parallel bootRun -x test -x check
```

### Run build container with a different entrypoint
It can be useful to run different gradle tasks or start application in a custom way
```shell
cd $(pwd)/../.. # Go to the root of project
docker run \
  --rm \
  -i \
  -v "$(pwd)":/opt/app/src/ \
  -v "$HOME/.gradle":/opt/app/src/.gradle \
  --user="$UID:$GID" \
  --network host \
  --name development \
  build \
  ./gradlew tasks
```

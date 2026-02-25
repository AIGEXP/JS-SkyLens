rootProject.name = "skylens"

include(":app")
include(":app:acceptance")

include(":commons")
include(":commons:persistence-commons")

include(":domain")
include(":domain:usecases")
include(":domain:domain-api")
include(":domain:catalog")

include(":domain:ports")
include(":domain:ports:cache-api")
include(":domain:ports:http-out-api")
include(":domain:ports:telemetry-api")
include(":domain:ports:persistence-api")

include(":driven-adapters")
include(":driven-adapters:http-out")
include(":driven-adapters:infrastructure:cache")
include(":driven-adapters:infrastructure:persistence:migrations-jpa")
include(":driven-adapters:infrastructure:persistence:postgresql-jpa")
include(":driven-adapters:micrometer")

include(":driver-adapters")
include(":driver-adapters:cli-in")
include(":driver-adapters:http-in")
include(":driver-adapters:http-in:acl")
include(":driver-adapters:kafka-in")

include(":test-components:fakers")
include(":test-components:test-containers")

includeBuild("build-logic")

plugins {
    id("conventions.spring-java")
    alias(libs.plugins.openapi.generator)
}

openApiGenerate {
    generatorName.set("spring")
    inputSpec.set("$rootDir/app/src/main/resources/public/api/openapi.yaml")
    outputDir.set("${layout.buildDirectory.get().asFile}/generated")
    apiPackage.set("com.jumia.skylens.http.in.controllers")
    modelPackage.set("com.jumia.skylens.http.in.model")
    configOptions.set(mapOf(
        "interfaceOnly" to "true",
        "useSpringBoot3" to "true",
        "generateBuilders" to "true",
        "useTags" to "true",
        "useResponseEntity" to "false",
        "unhandledException" to "true",
        "openApiNullable" to "false"
    ))
}

tasks.compileJava {
    dependsOn(tasks.named("openApiGenerate"))
}

sourceSets {
    main {
        java {
            srcDir("${layout.buildDirectory.get().asFile}/generated/src/main/java")
        }
    }
}

dependencies {
    // Internal
    implementation(project(":commons"))
    implementation(project(":commons:persistence-commons"))
    implementation(project(":domain:domain-api"))
    implementation(project(":domain:catalog"))
    implementation(project(":driver-adapters:http-in:acl"))

    // Spring
    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.boot.starter.validation)
    implementation(libs.spring.boot.starter.actuator)
    implementation(libs.spring.tx)

    // MapStruct
    implementation(libs.mapstruct)

    // Swagger
    implementation(libs.springdoc.openapi.starter.webmvc.api)

    // Jumia
    implementation(libs.logging.context)

    compileOnly(libs.spotbugs.annotations)
    compileOnly(libs.lombok)

    annotationProcessor(libs.lombok)
    annotationProcessor(libs.mapstruct.processor)

    // Tests
    testImplementation(project(":test-components:fakers"))
    testImplementation(libs.awaitility)

    testImplementation(libs.spring.boot.starter.test)
    testImplementation(libs.spring.boot.starter.webmvc.test)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.datafaker)

    testCompileOnly(libs.lombok)
    testAnnotationProcessor(libs.lombok)
}

tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            element = "CLASS"
            limit {
                counter = "LINE"
                value = "COVEREDRATIO"
                minimum = "0.9".toBigDecimal()
            }
            excludes = listOf(
                    "com.jumia.skylens.http.in.responses.*",
                    "com.jumia.skylens.http.in.requests.*",
                    "com.jumia.skylens.http.in.exceptions.*",
                    "com.jumia.skylens.http.in.constants.*",
                    "com.jumia.skylens.http.in.configurations.*",
                    "com.jumia.skylens.http.in.interceptors.*",
                    "com.jumia.skylens.http.in.handlers.RestErrorHandler",
                    "com.jumia.skylens.http.in.management.responses.*",
                    "com.jumia.skylens.http.in.controllers.ApiUtil",
                    "com.jumia.skylens.http.in.controllers.ConfigurationApi",
                    "com.jumia.skylens.http.in.controllers.ReferenceDataApi",
                    "com.jumia.skylens.http.in.controllers.ServiceProvidersApi",
                    "com.jumia.skylens.http.in.model.*",
                    "org.openapitools.configuration.*",
                    "*ConverterImpl",
            )
        }
    }
}

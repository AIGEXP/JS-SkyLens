plugins {
    id("conventions.spring-java")
}

dependencies {
    // Internal
    implementation(project(":commons"))
    implementation(project(":commons:persistence-commons"))

    implementation(project(":domain:domain-api"))
    implementation(project(":domain:catalog"))

    implementation(project(":domain:ports:persistence-api"))
    implementation(project(":domain:ports:cache-api"))

    implementation(libs.commons.lang3)
    implementation(libs.slf4j.api)

    // Jumia
    implementation(libs.logging.context)

    implementation(libs.mapstruct)

    compileOnly(libs.spotbugs.annotations)
    compileOnly(libs.lombok)

    annotationProcessor(libs.lombok)
    annotationProcessor(libs.mapstruct.processor)

    // Tests
    testImplementation(project(":test-components:fakers"))

    testImplementation(libs.spring.boot.starter.test)

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
                minimum = "0.8".toBigDecimal()
            }
        }
    }
}

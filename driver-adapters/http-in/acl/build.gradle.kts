plugins {
    `java-library`

    id("conventions.spring-java")
}

dependencies {
    // Internal
    implementation(project(":commons"))
    implementation(project(":domain:catalog"))

    // Jumia
    api(libs.acl.lib)

    // MapStruct
    implementation(libs.mapstruct)
    implementation(libs.slf4j.api)

    compileOnly(libs.spotbugs.annotations)
    compileOnly(libs.lombok)

    annotationProcessor(libs.lombok)
    annotationProcessor(libs.mapstruct.processor)

    // Tests
    testImplementation(project(":test-components:fakers"))

    testImplementation(libs.mockito)
    testImplementation(libs.mockito.junit.jupiter)
    testImplementation(libs.assertj)
    testImplementation(libs.junit.jupiter.engine)
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
                minimum = "1.0".toBigDecimal()
            }
            excludes = listOf(
                    "*.disabled.*", // ignore disabled auth configuration
                    "com.jumia.skylens.http.in.acl.tokens.*",
                    "com.jumia.skylens.http.in.acl.exceptions.*",
                    "com.jumia.skylens.http.in.acl.credentials.*",
                    "com.jumia.skylens.http.in.acl.permissions.*",
                    "com.jumia.skylens.http.in.acl.resources.*",
                    "com.jumia.skylens.http.in.acl.AuthInstances.Instance",
                    "com.jumia.skylens.http.in.acl.authentication.AuthToken",
            )
        }
    }
}

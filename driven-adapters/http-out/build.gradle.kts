plugins {
    id("conventions.spring-java")
}

dependencies {

    // Internal dependencies
    implementation(project(":commons"))
    implementation(project(":domain:catalog"))
    implementation(project(":domain:ports:http-out-api"))

    // Jumia
    implementation(libs.logging.context)

    implementation(libs.spring.boot.starter.restclient)

    implementation(libs.apache.http.client)

    implementation(libs.slf4j.api)

    // Mapstruct
    implementation(libs.mapstruct)

    // Spotbugs Annotations
    implementation(libs.spotbugs.annotations)

    implementation(libs.resilience4j)
    implementation(libs.resilience4j.micrometer)

    // Others
    implementation(libs.spring.boot.starter.validation)

    compileOnly(libs.lombok)

    annotationProcessor(libs.lombok)
    annotationProcessor(libs.mapstruct.processor)

    // Tests
    testImplementation(project(":test-components:fakers"))
    testImplementation(project(":test-components:test-containers"))

    testImplementation(libs.spring.boot.starter.test)
    testImplementation(libs.spring.boot.starter.restclient.test)

    testImplementation(libs.mockserver.client.java)

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
                minimum = 1.0.toBigDecimal()
            }
            excludes = listOf(
                "com.jumia.skylens.http.out.configuration.*",
                "com.jumia.skylens.http.out.interceptors.*",
                "com.jumia.skylens.http.out.factories.ResilienceFactory",
                "com.jumia.skylens.http.out.skynet.configuration.*",
                "*ConverterImpl",
                )
        }
    }
}

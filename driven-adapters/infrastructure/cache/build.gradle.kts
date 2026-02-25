plugins {
    id("conventions.spring-java")
}

dependencies {

    // Internal dependencies
    implementation(project(":domain:ports:cache-api"))

    implementation(libs.spring.boot.starter.cache)
    implementation(libs.caffeine)

    implementation(libs.slf4j.api)

    // Spotbugs Annotations
    implementation(libs.spotbugs.annotations)

    compileOnly(libs.lombok)

    annotationProcessor(libs.lombok)

    // Tests
    testImplementation(libs.spring.boot.starter.test)

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
                "com.jumia.skylens.cache.country.configuration.*",
            )
        }
    }
}

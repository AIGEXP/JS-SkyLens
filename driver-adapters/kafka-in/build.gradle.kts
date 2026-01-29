plugins {
    id("conventions.spring-java")
}

dependencies {

    // Internal dependencies
    implementation(project(":commons"))
    implementation(project(":commons:persistence-commons"))
    implementation(project(":domain:domain-api"))
    implementation(project(":domain:catalog"))

    // Spring Boot
    implementation(libs.spring.boot.starter.kafka)
    implementation(libs.spring.boot.starter.json)
    implementation(libs.spring.boot.starter.validation)

    // Jumia
    implementation(libs.logging.context)

    implementation(libs.mapstruct)
    implementation(libs.spotbugs.annotations)

    compileOnly(libs.lombok)

    annotationProcessor(libs.lombok)
    annotationProcessor(libs.mapstruct.processor)

    //Test implementations
    testImplementation(libs.spring.boot.starter.test)
    testImplementation(libs.datafaker)
    testImplementation(project(":test-components:test-containers"))

    testCompileOnly(libs.lombok)
    testAnnotationProcessor(libs.lombok)
}

plugins {
    id("conventions.spring-java")
}

dependencies {

    // Internal dependencies
    implementation(project(":commons"))
    implementation(project(":commons:persistence-commons"))
    implementation(project(":domain:catalog"))
    implementation(project(":domain:ports:persistence-api"))

    // Data sources
    implementation(libs.postgresql)

    //Spring
    implementation(libs.spring.boot.starter.data.jpa)
    implementation(libs.spring.boot.starter.validation)

    //Hibernate Types (JsonB support)
    implementation(libs.hypersistence.utils)

    //Query DSL
    implementation(variantOf(libs.querydsl.jpa) { classifier("jakarta") })

    // Validation
    implementation(libs.hibernate.validator)

    implementation(libs.mapstruct)

    implementation(libs.commons.text)

    compileOnly(libs.lombok)

    //Annotation Processors
    annotationProcessor(libs.spring.boot.starter.data.jpa)
    annotationProcessor(libs.lombok)
    annotationProcessor(libs.mapstruct.processor)
    annotationProcessor(variantOf(libs.querydsl.apt) { classifier("jakarta") })

    //Test implementations
    testImplementation(project(":driven-adapters:infrastructure:persistence:migrations-jpa"))

    testImplementation(libs.spring.boot.starter.test)
    testImplementation(libs.spring.boot.starter.flyway)
    testImplementation(libs.flyway)
    testImplementation(libs.flyway.postgresql)
    testImplementation(libs.datafaker)
    testImplementation(project(":test-components:fakers"))
    testImplementation(project(":test-components:test-containers"))

    testCompileOnly(libs.lombok)
    testAnnotationProcessor(libs.lombok)
}

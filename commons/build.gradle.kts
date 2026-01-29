plugins {
    `java-library`

    id("conventions.spring-java")
}

dependencies {
    implementation(libs.logging.context)
    implementation(libs.hibernate.validator)
    implementation(libs.slf4j.api)

    compileOnly(libs.lombok)
    compileOnly(libs.spotbugs.annotations)

    annotationProcessor(libs.lombok)

    // Apache commons
    api(libs.commons.collections4)
}

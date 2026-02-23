plugins {
    id("conventions.spring-java")
}

dependencies {
    // Internal
    implementation(project(":commons"))
    implementation(project(":domain:catalog"))

    compileOnly(libs.lombok)

    annotationProcessor(libs.lombok)
}

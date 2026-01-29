plugins {
    id("conventions.spring-java")
}

dependencies {

    // Internal dependencies
    implementation(project(":commons"))

    compileOnly(libs.spotbugs.annotations)
    compileOnly(libs.lombok)

    annotationProcessor(libs.lombok)
}

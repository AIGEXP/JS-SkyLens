plugins {
    id("conventions.spring-java")
}

dependencies {
    compileOnly(libs.lombok)

    annotationProcessor(libs.lombok)
}

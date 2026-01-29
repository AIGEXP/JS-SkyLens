plugins {
    id("conventions.spring-java")
}

dependencies {

    implementation(project(":domain:catalog"))

    implementation(libs.datafaker)
}

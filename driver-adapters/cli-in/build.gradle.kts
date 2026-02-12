plugins {
    id("conventions.spring-java")
}

dependencies {
    implementation(project(":domain:domain-api"))
    implementation(project(":domain:catalog"))

    implementation(libs.spring.boot.starter)
    implementation(libs.spring.boot.starter.jdbc)

    runtimeOnly(libs.mssql.jdbc)

    compileOnly(libs.lombok)
    compileOnly(libs.spotbugs.annotations)

    annotationProcessor(libs.lombok)
}

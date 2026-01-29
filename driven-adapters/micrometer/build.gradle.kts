plugins {
    id("conventions.spring-java")
}

dependencies {

    // Internal dependencies
    implementation(project(":domain:ports:telemetry-api"))

    // Prometheus
    implementation(libs.micrometer.registry.prometheus)
    implementation(libs.spotbugs.annotations)
    compileOnly(libs.spotbugs.annotations)
    compileOnly(libs.lombok)

    annotationProcessor(libs.lombok)
}

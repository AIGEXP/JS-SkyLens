plugins {
    `java-library`

    id("conventions.spring-java")
}

dependencies {

    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)

    api(libs.testcontainers.junit.jupiter)
    api(libs.testcontainers.kafka)
    api(libs.testcontainers.mockserver)
    api(libs.testcontainers.postgresql)
    api(libs.testcontainers.rabbitmq)
    api(libs.testcontainers)
}

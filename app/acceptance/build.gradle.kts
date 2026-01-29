import org.gradle.api.tasks.testing.logging.TestExceptionFormat

plugins {
    id("conventions.spring-java")
}

dependencies {
    testImplementation(platform(libs.cucumber.bom))

    testImplementation(libs.cucumber.java)
    testImplementation(libs.cucumber.junit.platform.engine)
    testImplementation(libs.cucumber.spring)
    testImplementation(libs.junit.platform.suite)
    testImplementation(libs.assertj)

    testImplementation(libs.spring.boot.starter)
    testImplementation(libs.spring.boot.starter.kafka)
    testImplementation(libs.spring.jdbc)
    testImplementation(libs.spring.rabbit)
    testImplementation(libs.spring.test)
    testImplementation(libs.spring.web)
    testImplementation(libs.jackson.databind)
    testImplementation(libs.hikari)

    testImplementation(libs.awaitility)
    testImplementation(libs.datafaker)

    testRuntimeOnly(libs.junit.platform.launcher)
    testRuntimeOnly(libs.postgresql)

    testCompileOnly(libs.lombok)
    testAnnotationProcessor(libs.lombok)
}

tasks.test {
    enabled = false
}

tasks.register<Test>("acceptanceTest") {
    description = "Runs acceptance tests"
    group = LifecycleBasePlugin.VERIFICATION_GROUP

    testClassesDirs = sourceSets["test"].output.classesDirs
    classpath = sourceSets["test"].runtimeClasspath

    reports {
        html.required.set(true)
        junitXml.required.set(true)
        html.outputLocation = reporting.baseDirectory.dir("acceptanceTest/tests")
    }
    testLogging {
        exceptionFormat = TestExceptionFormat.FULL
        showStandardStreams = true
    }
    outputs.upToDateWhen { false }
}

tasks.register<JacocoReport>("jacocoAcceptanceTestReport") {
    description = "Runs acceptance tests"
    group = LifecycleBasePlugin.VERIFICATION_GROUP

    mustRunAfter(tasks.named("acceptanceTest"))
    reports {
        html.outputLocation = reporting.baseDirectory.dir("acceptanceTest/jacoco")
    }
}

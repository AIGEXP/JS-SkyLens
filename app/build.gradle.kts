plugins {
    id("conventions.spring-java")

    alias(libs.plugins.spring.boot)
    alias(libs.plugins.gradle.git.properties)
}

tasks.bootJar {
    mainClass = "com.jumia.skylens.app.Application"
    manifest {
        attributes(
            mapOf(
                "Implementation-Title" to "HMT Stops",
                "Implementation-Version" to rootProject.version
            )
        )
    }
    archiveFileName = "${archiveBaseName.get()}.${archiveExtension.get()}"
}

dependencies {
    // Internal
    implementation(project(":domain:domain-api"))
    implementation(project(":domain:catalog"))
    implementation(project(":domain:usecases"))

    implementation(project(":commons"))
    implementation(project(":commons:persistence-commons"))

    implementation(project(":domain:ports:persistence-api"))
    implementation(project(":domain:ports:telemetry-api"))

    implementation(project(":driven-adapters:infrastructure:persistence:postgresql-jpa"))
    implementation(project(":driven-adapters:micrometer"))

    implementation(project(":driver-adapters:cli-in"))
    implementation(project(":driver-adapters:http-in"))
    implementation(project(":driver-adapters:http-in:acl"))
    implementation(project(":driver-adapters:kafka-in"))

    // Spring Boot
    implementation(libs.spring.boot.starter)
    implementation(libs.spring.boot.starter.actuator)
    implementation(libs.spring.boot.starter.validation)
    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.tx)

    // Hibernate
    implementation(libs.hibernate.core)
    implementation(libs.hibernate.micrometer)

    // Hikari
    implementation(libs.hikari)

    // MapStruct
    implementation(libs.mapstruct)

    // Spring doc and swagger
    implementation(libs.springdoc.openapi.starter.webmvc.api)

    //Prometheus
    implementation(libs.micrometer.registry.prometheus)

    // Others
    compileOnly(libs.spring.boot.devtools)
    compileOnly(libs.spotbugs.annotations)
    compileOnly(libs.lombok)

    annotationProcessor(libs.lombok)
}

listOf(configurations["apiElements"], configurations["runtimeElements"]).forEach { config ->
    config.outgoing.artifacts.removeIf { artifact ->
        artifact.buildDependencies.getDependencies(null).contains(tasks.named("jar").get())
    }
    config.outgoing.artifact(tasks.named("bootJar").get())
}

tasks.processResources {
    dependsOn("generateGitProperties")
}

gitProperties {
    gitPropertiesName = "rev.txt"
    gitPropertiesResourceDir = file("${project.rootDir}/app/src/main/resources/public/api")

    customProperty("version", { project.version })
}

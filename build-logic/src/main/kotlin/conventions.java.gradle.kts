plugins {
    java
}

group = "com.jumia"

val libs: VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

repositories {
    maven {
        url = uri(getEnvOrProperty("NEXUS_URL") + getEnvOrProperty("NEXUS_DOWNLOAD_PATH"))
        isAllowInsecureProtocol = true
        credentials {
            username = getEnvOrProperty("NEXUS_USERNAME")
            password = getEnvOrProperty("NEXUS_PASSWORD")
        }
    }
    mavenCentral()
}

tasks.withType<JavaCompile> {
    val compilerArgs = options.compilerArgs
    compilerArgs.add("-Amapstruct.unmappedTargetPolicy=ERROR")
    compilerArgs.add("-Amapstruct.defaultInjectionStrategy=constructor")
    compilerArgs.add("-parameters")
}

tasks.withType<Test> {
    useJUnitPlatform()

    failOnNoDiscoveredTests = false

    dependencies {
        testRuntimeOnly(libs.findLibrary("junit-platform-launcher").get())
    }
}

fun getEnvOrProperty(name: String): String {
    val value = System.getenv(name) ?: findProperty(name) as? String
    if (value.isNullOrBlank()) {
        throw GradleException("Missing configuration [property or env variable]: $name")
    }
    return value
}

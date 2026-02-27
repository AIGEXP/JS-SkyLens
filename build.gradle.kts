plugins {
    alias(libs.plugins.versions)
}

tasks.dependencyUpdates {
    rejectVersionIf {
        listOf("-SNAPSHOT", "-alpha", "-beta", ".beta", "-rc", "-M").any {
            candidate.version.contains(it, ignoreCase = true)
        }
    }
}

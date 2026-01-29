plugins {
    java
    id("com.github.spotbugs")
}

spotbugs {
    showProgress = true
    showStackTraces = true
}

tasks.spotbugsMain {
    reports.create("xml") {
        required = true
    }
    reports.create("html") {
        required = true
    }
    excludeFilter = file("${project.rootDir}/config/spotbugs/main-exclude-filter.xml")
}

tasks.spotbugsTest {
    reports.create("xml") {
        required = true
    }
    reports.create("html") {
        required = true
    }
    excludeFilter = file("${project.rootDir}/config/spotbugs/test-exclude-filter.xml")
}

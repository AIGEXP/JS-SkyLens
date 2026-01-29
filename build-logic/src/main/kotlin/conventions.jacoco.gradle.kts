plugins {
    java
    jacoco
}

jacoco {
    toolVersion = "0.8.14"
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required = true
        html.required = true

        xml.outputLocation = layout.buildDirectory.file("test-results/jacoco/main.xml")
        html.outputLocation = layout.buildDirectory.dir("reports/jacoco")
    }
}

tasks.check {
    dependsOn(tasks.jacocoTestCoverageVerification)
}

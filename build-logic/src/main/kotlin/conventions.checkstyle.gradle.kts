plugins {
    java
    checkstyle
}

checkstyle {
    group = LifecycleBasePlugin.VERIFICATION_GROUP
    toolVersion = "13.2.0"
    configFile = file("${project.rootDir}/config/checkstyle/checkstyle.xml")
    isShowViolations = true
    isIgnoreFailures = false

    tasks.checkstyleTest {

        mustRunAfter(tasks.named("checkstyleMain"))
    }
}

tasks.withType<Checkstyle>().configureEach {
    group = LifecycleBasePlugin.VERIFICATION_GROUP
    reports {
        xml.required = true
        html.required = true
    }
}

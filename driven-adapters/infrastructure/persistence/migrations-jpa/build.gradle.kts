plugins {
    `java-library`

    id("conventions.spring-java")
    alias(libs.plugins.flyway)
}

buildscript {
    dependencies {
        classpath("org.flywaydb:flyway-database-postgresql:10.1.0")
        classpath("org.postgresql:postgresql:42.3.1")
    }
}

flyway {
    url = if (project.hasProperty("url")) project.property("url") as? String else "jdbc:postgresql://localhost:5432/hmt_stops"
    user = if (project.hasProperty("user")) project.property("user") as? String else "postgres"
    password = if (project.hasProperty("password")) project.property("password") as? String else "postgres"
    driver = "org.postgresql.Driver"
    table = "flyway_schema_history"
    schemas = arrayOf("public")
    outOfOrder = true
    baselineOnMigrate = true
    baselineVersion = "0"
    locations = arrayOf("classpath:db/migration")
}

dependencies {
    api(libs.flyway)
}

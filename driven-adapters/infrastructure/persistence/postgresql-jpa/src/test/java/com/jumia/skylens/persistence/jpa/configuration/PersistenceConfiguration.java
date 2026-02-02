package com.jumia.skylens.persistence.jpa.configuration;

import com.jumia.skylens.persistence.jpa.fakers.Faker;
import org.flywaydb.core.Flyway;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.flyway.autoconfigure.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;

@SpringBootConfiguration
@ComponentScan(value = {
        "com.jumia.skylens.persistence",
        "com.jumia.skylens.persistence.jpa",
        "com.jumia.skylens.persistence.jpa.converters"
})
@EnableAutoConfiguration
@ContextConfiguration
public class PersistenceConfiguration {

    @Bean
    Faker faker() {

        return new Faker();
    }

    @ConditionalOnMissingBean(FlywayMigrationStrategy.class)
    @Bean
    FlywayMigrationStrategy flywayMigrationStrategy() {

        return Flyway::migrate;
    }
}

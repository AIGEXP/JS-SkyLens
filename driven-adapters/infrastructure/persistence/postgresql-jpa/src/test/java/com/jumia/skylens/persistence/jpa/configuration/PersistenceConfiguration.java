package com.jumia.skylens.persistence.jpa.configuration;

import com.jumia.skylens.persistence.jpa.fakers.Faker;
import com.zaxxer.hikari.HikariConfig;
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
        "com.jumia.skylens.persistence.jpa.converters",
        "com.jumia.skylens.persistence.jpa.utils"
})
@EnableAutoConfiguration
@ContextConfiguration(classes = {DataSourceConfiguration.class})
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

    @Bean
    PersistenceProperties persistenceProperties() {

        return new PersistenceProperties() {

            @Override
            public HikariConfig getDataSource() {

                final HikariConfig hikariConfig = new HikariConfig();
                hikariConfig.setJdbcUrl(System.getProperty("app.data-sources.rw.jdbc-url"));
                hikariConfig.setUsername(System.getProperty("app.data-sources.rw.username"));
                hikariConfig.setPassword(System.getProperty("app.data-sources.rw.password"));

                return hikariConfig;
            }

            @Override
            public HikariConfig getReplicaDataSource() {

                return getDataSource();
            }
        };
    }
}

package com.jumia.skylens.test.testcontainers;

import lombok.extern.slf4j.Slf4j;
import org.testcontainers.postgresql.PostgreSQLContainer;

@Slf4j
public final class PostgreSqlContainerSingleton extends PostgreSQLContainer {

    private static final String IMAGE_VERSION = "postgres:16-alpine";

    private PostgreSqlContainerSingleton() {

        super(IMAGE_VERSION);
    }

    public static PostgreSqlContainerSingleton getInstance() {

        return ContainerHolder.CONTAINER;
    }

    @Override
    public void start() {

        ContainerHolder.CONTAINER.withDatabaseName("")
                .withUsername("postgres")
                .withPassword("postgres");

        super.start();
    }

    @Override
    public void stop() {

        log.debug("PostgreSQL Database container shutting down");
        super.stop();
    }

    private static final class ContainerHolder {

        private static final PostgreSqlContainerSingleton CONTAINER = new PostgreSqlContainerSingleton();
    }
}


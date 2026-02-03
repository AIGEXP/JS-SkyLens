package com.jumia.skylens.persistence.jpa.configuration;

import com.jumia.skylens.test.testcontainers.PostgreSqlContainerSingleton;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.postgresql.PostgreSQLContainer;

public class PostgreSqlContainerExtension implements BeforeAllCallback, AfterEachCallback, BeforeEachCallback {

    private PostgreSQLContainer postgreSQLContainer;

    @Override
    public void beforeAll(@NonNull ExtensionContext context) {

        postgreSQLContainer = PostgreSqlContainerSingleton.getInstance();

        postgreSQLContainer.withReuse(true);
        postgreSQLContainer.start();

        setDatasourceProperties();
    }

    @Override
    public void beforeEach(@NonNull ExtensionContext extensionContext) {

        setDatasourceProperties();
    }

    @Override
    public void afterEach(@NonNull ExtensionContext extensionContext) {

        final ApplicationContext springContext = SpringExtension.getApplicationContext(extensionContext);
        final RepoUtilsIT repoUtilsIT = springContext.getBean(RepoUtilsIT.class);

        repoUtilsIT.cleanUpTables();
    }

    private void setDatasourceProperties() {

        System.setProperty("spring.datasource.url", postgreSQLContainer.getJdbcUrl());
        System.setProperty("spring.datasource.username", postgreSQLContainer.getUsername());
        System.setProperty("spring.datasource.password", postgreSQLContainer.getPassword());
    }
}

package com.jumia.skylens.persistence.jpa.configuration;

import com.jumia.skylens.persistence.jpa.fakers.Faker;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.TimeZone;

@ExtendWith(PostgreSqlContainerExtension.class)
@Slf4j
@Transactional
@SpringBootTest(classes = PersistenceConfiguration.class)
public class BaseTestIT {

    static {

        System.setProperty("user.timezone", "UTC");
        TimeZone.setDefault(TimeZone.getTimeZone("Etc/UTC"));
    }

    @Autowired
    protected Faker faker;

    @Autowired
    protected ITPersister itPersister;
}

package com.jumia.skylens.persistence.jpa.configuration;

import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan("com.jumia.skylens.persistence.jpa.entities")
@EnableJpaRepositories(basePackages = "com.jumia.skylens.persistence.jpa.repositories")
public class JpaConfiguration {


}

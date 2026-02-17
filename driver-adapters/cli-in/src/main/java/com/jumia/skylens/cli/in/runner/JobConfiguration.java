package com.jumia.skylens.cli.in.runner;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("job")
@EnableConfigurationProperties(JobProperties.class)
public class JobConfiguration {
}

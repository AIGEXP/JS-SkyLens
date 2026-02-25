package com.jumia.skylens.app.configurations.cache;

import com.jumia.skylens.app.configurations.properties.AppProperties;
import com.jumia.skylens.app.configurations.properties.CountryCachePropertiesImpl;
import com.jumia.skylens.cache.country.configuration.CountryCacheProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfiguration {

    @Bean
    public CountryCacheProperties countryCacheProperties(final AppProperties appProperties) {

        return new CountryCachePropertiesImpl(appProperties.getCache().getCountry());
    }
}

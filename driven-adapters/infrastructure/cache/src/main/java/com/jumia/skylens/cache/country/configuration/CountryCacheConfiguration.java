package com.jumia.skylens.cache.country.configuration;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.jumia.skylens.cache.country.util.Constants;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CountryCacheConfiguration {

    @Bean
    public CacheManager countryCacheManagement(final CountryCacheProperties countryCacheProperties) {

        final CaffeineCacheManager cacheManager = new CaffeineCacheManager(Constants.COUNTRY_CACHE_NAME);

        cacheManager.setCaffeine(Caffeine.newBuilder()
                                         .expireAfterAccess(countryCacheProperties.expiration())
                                         .maximumSize(countryCacheProperties.maxSize()));

        return cacheManager;
    }
}

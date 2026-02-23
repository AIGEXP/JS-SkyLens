package com.jumia.skylens.http.out.skynet.configuration;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.jumia.skylens.commons.configurations.CacheProperties;
import com.jumia.skylens.http.out.skynet.util.Constants;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class SkyNetCacheConfiguration {

    @Bean
    public CacheManager countryCacheManagement(final SkyNetProperties skyNetProperties) {

        final CacheProperties cacheProperties = skyNetProperties.cache();

        final CaffeineCacheManager cacheManager = new CaffeineCacheManager(Constants.COUNTRY_CACHE_NAME);

        cacheManager.setCaffeine(Caffeine.newBuilder()
                                         .expireAfterWrite(cacheProperties.expiration())
                                         .maximumSize(cacheProperties.maxSize()));

        return cacheManager;
    }
}

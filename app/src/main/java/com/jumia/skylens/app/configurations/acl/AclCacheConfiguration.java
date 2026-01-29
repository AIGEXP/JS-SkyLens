package com.jumia.skylens.app.configurations.acl;

import com.jumia.skylens.http.in.configurations.properties.AclCacheProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pt.jumia.services.acl.lib.CacheStrategy;
import pt.jumia.services.acl.lib.cache.InMemoryCacheStrategy;

import java.util.concurrent.TimeUnit;

@Configuration
public class AclCacheConfiguration {

    @Bean
    CacheStrategy cacheStrategy(AclCacheProperties aclCacheProperties) {

        if (aclCacheProperties == null) {

            return InMemoryCacheStrategy.newInstance(1, TimeUnit.HOURS);
        }

        return InMemoryCacheStrategy.newInstance(aclCacheProperties.expireDuration().toMillis(),
                                                 TimeUnit.MILLISECONDS);
    }
}

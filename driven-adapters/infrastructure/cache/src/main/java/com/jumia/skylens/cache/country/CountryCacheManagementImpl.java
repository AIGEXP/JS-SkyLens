package com.jumia.skylens.cache.country;

import com.github.benmanes.caffeine.cache.Cache;
import com.jumia.skylens.cache.country.configuration.CountryCacheProperties;
import com.jumia.skylens.cache.country.util.Constants;
import com.jumia.skylens.ports.cache.api.CountryCacheManagement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class CountryCacheManagementImpl implements CountryCacheManagement {

    private final boolean enabled;
    private final CacheManager cacheManager;

    public CountryCacheManagementImpl(final CountryCacheProperties countryCacheProperties, final CacheManager cacheManager) {

        this.enabled = countryCacheProperties.enabled();
        this.cacheManager = cacheManager;
    }

    @Override
    public boolean isEnabled() {

        return enabled;
    }

    @Override
    public void clearCache() {

        final org.springframework.cache.Cache cache = cacheManager.getCache(Constants.COUNTRY_CACHE_NAME);

        if (cache != null) {

            cache.clear();
            log.info("Cache '{}' cleared successfully", Constants.COUNTRY_CACHE_NAME);
        }
    }

    @Override
    public Map<String, String> getCache() {

        final org.springframework.cache.Cache springCache = cacheManager.getCache(Constants.COUNTRY_CACHE_NAME);

        if (springCache == null) {

            return Collections.emptyMap();
        }

        final Object nativeCache = springCache.getNativeCache();

        if (!(nativeCache instanceof Cache<?, ?> caffeineCache)) {

            log.warn("Unexpected cache type: {}.", nativeCache.getClass().getName());
            return Collections.emptyMap();
        }

        return Collections.unmodifiableMap(
                caffeineCache.asMap().entrySet().stream()
                        .collect(Collectors.toMap(
                                entry -> String.valueOf(entry.getKey()),
                                entry -> String.valueOf(entry.getValue()))));
    }
}

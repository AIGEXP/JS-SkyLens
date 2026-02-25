package com.jumia.skylens.cache.country;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.jumia.skylens.cache.country.configuration.CountryCacheProperties;
import com.jumia.skylens.cache.country.util.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CountryCacheManagementImplTest {

    @Mock
    private CountryCacheProperties countryCacheProperties;

    @Mock
    private CacheManager cacheManager;

    private CountryCacheManagementImpl subject;

    @BeforeEach
    void setUp() {

        when(countryCacheProperties.enabled()).thenReturn(true);
        subject = new CountryCacheManagementImpl(countryCacheProperties, cacheManager);
    }

    @Test
    void isEnabled_whenCacheIsEnabled_returnsTrue() {

        // Then
        assertThat(subject.isEnabled()).isTrue();
    }

    @Test
    void isEnabled_whenCacheIsDisabled_returnsFalse() {

        // Given
        when(countryCacheProperties.enabled()).thenReturn(false);
        subject = new CountryCacheManagementImpl(countryCacheProperties, cacheManager);

        // Then
        assertThat(subject.isEnabled()).isFalse();
    }

    @Test
    void clearCache_whenCacheExists_clearsSuccessfully() {

        // Given
        final Cache<Object, Object> nativeCache = Caffeine.newBuilder().build();
        nativeCache.put("SP-001", "PT");
        final CaffeineCache caffeineCache = new CaffeineCache(Constants.COUNTRY_CACHE_NAME, nativeCache);
        when(cacheManager.getCache(Constants.COUNTRY_CACHE_NAME)).thenReturn(caffeineCache);

        // When
        subject.clearCache();

        // Then
        assertThat(nativeCache.asMap()).isEmpty();
    }

    @Test
    void clearCache_whenCacheIsNull_doesNothing() {

        // Given
        when(cacheManager.getCache(Constants.COUNTRY_CACHE_NAME)).thenReturn(null);

        // When
        subject.clearCache();

        // Then
        verify(cacheManager).getCache(Constants.COUNTRY_CACHE_NAME);
    }

    @Test
    void getCache_whenCacheHasEntries_returnsAllEntries() {

        // Given
        final Cache<Object, Object> nativeCache = Caffeine.newBuilder().build();
        nativeCache.put("SP-001", "PT");
        nativeCache.put("SP-002", "NG");
        nativeCache.put("SP-003", "EG");
        final CaffeineCache caffeineCache = new CaffeineCache(Constants.COUNTRY_CACHE_NAME, nativeCache);
        when(cacheManager.getCache(Constants.COUNTRY_CACHE_NAME)).thenReturn(caffeineCache);

        // When
        final Map<String, String> result = subject.getCache();

        // Then
        assertThat(result)
                .containsEntry("SP-001", "PT")
                .containsEntry("SP-002", "NG")
                .containsEntry("SP-003", "EG")
                .hasSize(3);
    }

    @Test
    void getCache_whenCacheIsEmpty_returnsEmptyMap() {

        // Given
        final Cache<Object, Object> nativeCache = Caffeine.newBuilder().build();
        final CaffeineCache caffeineCache = new CaffeineCache(Constants.COUNTRY_CACHE_NAME, nativeCache);
        when(cacheManager.getCache(Constants.COUNTRY_CACHE_NAME)).thenReturn(caffeineCache);

        // When
        final Map<String, String> result = subject.getCache();

        // Then
        assertThat(result).isEmpty();
    }

    @Test
    void getCache_whenCacheIsNull_returnsEmptyMap() {

        // Given
        when(cacheManager.getCache(Constants.COUNTRY_CACHE_NAME)).thenReturn(null);

        // When
        final Map<String, String> result = subject.getCache();

        // Then
        assertThat(result).isEmpty();
    }

    @Test
    void getCache_whenNativeCacheIsNotCaffeine_returnsEmptyMap() {

        // Given
        final org.springframework.cache.Cache springCache = mock(org.springframework.cache.Cache.class);
        when(cacheManager.getCache(Constants.COUNTRY_CACHE_NAME)).thenReturn(springCache);
        when(springCache.getNativeCache()).thenReturn(new Object());

        // When
        final Map<String, String> result = subject.getCache();

        // Then
        assertThat(result).isEmpty();
    }
}

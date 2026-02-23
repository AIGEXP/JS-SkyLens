package com.jumia.skylens.domain.impl;

import com.jumia.skylens.ports.http.out.api.cache.CountryCacheManagement;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetCacheUseCaseImplTest {

    @Mock
    private CountryCacheManagement countryCacheManagement;

    @InjectMocks
    private GetCacheUseCaseImpl subject;

    @Test
    void run_whenCalled_thenReturnCacheEntries() {

        // Given
        final Map<String, String> cacheEntries = Map.of("SP-001", "KE", "SP-002", "NG");

        when(countryCacheManagement.getCache()).thenReturn(cacheEntries);

        // When
        final Map<String, String> result = subject.run();

        // Then
        assertThat(result).isEqualTo(cacheEntries);

        verify(countryCacheManagement).getCache();
    }
}

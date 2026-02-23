package com.jumia.skylens.http.in.services;

import com.jumia.skylens.domain.ClearCacheUseCase;
import com.jumia.skylens.domain.GetCacheUseCase;
import com.jumia.skylens.http.in.converters.CacheResponseConverter;
import com.jumia.skylens.http.in.model.CacheResponseInner;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CacheServiceTest {

    @Mock
    private ClearCacheUseCase clearCacheUseCase;

    @Mock
    private GetCacheUseCase getCacheUseCase;

    @Mock
    private CacheResponseConverter cacheResponseConverter;

    @InjectMocks
    private CacheService subject;

    @Test
    void clearCache_whenCalled_thenDelegatesToUseCase() {

        // When
        subject.clearCache();

        // Then
        verify(clearCacheUseCase).run();
    }

    @Test
    void listCache_whenCalled_thenDelegatesToUseCaseAndConverter() {

        // Given
        final Map<String, String> cacheEntries = Map.of("SP-001", "KE", "SP-002", "NG");
        final List<CacheResponseInner> expectedResponse = List.of(mock(CacheResponseInner.class));

        when(getCacheUseCase.run()).thenReturn(cacheEntries);
        when(cacheResponseConverter.convert(cacheEntries)).thenReturn(expectedResponse);

        // When
        final List<CacheResponseInner> result = subject.listCache();

        // Then
        assertThat(result).isEqualTo(expectedResponse);

        verify(getCacheUseCase).run();
        verify(cacheResponseConverter).convert(cacheEntries);
    }
}

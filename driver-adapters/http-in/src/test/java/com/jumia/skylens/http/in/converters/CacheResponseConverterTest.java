package com.jumia.skylens.http.in.converters;

import com.jumia.skylens.http.in.model.CacheResponseInner;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class CacheResponseConverterTest {

    private final CacheResponseConverter subject = new CacheResponseConverter();

    @Test
    void convert_whenCacheHasEntries_thenConvertSuccessfully() {

        // Given
        final Map<String, String> source = Map.of("SP-001", "KE", "SP-002", "NG");

        // When
        final List<CacheResponseInner> result = subject.convert(source);

        // Then
        assertThat(result).hasSize(2);
        assertThat(result).extracting(CacheResponseInner::getKey).containsExactlyInAnyOrder("SP-001", "SP-002");
        assertThat(result).extracting(CacheResponseInner::getValue).containsExactlyInAnyOrder("KE", "NG");
    }

    @Test
    void convert_whenCacheIsEmpty_thenReturnEmptyList() {

        // Given
        final Map<String, String> source = Map.of();

        // When
        final List<CacheResponseInner> result = subject.convert(source);

        // Then
        assertThat(result).isEmpty();
    }
}

package com.jumia.skylens.http.in.converters;

import com.jumia.skylens.domain.catalog.DateRange;
import com.jumia.skylens.http.in.model.DateRangeOption;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ListDateRangeConverterTest {

    private final ListDateRangeConverter subject = new ListDateRangeConverterImpl();

    @Test
    void convert_whenCalled_thenConvertSuccessfully() {

        // Given
        final List<DateRange> dateRanges = List.of(DateRange.values());

        // When
        final List<DateRangeOption> dateRangeOptions = subject.convert(dateRanges);

        // Then
        assertThat(dateRangeOptions)
                .hasSize(dateRanges.size())
                .usingRecursiveComparison()
                .isEqualTo(dateRanges.stream()
                        .map(dateRange -> DateRangeOption.builder()
                                .value(com.jumia.skylens.http.in.model.DateRange.fromValue(dateRange.name()))
                                .description(subject.toDescription(dateRange))
                                .build())
                        .toList());
    }
}

package com.jumia.skylens.http.in.converters;

import com.jumia.skylens.domain.catalog.enums.DateRangeType;
import com.jumia.skylens.http.in.model.DateRange;
import com.jumia.skylens.http.in.model.DateRangeOption;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ListDateRangeConverterTest {

    private final ListDateRangeConverter subject = new ListDateRangeConverterImpl();

    @Test
    void convert_whenCalled_thenConvertSuccessfully() {

        // Given
        final List<DateRangeType> dateRangeTypes = List.of(DateRangeType.values());

        // When
        final List<DateRangeOption> dateRangeOptions = subject.convert(dateRangeTypes);

        // Then
        assertThat(dateRangeOptions)
                .hasSize(dateRangeTypes.size())
                .usingRecursiveComparison()
                .isEqualTo(dateRangeTypes.stream()
                        .map(dateRangeType -> DateRangeOption.builder()
                                .value(DateRange.fromValue(dateRangeType.name()))
                                .description(dateRangeType.getDescription())
                                .build())
                        .toList());
    }
}

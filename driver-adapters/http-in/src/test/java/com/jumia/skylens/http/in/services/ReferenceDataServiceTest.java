package com.jumia.skylens.http.in.services;

import com.jumia.skylens.domain.ListDateRangeUseCase;
import com.jumia.skylens.domain.catalog.enums.DateRangeType;
import com.jumia.skylens.http.in.converters.ListDateRangeConverter;
import com.jumia.skylens.http.in.model.DateRange;
import com.jumia.skylens.http.in.model.DateRangeOption;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReferenceDataServiceTest {

    @Mock
    private ListDateRangeUseCase listDateRangeUseCase;

    @Mock
    private ListDateRangeConverter listDateRangeConverter;

    @InjectMocks
    private ReferenceDataService subject;

    @Test
    void listDateRange_whenFilteringByDate_thenReturnAllDateRangeTypes() {

        // Given
        final List<DateRangeType> dateRangeTypes = List.of(DateRangeType.values());
        final List<DateRangeOption> expectedDateRange = Stream.of(DateRangeType.values())
                .map(dateRange -> new DateRangeOption(DateRange.valueOf(dateRange.name()), dateRange.getDescription()))
                .toList();

        // When
        when(listDateRangeUseCase.run()).thenReturn(dateRangeTypes);
        when(listDateRangeConverter.convert(dateRangeTypes)).thenReturn(expectedDateRange);
        final List<DateRangeOption> actualDateRanges = subject.listDateRangeTypes();

        // Then
        assertEquals(expectedDateRange, actualDateRanges);
    }
}

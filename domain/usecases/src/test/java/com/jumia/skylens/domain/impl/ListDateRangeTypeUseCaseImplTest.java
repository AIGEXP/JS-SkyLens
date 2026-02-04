package com.jumia.skylens.domain.impl;

import com.jumia.skylens.domain.catalog.DateRange;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ListDateRangeTypeUseCaseImplTest {

    @InjectMocks
    private ListDateRangeUseCaseImpl subject;

    @Test
    void run_whenListDateRange_thenReturnAllDateRanges() {

        // Given
        // When
        final List<DateRange> dateRanges = subject.run();

        // Then
        assertThat(dateRanges)
                .isNotEmpty()
                .isEqualTo(List.of(DateRange.values()));
    }
}

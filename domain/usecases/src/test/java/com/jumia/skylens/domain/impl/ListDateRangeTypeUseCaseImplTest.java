package com.jumia.skylens.domain.impl;

import com.jumia.skylens.domain.catalog.enums.DateRangeType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class ListDateRangeTypeUseCaseImplTest {

    @InjectMocks
    private ListDateRangeUseCaseImpl subject;

    @Test
    void run_whenListDateRange_thenReturnAllDateRanges() {

        // Given
        // When
        final List<DateRangeType> dateRangeTypes = subject.run();

        // Then
        assertThat(dateRangeTypes)
                .isNotEmpty()
                .isEqualTo(List.of(DateRangeType.values()));
    }
}

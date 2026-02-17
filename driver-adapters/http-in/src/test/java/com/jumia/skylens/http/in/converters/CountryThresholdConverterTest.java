package com.jumia.skylens.http.in.converters;

import com.jumia.skylens.domain.catalog.CountryThreshold;
import com.jumia.skylens.http.in.model.ReportType;
import com.jumia.skylens.http.in.model.ThresholdRequest;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class CountryThresholdConverterTest {

    private final CountryThresholdConverter subject = new CountryThresholdConverterImpl();

    @Test
    void convert_whenCalled_convertSuccessfully() {

        // Given
        final String country = "CI";
        final ReportType reportType = ReportType.SUCCESS_RATE;
        final ThresholdRequest request = new ThresholdRequest(BigDecimal.valueOf(0.85));

        // When
        final CountryThreshold result = subject.convert(country, reportType, request);

        // Then
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(CountryThreshold.builder()
                                   .reportType(com.jumia.skylens.domain.catalog.ReportType.SUCCESS_RATE)
                                   .country(country)
                                   .value(BigDecimal.valueOf(0.85))
                                   .build());
    }
}

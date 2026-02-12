package com.jumia.skylens.http.in.converters;

import com.jumia.skylens.domain.catalog.NetworkThreshold;
import com.jumia.skylens.http.in.model.ReportType;
import com.jumia.skylens.http.in.model.ThresholdRequest;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class NetworkThresholdConverterTest {

    private final NetworkThresholdConverter subject = new NetworkThresholdConverterImpl();

    @Test
    void convert_whenCalled_convertSuccessfully() {

        // Given
        final String country = "CI";
        final ReportType reportType = ReportType.SUCCESS_RATE;
        final ThresholdRequest request = new ThresholdRequest(BigDecimal.valueOf(0.85));

        // When
        final NetworkThreshold result = subject.convert(country, reportType, request);

        // Then
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(NetworkThreshold.builder()
                                   .reportType(com.jumia.skylens.domain.catalog.ReportType.SUCCESS_RATE)
                                   .network(country)
                                   .value(BigDecimal.valueOf(0.85))
                                   .build());
    }
}

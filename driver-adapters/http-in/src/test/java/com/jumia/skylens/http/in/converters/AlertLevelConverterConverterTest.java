package com.jumia.skylens.http.in.converters;

import com.jumia.skylens.domain.catalog.AlertLevel;
import com.jumia.skylens.http.in.fakers.RestFaker;
import com.jumia.skylens.http.in.model.AlertLevelRequest;
import com.jumia.skylens.http.in.model.ReportType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class AlertLevelConverterConverterTest {

    private final RestFaker faker = new RestFaker();

    private final AlertLevelConverter subject = new AlertLevelConverterImpl();

    @Test
    void convert_whenCalled_convertSuccessfully() {

        // Given
        final AlertLevelRequest alertLevelRequest = faker.alertLevelRequest().build();
        final String country = faker.country().name();
        final ReportType reportType = faker.options().option(ReportType.class);

        // When
        final AlertLevel alertLevel = subject.convert(alertLevelRequest, country, reportType);

        // Then
        assertThat(alertLevel)
                .usingRecursiveComparison()
                .isEqualTo(AlertLevel.builder()
                                   .criticalValue(BigDecimal.valueOf(alertLevelRequest.getCriticalValue()))
                                   .warningValue(BigDecimal.valueOf(alertLevelRequest.getWarningValue()))
                                   .country(country)
                                   .reportType(com.jumia.skylens.domain.catalog.ReportType.valueOf(reportType.name()))
                                   .build());
    }
}

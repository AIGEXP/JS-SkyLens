package com.jumia.skylens.http.in.converters;

import com.jumia.skylens.domain.catalog.Boundary;
import com.jumia.skylens.http.in.fakers.RestFaker;
import com.jumia.skylens.http.in.model.BoundaryRequest;
import com.jumia.skylens.http.in.model.ReportType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class BoundaryConverterConverterTest {

    private final RestFaker faker = new RestFaker();

    private final BoundaryConverter subject = new BoundaryConverterImpl();

    @Test
    void convert_whenCalled_convertSuccessfully() {

        // Given
        final BoundaryRequest boundaryRequest = faker.boundaryRequest().build();
        final String country = faker.country().name();
        final ReportType reportType = faker.options().option(ReportType.class);

        // When
        final Boundary boundary = subject.convert(boundaryRequest, country, reportType);

        // Then
        assertThat(boundary)
                .usingRecursiveComparison()
                .isEqualTo(Boundary.builder()
                                   .critical(BigDecimal.valueOf(boundaryRequest.getCritical()))
                                   .warning(BigDecimal.valueOf(boundaryRequest.getWarning()))
                                   .country(country)
                                   .reportType(com.jumia.skylens.domain.catalog.ReportType.valueOf(reportType.name()))
                                   .build());
    }
}

package com.jumia.skylens.http.in.converters;

import com.jumia.skylens.domain.catalog.Boundary;
import com.jumia.skylens.http.in.model.BoundaryResponse;
import com.jumia.skylens.test.fakers.DomainFaker;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BoundaryResponseConverterTest {

    private final DomainFaker faker = new DomainFaker();

    private final BoundaryResponseConverter subject = new BoundaryResponseConverterImpl();

    @Test
    void convert_whenCalled_convertSuccessfully() {

        // Given
        final Boundary boundary = faker.boundary().build();

        // When
        final BoundaryResponse boundaryResponse = subject.convert(boundary);

        // Then
        assertThat(boundaryResponse)
                .usingRecursiveComparison()
                .isEqualTo(BoundaryResponse.builder()
                                   .critical(boundary.critical().doubleValue())
                                   .warning(boundary.warning().doubleValue())
                                   .build());
    }
}

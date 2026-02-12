package com.jumia.skylens.http.in.converters;

import com.jumia.skylens.domain.catalog.NetworkThreshold;
import com.jumia.skylens.http.in.model.ThresholdResponse;
import com.jumia.skylens.test.fakers.DomainFaker;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ThresholdResponseConverterTest {

    private final ThresholdResponseConverter subject = new ThresholdResponseConverterImpl();

    private final DomainFaker faker = new DomainFaker();

    @Test
    void convert_whenCalled_convertSuccessfully() {

        // Given
        final NetworkThreshold networkThreshold = faker.networkThreshold();

        // When
        final ThresholdResponse result = subject.convert(networkThreshold);

        // Then
        assertThat(result.getTargetRate()).isEqualByComparingTo(networkThreshold.value());
        assertThat(result.getUpdatedAt()).isNull();
    }
}

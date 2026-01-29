package com.jumia.skylens.http.in.converters;

import com.jumia.skylens.domain.catalog.StopPublishingFilter;
import com.jumia.skylens.http.in.fakers.RestFaker;
import com.jumia.skylens.http.in.model.StopPublishingRequest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StopPublishingFilterConverterTest {

    private final RestFaker faker = new RestFaker();

    private final StopPublishingFilterConverter subject = new StopPublishingFilterConverterImpl();

    @Test
    void convert_whenForceIsFalse_thenConvertSuccessfully() {

        // Given
        final StopPublishingRequest stopPublishingRequest = faker.stopPublishingRequest().force(false).build();

        // When
        final StopPublishingFilter result = subject.convert(stopPublishingRequest);

        // Then
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(StopPublishingFilter.builder()
                                   .partnerSid(stopPublishingRequest.getPartnerSid())
                                   .network(stopPublishingRequest.getNetwork())
                                   .dateFrom(stopPublishingRequest.getDateFrom())
                                   .dateTo(stopPublishingRequest.getDateTo())
                                   .published(false)
                                   .build());
    }

    @Test
    void convert_whenForceIsTrue_thenPublishedIsNull() {

        // Given
        final StopPublishingRequest stopPublishingRequest = faker.stopPublishingRequest().force(true).build();

        // When
        final StopPublishingFilter result = subject.convert(stopPublishingRequest);

        // Then
        assertThat(result.published()).isNull();
    }
}

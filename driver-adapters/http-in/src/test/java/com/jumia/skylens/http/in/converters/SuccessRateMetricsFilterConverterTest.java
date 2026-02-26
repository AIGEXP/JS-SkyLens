package com.jumia.skylens.http.in.converters;

import com.jumia.skylens.domain.catalog.SuccessRateMetricsFilter;
import com.jumia.skylens.http.in.model.DateRange;
import com.jumia.skylens.http.in.model.MovementType;
import com.jumia.skylens.http.in.model.PaymentType;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class SuccessRateMetricsFilterConverterTest {

    private final SuccessRateMetricsFilterConverter subject = new SuccessRateMetricsFilterConverterImpl();

    @Test
    void convert_whenCalled_convertSuccessfully() {

        // Given
        final String country = "CI";
        final UUID serviceProviderSid = UUID.randomUUID();
        final DateRange dateRange = DateRange.CURRENT_WEEK;
        final UUID hubSid = UUID.randomUUID();
        final PaymentType paymentType = PaymentType.PRE;
        final MovementType movementType = MovementType.DOOR;

        // When
        final SuccessRateMetricsFilter successRateMetricsFilter = subject.convert(country,
                                                                                  serviceProviderSid,
                                                                                  dateRange,
                                                                                  hubSid,
                                                                                  paymentType,
                                                                                  movementType);

        // Then
        assertThat(successRateMetricsFilter)
                .usingRecursiveComparison()
                .isEqualTo(SuccessRateMetricsFilter.builder()
                                   .country(country)
                                   .serviceProviderSid(serviceProviderSid)
                                   .hubSid(hubSid)
                                   .dateRange(com.jumia.skylens.domain.catalog.DateRange.CURRENT_WEEK)
                                   .paymentType(com.jumia.skylens.domain.catalog.PaymentType.PRE)
                                   .movementType(com.jumia.skylens.domain.catalog.MovementType.DOOR)
                                   .build());
    }
}

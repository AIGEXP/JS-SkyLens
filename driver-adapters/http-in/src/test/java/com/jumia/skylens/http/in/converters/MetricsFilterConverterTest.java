package com.jumia.skylens.http.in.converters;

import com.jumia.skylens.domain.catalog.MetricsFilter;
import com.jumia.skylens.http.in.model.DateRange;
import com.jumia.skylens.http.in.model.MovementType;
import com.jumia.skylens.http.in.model.PaymentType;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class MetricsFilterConverterTest {

    private final MetricsFilterConverter subject = new MetricsFilterConverterImpl();

    @Test
    void convert_whenCalled_convertSuccessfully() {

        // Given
        final UUID serviceProviderSid = UUID.randomUUID();
        final DateRange dateRange = DateRange.CURRENT_WEEK;
        final UUID hubSid = UUID.randomUUID();
        final PaymentType paymentType = PaymentType.PRE;
        final MovementType movementType = MovementType.DOOR;

        // When
        final MetricsFilter metricsFilter = subject.convert(serviceProviderSid, dateRange, hubSid, paymentType, movementType);

        // Then
        assertThat(metricsFilter)
                .usingRecursiveComparison()
                .isEqualTo(MetricsFilter.builder()
                                   .serviceProviderSid(serviceProviderSid)
                                   .hubSid(hubSid)
                                   .dateRange(com.jumia.skylens.domain.catalog.DateRange.CURRENT_WEEK)
                                   .paymentType(com.jumia.skylens.domain.catalog.PaymentType.PRE)
                                   .movementType(com.jumia.skylens.domain.catalog.MovementType.DOOR)
                                   .build());
    }

    @Test
    void convert_whenCalledWithoutDateRange_convertSuccessfully() {

        // Given
        final UUID serviceProviderSid = UUID.randomUUID();
        final UUID hubSid = UUID.randomUUID();
        final PaymentType paymentType = PaymentType.PRE;
        final MovementType movementType = MovementType.DOOR;

        // When
        final MetricsFilter metricsFilter = subject.convert(serviceProviderSid, hubSid, paymentType, movementType);

        // Then
        assertThat(metricsFilter)
                .usingRecursiveComparison()
                .isEqualTo(MetricsFilter.builder()
                                   .serviceProviderSid(serviceProviderSid)
                                   .hubSid(hubSid)
                                   .paymentType(com.jumia.skylens.domain.catalog.PaymentType.PRE)
                                   .movementType(com.jumia.skylens.domain.catalog.MovementType.DOOR)
                                   .build());
    }
}

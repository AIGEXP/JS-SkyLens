package com.jumia.skylens.persistence.jpa.converters;

import com.jumia.skylens.domain.catalog.HubDailyMetric;
import com.jumia.skylens.domain.catalog.MovementType;
import com.jumia.skylens.domain.catalog.PaymentType;
import com.jumia.skylens.persistence.jpa.entities.HubDailyMetricEntity;
import com.jumia.skylens.persistence.jpa.entities.HubDailyMetricEntityId;
import com.jumia.skylens.persistence.jpa.fakers.Faker;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HubDailyMetricEntityConverterTest {

    private final HubDailyMetricEntityConverter subject = new HubDailyMetricEntityConverterImpl();

    private final Faker faker = new Faker();

    @Test
    void convert() {

        // Given
        final HubDailyMetric hubDailyMetric = faker.domain.hubDailyMetric()
                .paymentType(PaymentType.PRE)
                .movementType(MovementType.DOOR)
                .build();

        // When
        final HubDailyMetricEntity result = subject.convert(hubDailyMetric);

        // Then
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(HubDailyMetricEntity.builder()
                                   .id(HubDailyMetricEntityId.builder()
                                               .hubSid(hubDailyMetric.hubSid())
                                               .serviceProviderSid(hubDailyMetric.serviceProviderSid())
                                               .day(hubDailyMetric.day())
                                               .paymentType(HubDailyMetricEntityId.PaymentType.PRE)
                                               .movementType(HubDailyMetricEntityId.MovementType.DD)
                                               .build())
                                   .packagesDelivered(hubDailyMetric.packagesDelivered())
                                   .packagesClosed(hubDailyMetric.packagesClosed())
                                   .packagesReceived(hubDailyMetric.packagesReceived())
                                   .packagesLostAtHub(hubDailyMetric.packagesLostAtHub())
                                   .packagesNoAttemptsOneDay(hubDailyMetric.packagesNoAttemptsOneDay())
                                   .packagesNoAttemptsTwoDays(hubDailyMetric.packagesNoAttemptsTwoDays())
                                   .packagesNoAttemptsThreeDays(hubDailyMetric.packagesNoAttemptsThreeDays())
                                   .packagesNoAttemptsOverThreeDays(hubDailyMetric.packagesNoAttemptsOverThreeDays())
                                   .build());
    }
}

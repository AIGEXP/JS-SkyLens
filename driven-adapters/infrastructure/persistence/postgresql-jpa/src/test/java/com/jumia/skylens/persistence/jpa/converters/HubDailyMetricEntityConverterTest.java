package com.jumia.skylens.persistence.jpa.converters;

import com.jumia.skylens.domain.catalog.HubDailyMetric;
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
        final HubDailyMetric hubDailyMetric = faker.domain.hubDailyMetric().build();

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
                                               .movementType(HubDailyMetricEntityId.MovementType.valueOf(hubDailyMetric
                                                                                                            .movementType()
                                                                                                            .name()))
                                               .prePaid(hubDailyMetric.prePaid())
                                               .build())
                                   .country(hubDailyMetric.country())
                                   .is3PL(hubDailyMetric.is3PL())
                                   .packagesDelivered(hubDailyMetric.packagesDelivered())
                                   .packagesClosed(hubDailyMetric.packagesClosed())
                                   .packagesReceived(hubDailyMetric.packagesReceived())
                                   .packagesLostAtHub(hubDailyMetric.packagesLostAtHub())
                                   .packagesNoAttempts(hubDailyMetric.packagesNoAttempts())
                                   .packagesNoAttemptsOneDay(hubDailyMetric.packagesNoAttemptsOneDay())
                                   .packagesNoAttemptsTwoDays(hubDailyMetric.packagesNoAttemptsTwoDays())
                                   .packagesNoAttemptsThreeDays(hubDailyMetric.packagesNoAttemptsThreeDays())
                                   .packagesNoAttemptsFourDays(hubDailyMetric.packagesNoAttemptsFourDays())
                                   .packagesNoAttemptsOverFourDays(hubDailyMetric.packagesNoAttemptsOverFourDays())
                                   .build());
    }
}

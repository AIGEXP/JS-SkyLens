package com.jumia.skylens.persistence.jpa.fakers;

import com.jumia.skylens.persistence.jpa.entities.CountryThresholdEntity;
import com.jumia.skylens.persistence.jpa.entities.CountryThresholdEntityId;
import com.jumia.skylens.persistence.jpa.entities.HubDailyMetricEntity;
import com.jumia.skylens.persistence.jpa.entities.HubDailyMetricEntityId;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EntityFaker {

    private final net.datafaker.Faker faker = new net.datafaker.Faker();

    public HubDailyMetricEntityId.Builder hubDailyMetricEntityId() {

        return HubDailyMetricEntityId.builder()
                .serviceProviderSid(UUID.randomUUID())
                .hubSid(UUID.randomUUID())
                .day(faker.timeAndDate().birthday())
                .paymentType(faker.options().option(HubDailyMetricEntityId.PaymentType.class))
                .movementType(faker.options().option(HubDailyMetricEntityId.MovementType.class));
    }

    public HubDailyMetricEntity.Builder hubDailyMetricEntity() {

        return HubDailyMetricEntity.builder()
                .id(hubDailyMetricEntityId().build())
                .packagesDelivered(faker.number().randomDigitNotZero())
                .packagesClosed(faker.number().randomDigitNotZero())
                .packagesReceived(faker.number().randomDigitNotZero())
                .packagesLostAtHub(faker.number().randomDigitNotZero())
                .packagesNoAttemptsOneDay(faker.number().randomDigitNotZero())
                .packagesNoAttemptsTwoDays(faker.number().randomDigitNotZero())
                .packagesNoAttemptsThreeDays(faker.number().randomDigitNotZero())
                .packagesNoAttemptsOverThreeDays(faker.number().randomDigitNotZero());
    }

    public CountryThresholdEntityId.Builder countryThresholdEntityId() {

        return CountryThresholdEntityId.builder()
                .reportType(faker.options().option(CountryThresholdEntityId.ReportType.class))
                .country(faker.country().countryCode2());
    }

    public CountryThresholdEntity.Builder countryThresholdEntity() {

        return CountryThresholdEntity.builder()
                .id(countryThresholdEntityId().build())
                .value(BigDecimal.valueOf(faker.number().randomDouble(2, 0, 0)).setScale(2, RoundingMode.HALF_UP));
    }
}

package com.jumia.skylens.test.fakers;

import com.jumia.skylens.domain.catalog.HubDailyMetric;
import net.datafaker.Faker;

import java.time.LocalDate;
import java.util.UUID;

public class DomainFaker extends Faker {

    public HubDailyMetric.Builder hubDailyMetric() {

        return HubDailyMetric.builder()
                .country(country().countryCode2())
                .hubSid(UUID.randomUUID())
                .serviceProviderSid(UUID.randomUUID())
                .day(LocalDate.now())
                .is3PL(bool().bool())
                .prePaid(bool().bool())
                .movementType(options().option(HubDailyMetric.MovementType.class))
                .packagesDelivered(number().positive())
                .packagesClosed(number().positive())
                .packagesReceived(number().positive())
                .packagesLostAtHub(number().positive())
                .packagesNoAttempts(number().positive())
                .packagesNoAttemptsOneDay(number().positive())
                .packagesNoAttemptsTwoDays(number().positive())
                .packagesNoAttemptsThreeDays(number().positive())
                .packagesNoAttemptsFourDays(number().positive())
                .packagesNoAttemptsOverFourDays(number().positive());
    }
}

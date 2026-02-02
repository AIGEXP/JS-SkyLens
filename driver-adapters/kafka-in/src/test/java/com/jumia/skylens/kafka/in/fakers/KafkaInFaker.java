package com.jumia.skylens.kafka.in.fakers;

import com.jumia.skylens.kafka.in.skydrivers.driverupdated.dtos.HubDailyMetricDTO;
import net.datafaker.Faker;

import java.util.UUID;

public class KafkaInFaker extends Faker {

    public HubDailyMetricDTO.Builder hubDailyMetricDTO() {

        return HubDailyMetricDTO.builder()
                .country(country().countryCode2())
                .hubSid(UUID.randomUUID())
                .serviceProviderSid(UUID.randomUUID())
                .is3PL(bool().bool())
                .day("20250101")
                .prePaid(bool().bool())
                .movementType(options().option(HubDailyMetricDTO.MovementType.class))
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

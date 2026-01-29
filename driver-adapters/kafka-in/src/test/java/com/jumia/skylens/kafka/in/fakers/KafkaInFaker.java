package com.jumia.skylens.kafka.in.fakers;

import com.jumia.skylens.kafka.in.skydrivers.driverupdated.dtos.HubPerformanceMetricsDTO;
import net.datafaker.Faker;

import java.util.UUID;

public class KafkaInFaker extends Faker {

    public HubPerformanceMetricsDTO.Builder hubPerformanceMetricsDTO() {

        return HubPerformanceMetricsDTO.builder()
                .country(country().countryCode2())
                .hubSid(UUID.randomUUID())
                .serviceProviderSid(UUID.randomUUID())
                .is3PL(bool().bool())
                .day("20250101")
                .prePaid(bool().bool())
                .movementType(options().option(HubPerformanceMetricsDTO.MovementType.class))
                .packagesClosed(number().positive())
                .packagesReceived(number().positive())
                .packagesLostAtHub(number().positive())
                .packagesNoAttempt(number().positive())
                .packagesNoAttempt1Day(number().positive())
                .packagesNoAttempts2Days(number().positive())
                .packagesNoAttempts3Days(number().positive())
                .packagesNoAttempts4Days(number().positive())
                .packagesNoAttemptsMoreThan4Days(number().positive());
    }
}

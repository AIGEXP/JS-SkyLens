package com.jumia.skylens.kafka.in.fakers;

import com.jumia.skylens.kafka.in.bi.hubdailymetrics.dtos.HubDailyMetricDTO;
import net.datafaker.Faker;

import java.util.UUID;

public class KafkaInFaker extends Faker {

    public HubDailyMetricDTO.Builder hubDailyMetricDTO() {

        return HubDailyMetricDTO.builder()
                .hubSid(UUID.randomUUID())
                .serviceProviderSid(UUID.randomUUID())
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

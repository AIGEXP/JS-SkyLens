package com.jumia.skylens.domain.catalog;

import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public record HubDailyMetric(String country,
                                    UUID hubSid,
                                    UUID serviceProviderSid,
                                    boolean is3PL,
                                    LocalDate day,
                                    boolean prePaid,
                                    MovementType movementType,
                                    Integer packagesDelivered,
                                    Integer packagesClosed,
                                    Integer packagesReceived,
                                    Integer packagesLostAtHub,
                                    Integer packagesNoAttempts,
                                    Integer packagesNoAttemptsOneDay,
                                    Integer packagesNoAttemptsTwoDays,
                                    Integer packagesNoAttemptsThreeDays,
                                    Integer packagesNoAttemptsFourDays,
                                    Integer packagesNoAttemptsOverFourDays) {

    public enum MovementType {
        DD,
        PUS
    }
}

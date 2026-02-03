package com.jumia.skylens.domain.catalog;

import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public record HubDailyMetric(UUID hubSid,
                             UUID serviceProviderSid,
                             LocalDate day,
                             PaymentType paymentType,
                             MovementType movementType,
                             Integer packagesDelivered,
                             Integer packagesClosed,
                             Integer packagesReceived,
                             Integer packagesLostAtHub,
                             Integer packagesNoAttemptsOneDay,
                             Integer packagesNoAttemptsTwoDays,
                             Integer packagesNoAttemptsThreeDays,
                             Integer packagesNoAttemptsOverThreeDays) {

    public enum PaymentType {
        PRE,
        POST
    }

    public enum MovementType {
        DD,
        PUS
    }
}

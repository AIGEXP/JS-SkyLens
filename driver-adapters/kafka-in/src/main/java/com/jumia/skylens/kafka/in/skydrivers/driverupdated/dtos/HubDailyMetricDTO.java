package com.jumia.skylens.kafka.in.skydrivers.driverupdated.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.UUID;

@Builder
public record HubDailyMetricDTO(String country,
                                       @JsonProperty("hub_sid") UUID hubSid,
                                       @JsonProperty("service_provider_sid") UUID serviceProviderSid,
                                       @JsonProperty("3PL") boolean is3PL,
                                       String day,
                                       @JsonProperty("Pre_Paid") boolean prePaid,
                                       @JsonProperty("Movement_Type") MovementType movementType,
                                       @JsonProperty("Nr_packages_delivered") Integer packagesDelivered,
                                       @JsonProperty("Nr_packages_closed") Integer packagesClosed,
                                       @JsonProperty("Nr_packages_received") Integer packagesReceived,
                                       @JsonProperty("Nr_packages_lost_at_Hub") Integer packagesLostAtHub,
                                       @JsonProperty("Nr_packages_no_attempt") Integer packagesNoAttempts,
                                       @JsonProperty("Nr_packages_no_attempt_1D") Integer packagesNoAttemptsOneDay,
                                       @JsonProperty("Nr_packages_no_attempt_2D") Integer packagesNoAttemptsTwoDays,
                                       @JsonProperty("Nr_packages_no_attempt_3D") Integer packagesNoAttemptsThreeDays,
                                       @JsonProperty("Nr_packages_no_attempt_4D") Integer packagesNoAttemptsFourDays,
                                       @JsonProperty("Nr_packages_no_attempt_+4D") Integer packagesNoAttemptsOverFourDays) {

    public enum MovementType {
        DD,
        PUS
    }
}

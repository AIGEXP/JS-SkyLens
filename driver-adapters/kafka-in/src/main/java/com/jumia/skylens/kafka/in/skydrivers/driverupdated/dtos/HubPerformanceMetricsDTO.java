package com.jumia.skylens.kafka.in.skydrivers.driverupdated.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.UUID;

@Builder
public record HubPerformanceMetricsDTO(String country,
                                       @JsonProperty("hub_sid") UUID hubSid,
                                       @JsonProperty("service_provider_sid") UUID serviceProviderSid,
                                       @JsonProperty("3PL") boolean is3PL,
                                       String day,
                                       @JsonProperty("Pre_Paid") boolean prePaid,
                                       @JsonProperty("Movement_Type") MovementType movementType,
                                       @JsonProperty("Nr_packages_closed") Integer packagesClosed,
                                       @JsonProperty("Nr_packages_received") Integer packagesReceived,
                                       @JsonProperty("Nr_packages_lost_at_Hub") Integer packagesLostAtHub,
                                       @JsonProperty("Nr_packages_no_attempt") Integer packagesNoAttempt,
                                       @JsonProperty("Nr_packages_no_attempt_1D") Integer packagesNoAttempt1Day,
                                       @JsonProperty("Nr_packages_no_attempt_2D") Integer packagesNoAttempts2Days,
                                       @JsonProperty("Nr_packages_no_attempt_3D") Integer packagesNoAttempts3Days,
                                       @JsonProperty("Nr_packages_no_attempt_4D") Integer packagesNoAttempts4Days,
                                       @JsonProperty("Nr_packages_no_attempt_+4D") Integer packagesNoAttemptsMoreThan4Days) {

    public enum MovementType {
        DD,
        PUS
    }
}

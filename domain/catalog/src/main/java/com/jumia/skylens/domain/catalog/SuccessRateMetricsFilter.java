package com.jumia.skylens.domain.catalog;

import lombok.Builder;

import java.util.UUID;

@Builder
public record SuccessRateMetricsFilter(String country,
                                       UUID serviceProviderSid,
                                       UUID hubSid,
                                       DateRange dateRange,
                                       PaymentType paymentType,
                                       MovementType movementType) {

}

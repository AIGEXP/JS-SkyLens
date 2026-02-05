package com.jumia.skylens.domain.catalog;

import lombok.Builder;

import java.util.UUID;

@Builder
public record MetricsFilter(UUID serviceProviderSid,
                            UUID hubSid,
                            DateRange dateRange,
                            PaymentType paymentType,
                            MovementType movementType) {

}

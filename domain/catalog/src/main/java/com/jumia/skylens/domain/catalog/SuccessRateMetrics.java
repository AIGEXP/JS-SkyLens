package com.jumia.skylens.domain.catalog;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record SuccessRateMetrics(LocalDate date,
                                 int packagesDelivered,
                                 int packagesClosed,
                                 BigDecimal successRate,
                                 Boolean isWarning,
                                 Boolean isCritical) {

}

package com.jumia.skylens.domain.catalog;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record PackageMetrics(LocalDate date,
                             int packagesDelivered,
                             int packagesClosed,
                             int packagesReceived,
                             int packagesLostAtHub,
                             BigDecimal successRate,
                             BigDecimal lossRate) {

}

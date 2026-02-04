package com.jumia.skylens.domain.catalog;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record PackageStatistics(LocalDate date,
                                int packagesDelivered,
                                int packagesClosed,
                                int packagesReceived,
                                int packagesLostAtHub) {

}

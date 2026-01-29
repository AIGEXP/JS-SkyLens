package com.jumia.skylens.domain.catalog;

import com.jumia.skylens.domain.catalog.enums.Size;
import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public record PackageFilter(UUID partnerSid,
                            String trackingNumber,
                            String stopId,
                            String serviceCode,
                            Size size,
                            UUID zoneSid,
                            UUID paymentMethodSid,
                            String nodeName,
                            String driverName,
                            LocalDate dateFrom,
                            LocalDate dateTo) {

}

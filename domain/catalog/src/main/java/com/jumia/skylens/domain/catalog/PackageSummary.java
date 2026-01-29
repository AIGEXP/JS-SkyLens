package com.jumia.skylens.domain.catalog;

import com.jumia.skylens.domain.catalog.enums.ServiceCode;
import com.jumia.skylens.domain.catalog.enums.Size;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record PackageSummary(String trackingNumber,
                             String stopId,
                             LocalDate eventDate,
                             Size size,
                             ServiceCode serviceCode,
                             String zone,
                             String nodeName,
                             String paymentMethodName,
                             String driverName) {
}

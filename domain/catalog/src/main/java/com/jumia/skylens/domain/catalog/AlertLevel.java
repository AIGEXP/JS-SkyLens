package com.jumia.skylens.domain.catalog;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record AlertLevel(String country,
                         ReportType reportType,
                         BigDecimal warningValue,
                         BigDecimal criticalValue) {

}

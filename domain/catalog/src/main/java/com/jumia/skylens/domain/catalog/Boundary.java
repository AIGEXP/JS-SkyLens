package com.jumia.skylens.domain.catalog;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record Boundary(String country,
                       ReportType reportType,
                       BigDecimal warning,
                       BigDecimal critical) {

}

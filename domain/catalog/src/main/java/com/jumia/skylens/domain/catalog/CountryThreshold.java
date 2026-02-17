package com.jumia.skylens.domain.catalog;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record CountryThreshold(ReportType reportType,
                               String country,
                               BigDecimal value,
                               LocalDateTime updatedAt) {

}

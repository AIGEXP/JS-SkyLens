package com.jumia.skylens.domain.catalog;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record NetworkThreshold(ReportType reportType,
                               String network,
                               BigDecimal value,
                               LocalDateTime updatedAt) {

}

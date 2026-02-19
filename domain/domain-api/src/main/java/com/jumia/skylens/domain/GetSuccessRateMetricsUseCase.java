package com.jumia.skylens.domain;

import com.jumia.skylens.domain.catalog.SuccessRateMetrics;
import com.jumia.skylens.domain.catalog.SuccessRateMetricsFilter;

import java.util.List;

@FunctionalInterface
public interface GetSuccessRateMetricsUseCase {

    List<SuccessRateMetrics> run(SuccessRateMetricsFilter successRateMetricsFilter);
}

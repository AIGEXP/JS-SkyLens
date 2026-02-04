package com.jumia.skylens.domain;

import com.jumia.skylens.domain.catalog.MetricsFilter;
import com.jumia.skylens.domain.catalog.PackageMetrics;

import java.util.List;

@FunctionalInterface
public interface GetPackageMetricsUseCase {

    List<PackageMetrics> run(MetricsFilter metricsFilter);
}

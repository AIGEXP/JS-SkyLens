package com.jumia.skylens.domain;

import com.jumia.skylens.domain.catalog.MetricsFilter;
import com.jumia.skylens.domain.catalog.PackageNoAttemptsStatistics;

@FunctionalInterface
public interface GetCurrentPackageAttemptsMetricsUseCase {

    PackageNoAttemptsStatistics run(final MetricsFilter metricsFilter);
}

package com.jumia.skylens.domain.impl;

import com.jumia.skylens.domain.GetCurrentPackageAttemptsMetricsUseCase;
import com.jumia.skylens.domain.catalog.MetricsFilter;
import com.jumia.skylens.domain.catalog.PackageNoAttemptsStatistics;
import com.jumia.skylens.persistence.api.HubDailyMetricDAO;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetCurrentPackageAttemptsMetricsUseCaseImpl implements GetCurrentPackageAttemptsMetricsUseCase {

    private final HubDailyMetricDAO hubDailyMetricDAO;

    @Override
    public PackageNoAttemptsStatistics run(final MetricsFilter metricsFilter) {

        return hubDailyMetricDAO.findCurrentNoAttemptsStatistics(metricsFilter.serviceProviderSid(),
                                                                 metricsFilter.hubSid(),
                                                                 metricsFilter.paymentType(),
                                                                 metricsFilter.movementType());
    }
}

package com.jumia.skylens.domain.impl;

import com.jumia.skylens.domain.GetPackageMetricsUseCase;
import com.jumia.skylens.domain.catalog.MetricsFilter;
import com.jumia.skylens.domain.catalog.PackageMetrics;
import com.jumia.skylens.domain.catalog.PackageStatistics;
import com.jumia.skylens.persistence.api.HubDailyMetricDAO;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GetPackageMetricsUseCaseImpl implements GetPackageMetricsUseCase {

    private final HubDailyMetricDAO hubDailyMetricDAO;

    @Override
    public List<PackageMetrics> run(final MetricsFilter metricsFilter) {

        final List<PackageStatistics> metricsByDay = hubDailyMetricDAO.getPackageStatistics(metricsFilter.serviceProviderSid(),
                                                                                            metricsFilter.hubSid(),
                                                                                            metricsFilter.dateRange().startDate(),
                                                                                            metricsFilter.dateRange().endDate(),
                                                                                            metricsFilter.paymentType(),
                                                                                            metricsFilter.movementType(),
                                                                                            metricsFilter.dateRange().granularity());

        return metricsByDay.stream()
                .map(packageStatistics -> PackageMetrics.builder()
                        .date(packageStatistics.date())
                        .packagesDelivered(packageStatistics.packagesDelivered())
                        .packagesClosed(packageStatistics.packagesClosed())
                        .packagesReceived(packageStatistics.packagesReceived())
                        .packagesLostAtHub(packageStatistics.packagesLostAtHub())
                        .successRate(packageStatistics.packagesClosed() > 0
                                             ? (double) packageStatistics.packagesDelivered() / packageStatistics.packagesClosed()
                                             : null)
                        .lossRate(packageStatistics.packagesReceived() > 0
                                          ? (double) packageStatistics.packagesLostAtHub() / packageStatistics.packagesReceived()
                                          : null)
                        .build())
                .toList();
    }
}

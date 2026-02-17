package com.jumia.skylens.domain.impl;

import com.jumia.skylens.domain.GetPackageMetricsUseCase;
import com.jumia.skylens.domain.catalog.MetricsFilter;
import com.jumia.skylens.domain.catalog.PackageMetrics;
import com.jumia.skylens.domain.catalog.PackageStatistics;
import com.jumia.skylens.persistence.api.HubDailyMetricDAO;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@RequiredArgsConstructor
public class GetPackageMetricsUseCaseImpl implements GetPackageMetricsUseCase {

    private static final int SCALE = 4;

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
                        .successRate(calculateSuccessRate(packageStatistics.packagesDelivered(), packageStatistics.packagesClosed()))
                        .lossRate(calculateLossRate(packageStatistics.packagesLostAtHub(), packageStatistics.packagesReceived()))
                        .build())
                .toList();
    }

    private BigDecimal calculateSuccessRate(int packagesDelivered, int packagesClosed) {

        if (packagesClosed == 0) {
            return null;
        }

        return BigDecimal.valueOf(packagesDelivered).divide(BigDecimal.valueOf(packagesClosed), SCALE, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateLossRate(int packagesLostAtHub, int packagesReceived) {

        if (packagesReceived == 0) {
            return null;
        }

        return BigDecimal.valueOf(packagesLostAtHub).divide(BigDecimal.valueOf(packagesReceived), SCALE, RoundingMode.HALF_UP);
    }
}

package com.jumia.skylens.domain.impl;

import com.jumia.skylens.domain.GetSuccessRateMetricsUseCase;
import com.jumia.skylens.domain.catalog.AlertLevel;
import com.jumia.skylens.domain.catalog.DateRange;
import com.jumia.skylens.domain.catalog.PackageStatistics;
import com.jumia.skylens.domain.catalog.ReportType;
import com.jumia.skylens.domain.catalog.SuccessRateMetrics;
import com.jumia.skylens.domain.catalog.SuccessRateMetricsFilter;
import com.jumia.skylens.persistence.api.AlertLevelDAO;
import com.jumia.skylens.persistence.api.HubDailyMetricDAO;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@RequiredArgsConstructor
public class GetSuccessRateMetricsUseCaseImpl implements GetSuccessRateMetricsUseCase {

    private static final int SCALE = 4;

    private final HubDailyMetricDAO hubDailyMetricDAO;

    private final AlertLevelDAO alertLevelDAO;

    @Override
    public List<SuccessRateMetrics> run(final SuccessRateMetricsFilter successRateMetricsFilter) {

        final DateRange dateRange = successRateMetricsFilter.dateRange();

        final List<PackageStatistics> metricsByDay = hubDailyMetricDAO.getPackageStatistics(successRateMetricsFilter.serviceProviderSid(),
                                                                                            successRateMetricsFilter.hubSid(),
                                                                                            dateRange.startDate(),
                                                                                            dateRange.endDate(),
                                                                                            successRateMetricsFilter.paymentType(),
                                                                                            successRateMetricsFilter.movementType(),
                                                                                            dateRange.granularity());

        final AlertLevel alertLevel = alertLevelDAO.findByCountryAndReportType(successRateMetricsFilter.country(),
                                                                               ReportType.SUCCESS_RATE)
                .orElse(null);

        return metricsByDay.stream()
                .map(packageStatistics -> buildSuccessRateMetrics(packageStatistics, alertLevel))
                .toList();
    }

    private SuccessRateMetrics buildSuccessRateMetrics(final PackageStatistics packageStatistics, final AlertLevel alertLevel) {

        final BigDecimal successRate = calculateSuccessRate(packageStatistics.packagesDelivered(),
                                                            packageStatistics.packagesClosed());

        return SuccessRateMetrics.builder()
                .date(packageStatistics.date())
                .packagesDelivered(packageStatistics.packagesDelivered())
                .packagesClosed(packageStatistics.packagesClosed())
                .successRate(successRate)
                .isWarning(alertLevel != null ? alertLevel.warningValue().compareTo(successRate) > 0 : null)
                .isCritical(alertLevel != null ? alertLevel.criticalValue().compareTo(successRate) > 0 : null)
                .build();
    }

    private BigDecimal calculateSuccessRate(int packagesDelivered, int packagesClosed) {

        if (packagesClosed == 0) {
            return null;
        }

        return BigDecimal.valueOf(packagesDelivered).divide(BigDecimal.valueOf(packagesClosed), SCALE, RoundingMode.HALF_UP);
    }
}

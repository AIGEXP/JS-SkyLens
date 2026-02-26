package com.jumia.skylens.domain.impl;

import com.jumia.skylens.domain.catalog.AlertLevel;
import com.jumia.skylens.domain.catalog.PackageStatistics;
import com.jumia.skylens.domain.catalog.ReportType;
import com.jumia.skylens.domain.catalog.SuccessRateMetrics;
import com.jumia.skylens.domain.catalog.SuccessRateMetricsFilter;
import com.jumia.skylens.persistence.api.AlertLevelDAO;
import com.jumia.skylens.persistence.api.HubDailyMetricDAO;
import com.jumia.skylens.test.fakers.DomainFaker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetSuccessRateMetricsUseCaseImplTest {

    private final DomainFaker faker = new DomainFaker();

    @Mock
    private HubDailyMetricDAO hubDailyMetricDAO;

    @Mock
    private AlertLevelDAO alertLevelDAO;

    @InjectMocks
    private GetSuccessRateMetricsUseCaseImpl subject;

    @Test
    void run_whenCalled_thenReturnExpectedMetrics() {

        // Given
        final SuccessRateMetricsFilter successRateMetricsFilter = faker.successRateMetricsFilter().build();
        final PackageStatistics packageStatistics = faker.packageStatistics().build();

        when(hubDailyMetricDAO.getPackageStatistics(any(),
                                                    any(),
                                                    any(),
                                                    any(),
                                                    any(),
                                                    any(),
                                                    any())).thenReturn(List.of(packageStatistics));
        when(alertLevelDAO.findByCountryAndReportType(any(), any())).thenReturn(Optional.empty());

        // When
        final List<SuccessRateMetrics> successRateMetrics = subject.run(successRateMetricsFilter);

        // Then
        assertThat(successRateMetrics)
                .singleElement()
                .usingRecursiveComparison()
                .isEqualTo(SuccessRateMetrics.builder()
                                   .date(packageStatistics.date())
                                   .packagesDelivered(packageStatistics.packagesDelivered())
                                   .packagesClosed(packageStatistics.packagesClosed())
                                   .successRate(BigDecimal.valueOf(packageStatistics.packagesDelivered())
                                                        .divide(BigDecimal.valueOf(packageStatistics.packagesClosed()),
                                                                4,
                                                                RoundingMode.HALF_UP))
                                   .build());

        verify(hubDailyMetricDAO).getPackageStatistics(successRateMetricsFilter.serviceProviderSid(),
                                                       successRateMetricsFilter.hubSid(),
                                                       successRateMetricsFilter.dateRange().startDate(),
                                                       successRateMetricsFilter.dateRange().endDate(),
                                                       successRateMetricsFilter.paymentType(),
                                                       successRateMetricsFilter.movementType(),
                                                       successRateMetricsFilter.dateRange().granularity());
        verify(alertLevelDAO).findByCountryAndReportType(successRateMetricsFilter.country(), ReportType.SUCCESS_RATE);
    }

    @Test
    void run_whenPackagesClosedIsZero_thenReturnNullSuccessRate() {

        // Given
        final SuccessRateMetricsFilter successRateMetricsFilter = faker.successRateMetricsFilter().build();
        final PackageStatistics packageStatistics = faker.packageStatistics().packagesClosed(0).build();

        when(hubDailyMetricDAO.getPackageStatistics(any(),
                                                    any(),
                                                    any(),
                                                    any(),
                                                    any(),
                                                    any(),
                                                    any())).thenReturn(List.of(packageStatistics));
        when(alertLevelDAO.findByCountryAndReportType(any(), any())).thenReturn(Optional.empty());

        // When
        final List<SuccessRateMetrics> successRateMetrics = subject.run(successRateMetricsFilter);

        // Then
        assertThat(successRateMetrics)
                .singleElement()
                .extracting(SuccessRateMetrics::successRate)
                .isNull();

        verify(hubDailyMetricDAO).getPackageStatistics(successRateMetricsFilter.serviceProviderSid(),
                                                       successRateMetricsFilter.hubSid(),
                                                       successRateMetricsFilter.dateRange().startDate(),
                                                       successRateMetricsFilter.dateRange().endDate(),
                                                       successRateMetricsFilter.paymentType(),
                                                       successRateMetricsFilter.movementType(),
                                                       successRateMetricsFilter.dateRange().granularity());
        verify(alertLevelDAO).findByCountryAndReportType(successRateMetricsFilter.country(), ReportType.SUCCESS_RATE);
    }

    @Test
    void run_whenSuccessRateIsAboveWarningLevel_thenIsWarningAndIsCriticalAreFalse() {

        // Given
        final SuccessRateMetricsFilter successRateMetricsFilter = faker.successRateMetricsFilter().build();
        final PackageStatistics packageStatistics = faker.packageStatistics()
                .packagesDelivered(90)
                .packagesClosed(100)
                .build();
        final AlertLevel alertLevel = faker.alertLevel()
                .warningValue(BigDecimal.valueOf(0.8))
                .criticalValue(BigDecimal.valueOf(0.7))
                .build();

        when(hubDailyMetricDAO.getPackageStatistics(any(),
                                                    any(),
                                                    any(),
                                                    any(),
                                                    any(),
                                                    any(),
                                                    any())).thenReturn(List.of(packageStatistics));
        when(alertLevelDAO.findByCountryAndReportType(any(), any())).thenReturn(Optional.of(alertLevel));

        // When
        final List<SuccessRateMetrics> successRateMetrics = subject.run(successRateMetricsFilter);

        // Then
        assertThat(successRateMetrics)
                .singleElement()
                .satisfies(successRateMetric -> {
                    assertThat(successRateMetric.isWarning()).isFalse();
                    assertThat(successRateMetric.isCritical()).isFalse();
                });

        verify(hubDailyMetricDAO).getPackageStatistics(successRateMetricsFilter.serviceProviderSid(),
                                                       successRateMetricsFilter.hubSid(),
                                                       successRateMetricsFilter.dateRange().startDate(),
                                                       successRateMetricsFilter.dateRange().endDate(),
                                                       successRateMetricsFilter.paymentType(),
                                                       successRateMetricsFilter.movementType(),
                                                       successRateMetricsFilter.dateRange().granularity());
        verify(alertLevelDAO).findByCountryAndReportType(successRateMetricsFilter.country(), ReportType.SUCCESS_RATE);
    }

    @Test
    void run_whenSuccessRateIsAboveCriticalLevelButBellowWarningLevel_thenIsWarningIsTrueAndIsCriticalIsFalse() {

        // Given
        final SuccessRateMetricsFilter successRateMetricsFilter = faker.successRateMetricsFilter().build();
        final PackageStatistics packageStatistics = faker.packageStatistics()
                .packagesDelivered(75)
                .packagesClosed(100)
                .build();
        final AlertLevel alertLevel = faker.alertLevel()
                .warningValue(BigDecimal.valueOf(0.8))
                .criticalValue(BigDecimal.valueOf(0.7))
                .build();

        when(hubDailyMetricDAO.getPackageStatistics(any(),
                                                    any(),
                                                    any(),
                                                    any(),
                                                    any(),
                                                    any(),
                                                    any())).thenReturn(List.of(packageStatistics));
        when(alertLevelDAO.findByCountryAndReportType(any(), any())).thenReturn(Optional.of(alertLevel));

        // When
        final List<SuccessRateMetrics> successRateMetrics = subject.run(successRateMetricsFilter);

        // Then
        assertThat(successRateMetrics)
                .singleElement()
                .satisfies(successRateMetric -> {
                    assertThat(successRateMetric.isWarning()).isTrue();
                    assertThat(successRateMetric.isCritical()).isFalse();
                });

        verify(hubDailyMetricDAO).getPackageStatistics(successRateMetricsFilter.serviceProviderSid(),
                                                       successRateMetricsFilter.hubSid(),
                                                       successRateMetricsFilter.dateRange().startDate(),
                                                       successRateMetricsFilter.dateRange().endDate(),
                                                       successRateMetricsFilter.paymentType(),
                                                       successRateMetricsFilter.movementType(),
                                                       successRateMetricsFilter.dateRange().granularity());
        verify(alertLevelDAO).findByCountryAndReportType(successRateMetricsFilter.country(), ReportType.SUCCESS_RATE);
    }

    @Test
    void run_whenSuccessRateIsBellowCriticalLeve_thenIsWarningAndIsCriticalAreTrue() {

        // Given
        final SuccessRateMetricsFilter successRateMetricsFilter = faker.successRateMetricsFilter().build();
        final PackageStatistics packageStatistics = faker.packageStatistics()
                .packagesDelivered(60)
                .packagesClosed(100)
                .build();
        final AlertLevel alertLevel = faker.alertLevel()
                .warningValue(BigDecimal.valueOf(0.8))
                .criticalValue(BigDecimal.valueOf(0.7))
                .build();

        when(hubDailyMetricDAO.getPackageStatistics(any(),
                                                    any(),
                                                    any(),
                                                    any(),
                                                    any(),
                                                    any(),
                                                    any())).thenReturn(List.of(packageStatistics));
        when(alertLevelDAO.findByCountryAndReportType(any(), any())).thenReturn(Optional.of(alertLevel));

        // When
        final List<SuccessRateMetrics> successRateMetrics = subject.run(successRateMetricsFilter);

        // Then
        assertThat(successRateMetrics)
                .singleElement()
                .satisfies(successRateMetric -> {
                    assertThat(successRateMetric.isWarning()).isTrue();
                    assertThat(successRateMetric.isCritical()).isTrue();
                });

        verify(hubDailyMetricDAO).getPackageStatistics(successRateMetricsFilter.serviceProviderSid(),
                                                       successRateMetricsFilter.hubSid(),
                                                       successRateMetricsFilter.dateRange().startDate(),
                                                       successRateMetricsFilter.dateRange().endDate(),
                                                       successRateMetricsFilter.paymentType(),
                                                       successRateMetricsFilter.movementType(),
                                                       successRateMetricsFilter.dateRange().granularity());
        verify(alertLevelDAO).findByCountryAndReportType(successRateMetricsFilter.country(), ReportType.SUCCESS_RATE);
    }
}

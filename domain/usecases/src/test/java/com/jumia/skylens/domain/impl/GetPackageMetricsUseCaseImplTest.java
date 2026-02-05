package com.jumia.skylens.domain.impl;

import com.jumia.skylens.domain.catalog.MetricsFilter;
import com.jumia.skylens.domain.catalog.PackageMetrics;
import com.jumia.skylens.domain.catalog.PackageStatistics;
import com.jumia.skylens.persistence.api.HubDailyMetricDAO;
import com.jumia.skylens.test.fakers.DomainFaker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetPackageMetricsUseCaseImplTest {

    private final DomainFaker faker = new DomainFaker();

    @Mock
    private HubDailyMetricDAO hubDailyMetricDAO;

    @InjectMocks
    private GetPackageMetricsUseCaseImpl subject;

    @Test
    void run_whenCalled_thenReturnExpectedMetrics() {

        // Given
        final MetricsFilter metricsFilter = faker.metricsFilter().build();
        final PackageStatistics packageStatistics = faker.packageStatistics().build();

        when(hubDailyMetricDAO.getPackageStatistics(any(),
                                                    any(),
                                                    any(),
                                                    any(),
                                                    any(),
                                                    any(),
                                                    any())).thenReturn(List.of(packageStatistics));

        // When
        final List<PackageMetrics> packageMetrics = subject.run(metricsFilter);

        // Then
        assertThat(packageMetrics)
                .singleElement()
                .usingRecursiveComparison()
                .isEqualTo(PackageMetrics.builder()
                                   .date(packageStatistics.date())
                                   .packagesDelivered(packageStatistics.packagesDelivered())
                                   .packagesClosed(packageStatistics.packagesClosed())
                                   .packagesReceived(packageStatistics.packagesReceived())
                                   .packagesLostAtHub(packageStatistics.packagesLostAtHub())
                                   .successRate((double) packageStatistics.packagesDelivered() / packageStatistics.packagesClosed())
                                   .lossRate((double) packageStatistics.packagesLostAtHub() / packageStatistics.packagesReceived())
                                   .build());

        verify(hubDailyMetricDAO).getPackageStatistics(metricsFilter.serviceProviderSid(),
                                                       metricsFilter.hubSid(),
                                                       metricsFilter.dateRange().startDate(),
                                                       metricsFilter.dateRange().endDate(),
                                                       metricsFilter.paymentType(),
                                                       metricsFilter.movementType(),
                                                       metricsFilter.dateRange().granularity());
    }

    @Test
    void run_whenPackagesClosedIsZero_thenReturnNullSuccessRate() {

        // Given
        final MetricsFilter metricsFilter = faker.metricsFilter().build();
        final PackageStatistics packageStatistics = faker.packageStatistics().packagesClosed(0).build();

        when(hubDailyMetricDAO.getPackageStatistics(any(),
                                                    any(),
                                                    any(),
                                                    any(),
                                                    any(),
                                                    any(),
                                                    any())).thenReturn(List.of(packageStatistics));

        // When
        final List<PackageMetrics> packageMetrics = subject.run(metricsFilter);

        // Then
        assertThat(packageMetrics)
                .singleElement()
                .extracting(PackageMetrics::successRate)
                .isNull();

        verify(hubDailyMetricDAO).getPackageStatistics(metricsFilter.serviceProviderSid(),
                                                       metricsFilter.hubSid(),
                                                       metricsFilter.dateRange().startDate(),
                                                       metricsFilter.dateRange().endDate(),
                                                       metricsFilter.paymentType(),
                                                       metricsFilter.movementType(),
                                                       metricsFilter.dateRange().granularity());
    }

    @Test
    void run_whenPackagesReceivedIsZero_thenReturnNullLossRate() {

        // Given
        final MetricsFilter metricsFilter = faker.metricsFilter().build();
        final PackageStatistics packageStatistics = faker.packageStatistics().packagesReceived(0).build();

        when(hubDailyMetricDAO.getPackageStatistics(any(),
                                                    any(),
                                                    any(),
                                                    any(),
                                                    any(),
                                                    any(),
                                                    any())).thenReturn(List.of(packageStatistics));

        // When
        final List<PackageMetrics> packageMetrics = subject.run(metricsFilter);

        // Then
        assertThat(packageMetrics)
                .singleElement()
                .extracting(PackageMetrics::lossRate)
                .isNull();

        verify(hubDailyMetricDAO).getPackageStatistics(metricsFilter.serviceProviderSid(),
                                                       metricsFilter.hubSid(),
                                                       metricsFilter.dateRange().startDate(),
                                                       metricsFilter.dateRange().endDate(),
                                                       metricsFilter.paymentType(),
                                                       metricsFilter.movementType(),
                                                       metricsFilter.dateRange().granularity());
    }
}

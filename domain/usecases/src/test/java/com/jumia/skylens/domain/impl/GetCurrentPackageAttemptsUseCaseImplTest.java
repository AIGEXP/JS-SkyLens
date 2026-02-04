package com.jumia.skylens.domain.impl;

import com.jumia.skylens.domain.catalog.MetricsFilter;
import com.jumia.skylens.domain.catalog.PackageNoAttemptsStatistics;
import com.jumia.skylens.persistence.api.HubDailyMetricDAO;
import com.jumia.skylens.test.fakers.DomainFaker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetCurrentPackageAttemptsUseCaseImplTest {

    private final DomainFaker faker = new DomainFaker();

    @Mock
    private HubDailyMetricDAO hubDailyMetricDAO;

    @InjectMocks
    private GetCurrentPackageAttemptsMetricsUseCaseImpl subject;

    @Test
    void run_whenCalled_thenCallDAO() {

        // Given
        final MetricsFilter metricsFilter = faker.metricsFilter().build();
        final PackageNoAttemptsStatistics packageNoAttemptsStatistics = mock(PackageNoAttemptsStatistics.class);

        when(hubDailyMetricDAO.findCurrentNoAttemptsStatistics(any(), any(), any(), any())).thenReturn(packageNoAttemptsStatistics);

        // When
        final PackageNoAttemptsStatistics packageNoAttemptsStatisticsReturned = subject.run(metricsFilter);

        // Then
        assertThat(packageNoAttemptsStatisticsReturned)
                .usingRecursiveComparison()
                .isEqualTo(packageNoAttemptsStatistics);

        verify(hubDailyMetricDAO).findCurrentNoAttemptsStatistics(metricsFilter.serviceProviderSid(),
                                                                  metricsFilter.hubSid(),
                                                                  metricsFilter.paymentType(),
                                                                  metricsFilter.movementType());
    }
}

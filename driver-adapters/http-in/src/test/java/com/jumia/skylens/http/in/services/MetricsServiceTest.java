package com.jumia.skylens.http.in.services;

import com.jumia.skylens.domain.GetCurrentPackageAttemptsMetricsUseCase;
import com.jumia.skylens.domain.GetPackageMetricsUseCase;
import com.jumia.skylens.domain.GetSuccessRateMetricsUseCase;
import com.jumia.skylens.domain.catalog.MetricsFilter;
import com.jumia.skylens.domain.catalog.PackageMetrics;
import com.jumia.skylens.domain.catalog.PackageNoAttemptsStatistics;
import com.jumia.skylens.domain.catalog.SuccessRateMetrics;
import com.jumia.skylens.domain.catalog.SuccessRateMetricsFilter;
import com.jumia.skylens.http.in.converters.DeliveryMetricResponseConverter;
import com.jumia.skylens.http.in.converters.LossRateMetricResponseConverter;
import com.jumia.skylens.http.in.converters.MetricsFilterConverter;
import com.jumia.skylens.http.in.converters.NoAttemptsMetricResponseConverter;
import com.jumia.skylens.http.in.converters.SuccessRateMetricResponseConverter;
import com.jumia.skylens.http.in.converters.SuccessRateMetricResponseDeprecatedConverter;
import com.jumia.skylens.http.in.converters.SuccessRateMetricsFilterConverter;
import com.jumia.skylens.http.in.model.DateRange;
import com.jumia.skylens.http.in.model.DeliveryMetricsResponseInner;
import com.jumia.skylens.http.in.model.LossRateMetricsResponseInner;
import com.jumia.skylens.http.in.model.MovementType;
import com.jumia.skylens.http.in.model.NoAttemptsMetricsResponse;
import com.jumia.skylens.http.in.model.PaymentType;
import com.jumia.skylens.http.in.model.SuccessRateMetricsResponseDeprecatedInner;
import com.jumia.skylens.http.in.model.SuccessRateMetricsResponseInner;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MetricsServiceTest {

    @Mock
    private GetPackageMetricsUseCase getPackageMetricsUseCase;

    @Mock
    private GetSuccessRateMetricsUseCase getSuccessRateMetricsUseCase;

    @Mock
    private GetCurrentPackageAttemptsMetricsUseCase getCurrentPackageAttemptsMetricsUseCase;

    @Mock
    private MetricsFilterConverter metricsFilterConverter;

    @Mock
    private SuccessRateMetricsFilterConverter successRateMetricsFilterConverter;

    @Mock
    private DeliveryMetricResponseConverter deliveryMetricResponseConverter;

    @Mock
    private SuccessRateMetricResponseDeprecatedConverter successRateMetricResponseDeprecatedConverter;

    @Mock
    private SuccessRateMetricResponseConverter successRateMetricResponseConverter;

    @Mock
    private LossRateMetricResponseConverter lossRateMetricResponseConverter;

    @Mock
    private NoAttemptsMetricResponseConverter noAttemptsMetricResponseConverter;

    @InjectMocks
    private MetricsService subject;

    @Test
    void getPackageDeliveries_whenCalled_thenCallUseCase() {

        // Given
        final UUID serviceProviderSid = UUID.randomUUID();
        final DateRange dateRange = DateRange.CURRENT_WEEK;
        final UUID hubSid = UUID.randomUUID();
        final PaymentType paymentType = PaymentType.PRE;
        final MovementType movementType = MovementType.DOOR;
        final MetricsFilter metricsFilter = mock(MetricsFilter.class);
        final PackageMetrics packageMetrics = mock(PackageMetrics.class);
        final DeliveryMetricsResponseInner deliveryMetricsResponse = mock(DeliveryMetricsResponseInner.class);

        when(metricsFilterConverter.convert(any(), any(), any(), any(), any())).thenReturn(metricsFilter);
        when(getPackageMetricsUseCase.run(any())).thenReturn(List.of(packageMetrics));
        when(deliveryMetricResponseConverter.convert(any())).thenReturn(deliveryMetricsResponse);

        // When
        final List<DeliveryMetricsResponseInner> packageDeliveries = subject.getPackageDeliveries(serviceProviderSid,
                                                                                                  dateRange,
                                                                                                  hubSid,
                                                                                                  paymentType,
                                                                                                  movementType);

        // Then
        assertThat(packageDeliveries)
                .containsExactly(deliveryMetricsResponse);

        verify(metricsFilterConverter).convert(serviceProviderSid, dateRange, hubSid, paymentType, movementType);
        verify(getPackageMetricsUseCase).run(metricsFilter);
        verify(deliveryMetricResponseConverter).convert(packageMetrics);
    }

    @Test
    void getSuccessRateDeprecated_whenCalled_thenCallUseCase() {

        // Given
        final UUID serviceProviderSid = UUID.randomUUID();
        final DateRange dateRange = DateRange.CURRENT_WEEK;
        final UUID hubSid = UUID.randomUUID();
        final PaymentType paymentType = PaymentType.PRE;
        final MovementType movementType = MovementType.DOOR;
        final MetricsFilter metricsFilter = mock(MetricsFilter.class);
        final PackageMetrics packageMetrics = mock(PackageMetrics.class);
        final SuccessRateMetricsResponseDeprecatedInner successRate = mock(SuccessRateMetricsResponseDeprecatedInner.class);

        when(metricsFilterConverter.convert(any(), any(), any(), any(), any())).thenReturn(metricsFilter);
        when(getPackageMetricsUseCase.run(any())).thenReturn(List.of(packageMetrics));
        when(successRateMetricResponseDeprecatedConverter.convert(any())).thenReturn(successRate);

        // When
        final List<SuccessRateMetricsResponseDeprecatedInner> successRates = subject.getSuccessRateDeprecated(serviceProviderSid,
                                                                                                              dateRange,
                                                                                                              hubSid,
                                                                                                              paymentType,
                                                                                                              movementType);

        // Then
        assertThat(successRates)
                .containsExactly(successRate);

        verify(metricsFilterConverter).convert(serviceProviderSid, dateRange, hubSid, paymentType, movementType);
        verify(getPackageMetricsUseCase).run(metricsFilter);
        verify(successRateMetricResponseDeprecatedConverter).convert(packageMetrics);
    }

    @Test
    void getSuccessRate_whenCalled_thenCallUseCase() {

        // Given
        final String country = "CI";
        final UUID serviceProviderSid = UUID.randomUUID();
        final DateRange dateRange = DateRange.CURRENT_WEEK;
        final UUID hubSid = UUID.randomUUID();
        final PaymentType paymentType = PaymentType.PRE;
        final MovementType movementType = MovementType.DOOR;
        final SuccessRateMetricsFilter successRateMetricsFilter = mock(SuccessRateMetricsFilter.class);
        final SuccessRateMetrics successRateMetrics = mock(SuccessRateMetrics.class);
        final SuccessRateMetricsResponseInner successRate = mock(SuccessRateMetricsResponseInner.class);

        when(successRateMetricsFilterConverter.convert(any(), any(), any(), any(), any(), any())).thenReturn(successRateMetricsFilter);
        when(getSuccessRateMetricsUseCase.run(any())).thenReturn(List.of(successRateMetrics));
        when(successRateMetricResponseConverter.convert(any())).thenReturn(successRate);

        // When
        final List<SuccessRateMetricsResponseInner> successRates = subject.getSuccessRate(country,
                                                                                          serviceProviderSid,
                                                                                          dateRange,
                                                                                          hubSid,
                                                                                          paymentType,
                                                                                          movementType);

        // Then
        assertThat(successRates)
                .containsExactly(successRate);

        verify(successRateMetricsFilterConverter).convert(country, serviceProviderSid, dateRange, hubSid, paymentType, movementType);
        verify(getSuccessRateMetricsUseCase).run(successRateMetricsFilter);
        verify(successRateMetricResponseConverter).convert(successRateMetrics);
    }

    @Test
    void getLossRate_whenCalled_thenCallUseCase() {

        // Given
        final UUID serviceProviderSid = UUID.randomUUID();
        final DateRange dateRange = DateRange.CURRENT_WEEK;
        final UUID hubSid = UUID.randomUUID();
        final PaymentType paymentType = PaymentType.PRE;
        final MovementType movementType = MovementType.DOOR;
        final MetricsFilter metricsFilter = mock(MetricsFilter.class);
        final PackageMetrics packageMetrics = mock(PackageMetrics.class);
        final LossRateMetricsResponseInner lossRate = mock(LossRateMetricsResponseInner.class);

        when(metricsFilterConverter.convert(any(), any(), any(), any(), any())).thenReturn(metricsFilter);
        when(getPackageMetricsUseCase.run(any())).thenReturn(List.of(packageMetrics));
        when(lossRateMetricResponseConverter.convert(any())).thenReturn(lossRate);

        // When
        final List<LossRateMetricsResponseInner> lossRates = subject.getLossRate(serviceProviderSid,
                                                                                 dateRange,
                                                                                 hubSid,
                                                                                 paymentType,
                                                                                 movementType);

        // Then
        assertThat(lossRates)
                .containsExactly(lossRate);

        verify(metricsFilterConverter).convert(serviceProviderSid, dateRange, hubSid, paymentType, movementType);
        verify(getPackageMetricsUseCase).run(metricsFilter);
        verify(lossRateMetricResponseConverter).convert(packageMetrics);
    }

    @Test
    void getNoAttempts_whenCalled_thenCallUseCase() {

        // Given
        final UUID serviceProviderSid = UUID.randomUUID();
        final UUID hubSid = UUID.randomUUID();
        final PaymentType paymentType = PaymentType.PRE;
        final MovementType movementType = MovementType.DOOR;
        final MetricsFilter metricsFilter = mock(MetricsFilter.class);
        final PackageNoAttemptsStatistics packageNoAttemptsStatistics = mock(PackageNoAttemptsStatistics.class);
        final NoAttemptsMetricsResponse noAttemptsMetricsResponse = mock(NoAttemptsMetricsResponse.class);

        when(metricsFilterConverter.convert(any(), any(), any(), any())).thenReturn(metricsFilter);
        when(getCurrentPackageAttemptsMetricsUseCase.run(any())).thenReturn(packageNoAttemptsStatistics);
        when(noAttemptsMetricResponseConverter.convert(any())).thenReturn(noAttemptsMetricsResponse);

        // When
        final NoAttemptsMetricsResponse noAttempts = subject.getNoAttempts(serviceProviderSid,
                                                                           hubSid,
                                                                           paymentType,
                                                                           movementType);

        // Then
        assertThat(noAttempts)
                .isEqualTo(noAttemptsMetricsResponse);

        verify(metricsFilterConverter).convert(serviceProviderSid, hubSid, paymentType, movementType);
        verify(getCurrentPackageAttemptsMetricsUseCase).run(metricsFilter);
        verify(noAttemptsMetricResponseConverter).convert(packageNoAttemptsStatistics);
    }
}

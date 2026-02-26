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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MetricsService {

    private final GetPackageMetricsUseCase getPackageMetricsUseCase;

    private final GetSuccessRateMetricsUseCase getSuccessRateMetricsUseCase;

    private final GetCurrentPackageAttemptsMetricsUseCase getCurrentPackageAttemptsMetricsUseCase;

    private final MetricsFilterConverter metricsFilterConverter;

    private final SuccessRateMetricsFilterConverter successRateMetricsFilterConverter;

    private final DeliveryMetricResponseConverter deliveryMetricResponseConverter;

    private final SuccessRateMetricResponseDeprecatedConverter successRateMetricResponseDeprecatedConverter;

    private final SuccessRateMetricResponseConverter successRateMetricResponseConverter;

    private final LossRateMetricResponseConverter lossRateMetricResponseConverter;

    private final NoAttemptsMetricResponseConverter noAttemptsMetricResponseConverter;

    public List<DeliveryMetricsResponseInner> getPackageDeliveries(final UUID serviceProviderSid,
                                                                   final DateRange dateRange,
                                                                   final UUID hubSid,
                                                                   final PaymentType paymentType,
                                                                   final MovementType movementType) {

        final MetricsFilter filter = metricsFilterConverter.convert(serviceProviderSid, dateRange, hubSid, paymentType, movementType);

        final List<PackageMetrics> metrics = getPackageMetricsUseCase.run(filter);

        return metrics.stream()
                .map(deliveryMetricResponseConverter::convert)
                .toList();
    }

    public List<SuccessRateMetricsResponseDeprecatedInner> getSuccessRateDeprecated(final UUID serviceProviderSid,
                                                                                    final DateRange dateRange,
                                                                                    final UUID hubSid,
                                                                                    final PaymentType paymentType,
                                                                                    final MovementType movementType) {

        final MetricsFilter filter = metricsFilterConverter.convert(serviceProviderSid, dateRange, hubSid, paymentType, movementType);

        final List<PackageMetrics> metrics = getPackageMetricsUseCase.run(filter);

        return metrics.stream()
                .map(successRateMetricResponseDeprecatedConverter::convert)
                .toList();
    }

    public List<SuccessRateMetricsResponseInner> getSuccessRate(final String country,
                                                                final UUID serviceProviderSid,
                                                                final DateRange dateRange,
                                                                final UUID hubSid,
                                                                final PaymentType paymentType,
                                                                final MovementType movementType) {

        final SuccessRateMetricsFilter filter = successRateMetricsFilterConverter.convert(country,
                                                                                          serviceProviderSid,
                                                                                          dateRange,
                                                                                          hubSid,
                                                                                          paymentType,
                                                                                          movementType);

        final List<SuccessRateMetrics> successRateMetrics = getSuccessRateMetricsUseCase.run(filter);

        return successRateMetrics.stream()
                .map(successRateMetricResponseConverter::convert)
                .toList();
    }

    public List<LossRateMetricsResponseInner> getLossRate(final UUID serviceProviderSid,
                                                          final DateRange dateRange,
                                                          final UUID hubSid,
                                                          final PaymentType paymentType,
                                                          final MovementType movementType) {

        final MetricsFilter filter = metricsFilterConverter.convert(serviceProviderSid, dateRange, hubSid, paymentType, movementType);

        final List<PackageMetrics> metrics = getPackageMetricsUseCase.run(filter);

        return metrics.stream()
                .map(lossRateMetricResponseConverter::convert)
                .toList();
    }

    public NoAttemptsMetricsResponse getNoAttempts(final UUID serviceProviderSid,
                                                   final UUID hubSid,
                                                   final PaymentType paymentType,
                                                   final MovementType movementType) {

        final MetricsFilter filter = metricsFilterConverter.convert(serviceProviderSid, hubSid, paymentType, movementType);

        final PackageNoAttemptsStatistics packageNoAttemptsStatistics = getCurrentPackageAttemptsMetricsUseCase.run(filter);

        return noAttemptsMetricResponseConverter.convert(packageNoAttemptsStatistics);
    }
}

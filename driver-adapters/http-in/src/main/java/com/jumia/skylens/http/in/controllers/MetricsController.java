package com.jumia.skylens.http.in.controllers;

import com.jumia.skylens.http.in.acl.authentication.AuthToken;
import com.jumia.skylens.http.in.acl.resources.PartnerResource;
import com.jumia.skylens.http.in.model.DateRange;
import com.jumia.skylens.http.in.model.DeliveryMetricsResponseInner;
import com.jumia.skylens.http.in.model.LossRateMetricsResponseInner;
import com.jumia.skylens.http.in.model.MovementType;
import com.jumia.skylens.http.in.model.NoAttemptsMetricsResponse;
import com.jumia.skylens.http.in.model.PaymentType;
import com.jumia.skylens.http.in.model.SuccessRateMetricsResponse;
import com.jumia.skylens.http.in.model.SuccessRateMetricsResponseDeprecatedInner;
import com.jumia.skylens.http.in.model.SuccessRateMetricsResponseInner;
import com.jumia.skylens.http.in.services.MetricsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class MetricsController implements MetricsApi {

    private final MetricsService metricsService;

    @Override
    public List<DeliveryMetricsResponseInner> getPackageDeliveries(final UUID serviceProviderSid,
                                                                   final DateRange dateRange,
                                                                   final UUID hubSid,
                                                                   final PaymentType paymentType,
                                                                   final MovementType movementType,
                                                                   final AuthToken authToken) {

        authToken.checkPermission(serviceProviderSid, PartnerResource.DASHBOARD_READ);

        return metricsService.getPackageDeliveries(serviceProviderSid, dateRange, hubSid, paymentType, movementType);
    }

    @Override
    public List<SuccessRateMetricsResponseDeprecatedInner> getSuccessRateDeprecated(final UUID serviceProviderSid,
                                                                                    final DateRange dateRange,
                                                                                    final UUID hubSid,
                                                                                    final PaymentType paymentType,
                                                                                    final MovementType movementType,
                                                                                    final AuthToken authToken) {

        authToken.checkPermission(serviceProviderSid, PartnerResource.DASHBOARD_READ);

        return metricsService.getSuccessRateDeprecated(serviceProviderSid, dateRange, hubSid, paymentType, movementType);
    }

    @Override
    public List<SuccessRateMetricsResponseInner> getSuccessRate(final String country,
                                                                final UUID serviceProviderSid,
                                                                final DateRange dateRange,
                                                                final UUID hubSid,
                                                                final PaymentType paymentType,
                                                                final MovementType movementType,
                                                                final AuthToken authToken) {

        authToken.checkPermission(serviceProviderSid, PartnerResource.DASHBOARD_READ);

        return metricsService.getSuccessRate(country, serviceProviderSid, dateRange, hubSid, paymentType, movementType);
    }

    @Override
    public List<LossRateMetricsResponseInner> getLossRate(final UUID serviceProviderSid,
                                                          final DateRange dateRange,
                                                          final UUID hubSid,
                                                          final PaymentType paymentType,
                                                          final MovementType movementType,
                                                          final AuthToken authToken) {

        authToken.checkPermission(serviceProviderSid, PartnerResource.DASHBOARD_READ);

        return metricsService.getLossRate(serviceProviderSid, dateRange, hubSid, paymentType, movementType);
    }

    @Override
    public NoAttemptsMetricsResponse getNoAttempts(final UUID serviceProviderSid,
                                                   final UUID hubSid,
                                                   final PaymentType paymentType,
                                                   final MovementType movementType,
                                                   final AuthToken authToken) {

        authToken.checkPermission(serviceProviderSid, PartnerResource.DASHBOARD_READ);

        return metricsService.getNoAttempts(serviceProviderSid, hubSid, paymentType, movementType);
    }
}

package com.jumia.skylens.persistence.jpa.impl;

import com.jumia.skylens.domain.catalog.Granularity;
import com.jumia.skylens.domain.catalog.HubDailyMetric;
import com.jumia.skylens.domain.catalog.MovementType;
import com.jumia.skylens.domain.catalog.PackageNoAttemptsStatistics;
import com.jumia.skylens.domain.catalog.PackageStatistics;
import com.jumia.skylens.domain.catalog.PaymentType;
import com.jumia.skylens.persistence.api.HubDailyMetricDAO;
import com.jumia.skylens.persistence.jpa.converters.HubDailyMetricEntityConverter;
import com.jumia.skylens.persistence.jpa.converters.MovementTypeConverter;
import com.jumia.skylens.persistence.jpa.entities.HubDailyMetricEntity;
import com.jumia.skylens.persistence.jpa.entities.HubDailyMetricEntityId;
import com.jumia.skylens.persistence.jpa.repositories.HubDailyMetricRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HubDailyMetricDAOImpl implements HubDailyMetricDAO {

    private final HubDailyMetricRepository hubDailyMetricRepository;

    private final HubDailyMetricEntityConverter hubDailyMetricEntityConverter;

    private final MovementTypeConverter movementTypeConverter;

    @Override
    public void save(final HubDailyMetric hubDailyMetric) {

        final HubDailyMetricEntity entity = hubDailyMetricEntityConverter.convert(hubDailyMetric);

        hubDailyMetricRepository.save(entity);
    }

    @Override
    public List<PackageStatistics> getPackageStatistics(final UUID serviceProviderSid,
                                                        final UUID hubSid,
                                                        final LocalDate startDate,
                                                        final LocalDate endDate,
                                                        final PaymentType paymentType,
                                                        final MovementType movementType,
                                                        final Granularity granularity) {

        return hubDailyMetricRepository.findByFilters(serviceProviderSid,
                                                      hubSid,
                                                      startDate,
                                                      endDate,
                                                      Optional.ofNullable(paymentType)
                                                              .map(pt -> HubDailyMetricEntityId.PaymentType.valueOf(pt.name()))
                                                              .orElse(null),
                                                      movementTypeConverter.convert(movementType),
                                                      granularity);
    }

    @Override
    public PackageNoAttemptsStatistics findCurrentNoAttemptsStatistics(final UUID serviceProviderSid,
                                                                       final UUID hubSid,
                                                                       final PaymentType paymentType,
                                                                       final MovementType movementType) {

        final HubDailyMetricEntityId.PaymentType pType = Optional.ofNullable(paymentType)
                .map(pt -> HubDailyMetricEntityId.PaymentType.valueOf(pt.name()))
                .orElse(null);

        return hubDailyMetricRepository.findCurrentNoAttemptsStatistics(serviceProviderSid,
                                                                        hubSid,
                                                                        pType,
                                                                        movementTypeConverter.convert(movementType));
    }
}

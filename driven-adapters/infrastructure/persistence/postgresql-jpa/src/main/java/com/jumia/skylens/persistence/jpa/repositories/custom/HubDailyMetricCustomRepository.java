package com.jumia.skylens.persistence.jpa.repositories.custom;

import com.jumia.skylens.domain.catalog.Granularity;
import com.jumia.skylens.domain.catalog.PackageNoAttemptsStatistics;
import com.jumia.skylens.domain.catalog.PackageStatistics;
import com.jumia.skylens.persistence.jpa.entities.HubDailyMetricEntityId;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface HubDailyMetricCustomRepository {

    List<PackageStatistics> findByFilters(UUID serviceProviderSid,
                                          UUID hubSid,
                                          LocalDate startDate,
                                          LocalDate endDate,
                                          HubDailyMetricEntityId.PaymentType paymentType,
                                          HubDailyMetricEntityId.MovementType movementType,
                                          Granularity granularity);

    PackageNoAttemptsStatistics findCurrentNoAttemptsStatistics(UUID serviceProviderSid,
                                                                UUID hubSid,
                                                                HubDailyMetricEntityId.PaymentType paymentType,
                                                                HubDailyMetricEntityId.MovementType movementType);
}

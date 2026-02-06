package com.jumia.skylens.persistence.api;

import com.jumia.skylens.domain.catalog.Granularity;
import com.jumia.skylens.domain.catalog.HubDailyMetric;
import com.jumia.skylens.domain.catalog.MovementType;
import com.jumia.skylens.domain.catalog.PackageNoAttemptsStatistics;
import com.jumia.skylens.domain.catalog.PackageStatistics;
import com.jumia.skylens.domain.catalog.PaymentType;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface HubDailyMetricDAO {

    void save(HubDailyMetric hubDailyMetric);

    List<PackageStatistics> getPackageStatistics(UUID serviceProviderSid,
                                                 UUID hubSid,
                                                 LocalDate startDate,
                                                 LocalDate endDate,
                                                 PaymentType paymentType,
                                                 MovementType movementType,
                                                 Granularity granularity);

    PackageNoAttemptsStatistics findCurrentNoAttemptsStatistics(UUID serviceProviderSid,
                                                                UUID hubSid,
                                                                PaymentType paymentType,
                                                                MovementType movementType);
}

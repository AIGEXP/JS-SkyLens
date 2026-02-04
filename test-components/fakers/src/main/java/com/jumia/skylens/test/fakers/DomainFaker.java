package com.jumia.skylens.test.fakers;

import com.jumia.skylens.domain.catalog.DateRange;
import com.jumia.skylens.domain.catalog.HubDailyMetric;
import com.jumia.skylens.domain.catalog.MetricsFilter;
import com.jumia.skylens.domain.catalog.PackageStatistics;
import net.datafaker.Faker;

import java.time.LocalDate;
import java.util.UUID;

public class DomainFaker extends Faker {

    public HubDailyMetric.Builder hubDailyMetric() {

        return HubDailyMetric.builder()
                .hubSid(UUID.randomUUID())
                .serviceProviderSid(UUID.randomUUID())
                .day(LocalDate.now())
                .paymentType(options().option(HubDailyMetric.PaymentType.class))
                .movementType(options().option(HubDailyMetric.MovementType.class))
                .packagesDelivered(number().positive())
                .packagesClosed(number().positive())
                .packagesReceived(number().positive())
                .packagesLostAtHub(number().positive())
                .packagesNoAttemptsOneDay(number().positive())
                .packagesNoAttemptsTwoDays(number().positive())
                .packagesNoAttemptsThreeDays(number().positive())
                .packagesNoAttemptsOverThreeDays(number().positive());
    }

    public MetricsFilter.Builder metricsFilter() {

        return MetricsFilter.builder()
                .serviceProviderSid(UUID.randomUUID())
                .hubSid(UUID.randomUUID())
                .dateRange(options().option(DateRange.class))
                .paymentType(options().option(HubDailyMetric.PaymentType.class))
                .movementType(options().option(HubDailyMetric.MovementType.class));
    }

    public PackageStatistics.Builder packageStatistics() {

        return PackageStatistics.builder()
                .date(LocalDate.now())
                .packagesDelivered(number().randomDigitNotZero())
                .packagesClosed(number().randomDigitNotZero())
                .packagesReceived(number().randomDigitNotZero())
                .packagesLostAtHub(number().randomDigitNotZero());
    }
}

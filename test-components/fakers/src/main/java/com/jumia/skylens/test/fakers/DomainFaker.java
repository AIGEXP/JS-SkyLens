package com.jumia.skylens.test.fakers;

import com.jumia.skylens.domain.catalog.AlertLevel;
import com.jumia.skylens.domain.catalog.CountryThreshold;
import com.jumia.skylens.domain.catalog.DateRange;
import com.jumia.skylens.domain.catalog.HubDailyMetric;
import com.jumia.skylens.domain.catalog.MetricsFilter;
import com.jumia.skylens.domain.catalog.MovementType;
import com.jumia.skylens.domain.catalog.PackageStatistics;
import com.jumia.skylens.domain.catalog.PaymentType;
import com.jumia.skylens.domain.catalog.ReportType;
import net.datafaker.Faker;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.UUID;

public class DomainFaker extends Faker {

    public HubDailyMetric.Builder hubDailyMetric() {

        return HubDailyMetric.builder()
                .hubSid(UUID.randomUUID())
                .serviceProviderSid(UUID.randomUUID())
                .day(LocalDate.now())
                .paymentType(options().option(PaymentType.class))
                .movementType(options().option(MovementType.class))
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
                .paymentType(options().option(PaymentType.class))
                .movementType(options().option(MovementType.class));
    }

    public PackageStatistics.Builder packageStatistics() {

        return PackageStatistics.builder()
                .date(LocalDate.now())
                .packagesDelivered(number().randomDigitNotZero())
                .packagesClosed(number().randomDigitNotZero())
                .packagesReceived(number().randomDigitNotZero())
                .packagesLostAtHub(number().randomDigitNotZero());
    }

    public CountryThreshold countryThreshold() {

        return CountryThreshold.builder()
                .reportType(options().option(ReportType.class))
                .country(country().countryCode2())
                .value(BigDecimal.valueOf(number().randomDouble(2, 0, 0)).setScale(2, RoundingMode.HALF_UP))
                .build();
    }

    public AlertLevel.Builder alertLevel() {

        return AlertLevel.builder()
                .country(country().countryCode2())
                .reportType(options().option(ReportType.class))
                .warningValue(new BigDecimal("0.80"))
                .criticalValue(new BigDecimal("0.20"));
    }
}

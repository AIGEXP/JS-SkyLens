package com.jumia.skylens.persistence.jpa.impl;

import com.jumia.skylens.domain.catalog.Granularity;
import com.jumia.skylens.domain.catalog.HubDailyMetric;
import com.jumia.skylens.domain.catalog.PackageNoAttemptsStatistics;
import com.jumia.skylens.domain.catalog.PackageStatistics;
import com.jumia.skylens.persistence.jpa.configuration.BaseTestIT;
import com.jumia.skylens.persistence.jpa.entities.HubDailyMetricEntity;
import com.jumia.skylens.persistence.jpa.entities.HubDailyMetricEntityId;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class HubDailyMetricDAOImplTestIT extends BaseTestIT {

    @Autowired
    private HubDailyMetricDAOImpl subject;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void save_whenSaving_thenSaveItSuccessfully() {

        // Given
        final HubDailyMetric hubDailyMetric = faker.domain.hubDailyMetric().build();

        // When
        subject.save(hubDailyMetric);

        // Then
        itPersister.flushAndClear1LevelCache();

        final HubDailyMetricEntityId hubDailyMetricEntityId = HubDailyMetricEntityId.builder()
                .serviceProviderSid(hubDailyMetric.serviceProviderSid())
                .hubSid(hubDailyMetric.hubSid())
                .day(hubDailyMetric.day())
                .paymentType(HubDailyMetricEntityId.PaymentType.valueOf(hubDailyMetric.paymentType().name()))
                .movementType(HubDailyMetricEntityId.MovementType.valueOf(hubDailyMetric.movementType().name()))
                .build();

        final HubDailyMetricEntity hubDailyMetricEntity = entityManager.find(HubDailyMetricEntity.class, hubDailyMetricEntityId);

        assertThat(hubDailyMetricEntity).isNotNull();
    }

    @Test
    void getPackageStatistics_whenGranularityIsDaily_thenReturnExpectedPackageStatistics() {

        // Given
        final UUID serviceProviderSid = UUID.randomUUID();
        final UUID hubSid = UUID.randomUUID();
        final LocalDate day = LocalDate.of(2026, 2, 4);
        final LocalDate otherDay = LocalDate.of(2026, 2, 3);
        final HubDailyMetricEntity hubDailyMetricEntity1 = itPersister.save(faker.entity.hubDailyMetricEntity()
                                                                                    .id(faker.entity.hubDailyMetricEntityId()
                                                                                                .serviceProviderSid(serviceProviderSid)
                                                                                                .hubSid(hubSid)
                                                                                                .day(day)
                                                                                                .movementType(HubDailyMetricEntityId
                                                                                                                      .MovementType.DD)
                                                                                                .build())
                                                                                    .build());
        final HubDailyMetricEntity hubDailyMetricEntity2 = itPersister.save(faker.entity.hubDailyMetricEntity()
                                                                                    .id(faker.entity.hubDailyMetricEntityId()
                                                                                                .serviceProviderSid(serviceProviderSid)
                                                                                                .hubSid(hubSid)
                                                                                                .day(day)
                                                                                                .movementType(HubDailyMetricEntityId
                                                                                                                      .MovementType.PUS)
                                                                                                .build())
                                                                                    .build());
        final HubDailyMetricEntity hubDailyMetricEntity3 = itPersister.save(faker.entity.hubDailyMetricEntity()
                                                                                    .id(faker.entity.hubDailyMetricEntityId()
                                                                                                .serviceProviderSid(serviceProviderSid)
                                                                                                .hubSid(hubSid)
                                                                                                .day(otherDay)
                                                                                                .build())
                                                                                    .build());

        itPersister.flushAndClear1LevelCache();

        // When
        final List<PackageStatistics> packageStatistics = subject.getPackageStatistics(serviceProviderSid,
                                                                                       hubSid,
                                                                                       day.minusDays(10),
                                                                                       null,
                                                                                       null,
                                                                                       Granularity.DAILY);

        assertThat(packageStatistics)
                .containsExactly(PackageStatistics.builder()
                                         .date(day)
                                         .packagesDelivered(hubDailyMetricEntity1.getPackagesDelivered()
                                                                    + hubDailyMetricEntity2.getPackagesDelivered())
                                         .packagesClosed(hubDailyMetricEntity1.getPackagesClosed()
                                                                 + hubDailyMetricEntity2.getPackagesClosed())
                                         .packagesReceived(hubDailyMetricEntity1.getPackagesReceived()
                                                                   + hubDailyMetricEntity2.getPackagesReceived())
                                         .packagesLostAtHub(hubDailyMetricEntity1.getPackagesLostAtHub()
                                                                    + hubDailyMetricEntity2.getPackagesLostAtHub())
                                         .build(),
                                 PackageStatistics.builder()
                                         .date(otherDay)
                                         .packagesDelivered(hubDailyMetricEntity3.getPackagesDelivered())
                                         .packagesClosed(hubDailyMetricEntity3.getPackagesClosed())
                                         .packagesReceived(hubDailyMetricEntity3.getPackagesReceived())
                                         .packagesLostAtHub(hubDailyMetricEntity3.getPackagesLostAtHub())
                                         .build());
    }

    @Test
    void getPackageStatistics_whenGranularityIsWeekly_thenReturnExpectedPackageStatistics() {

        // Given
        final UUID serviceProviderSid = UUID.randomUUID();
        final UUID hubSid = UUID.randomUUID();
        final LocalDate dayFromThisWeek = LocalDate.now();
        final LocalDate dayFromPreviousWeek = LocalDate.now().minusWeeks(1);
        final HubDailyMetricEntity hubDailyMetricEntity1 = itPersister.save(faker.entity.hubDailyMetricEntity()
                                                                                    .id(faker.entity.hubDailyMetricEntityId()
                                                                                                .serviceProviderSid(serviceProviderSid)
                                                                                                .hubSid(hubSid)
                                                                                                .day(dayFromThisWeek)
                                                                                                .movementType(HubDailyMetricEntityId
                                                                                                                      .MovementType.DD)
                                                                                                .build())
                                                                                    .build());
        final HubDailyMetricEntity hubDailyMetricEntity2 = itPersister.save(faker.entity.hubDailyMetricEntity()
                                                                                    .id(faker.entity.hubDailyMetricEntityId()
                                                                                                .serviceProviderSid(serviceProviderSid)
                                                                                                .hubSid(hubSid)
                                                                                                .day(dayFromThisWeek)
                                                                                                .movementType(HubDailyMetricEntityId
                                                                                                                      .MovementType.PUS)
                                                                                                .build())
                                                                                    .build());
        final HubDailyMetricEntity hubDailyMetricEntity3 = itPersister.save(faker.entity.hubDailyMetricEntity()
                                                                                    .id(faker.entity.hubDailyMetricEntityId()
                                                                                                .serviceProviderSid(serviceProviderSid)
                                                                                                .hubSid(hubSid)
                                                                                                .day(dayFromPreviousWeek)
                                                                                                .build())
                                                                                    .build());

        itPersister.flushAndClear1LevelCache();

        // When
        final List<PackageStatistics> packageStatistics = subject.getPackageStatistics(serviceProviderSid,
                                                                                       hubSid,
                                                                                       LocalDate.now().minusMonths(4),
                                                                                       null,
                                                                                       null,
                                                                                       Granularity.WEEKLY);

        assertThat(packageStatistics)
                .containsExactly(PackageStatistics.builder()
                                         .date(dayFromThisWeek.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)))
                                         .packagesDelivered(hubDailyMetricEntity1.getPackagesDelivered()
                                                                    + hubDailyMetricEntity2.getPackagesDelivered())
                                         .packagesClosed(hubDailyMetricEntity1.getPackagesClosed()
                                                                 + hubDailyMetricEntity2.getPackagesClosed())
                                         .packagesReceived(hubDailyMetricEntity1.getPackagesReceived()
                                                                   + hubDailyMetricEntity2.getPackagesReceived())
                                         .packagesLostAtHub(hubDailyMetricEntity1.getPackagesLostAtHub()
                                                                    + hubDailyMetricEntity2.getPackagesLostAtHub())
                                         .build(),
                                 PackageStatistics.builder()
                                         .date(dayFromPreviousWeek.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)))
                                         .packagesDelivered(hubDailyMetricEntity3.getPackagesDelivered())
                                         .packagesClosed(hubDailyMetricEntity3.getPackagesClosed())
                                         .packagesReceived(hubDailyMetricEntity3.getPackagesReceived())
                                         .packagesLostAtHub(hubDailyMetricEntity3.getPackagesLostAtHub())
                                         .build());
    }

    @Test
    void getPackageStatistics_whenGranularityIsMonthly_thenReturnExpectedPackageStatistics() {

        // Given
        final UUID serviceProviderSid = UUID.randomUUID();
        final UUID hubSid = UUID.randomUUID();
        final LocalDate dayFromThisMonth = LocalDate.now();
        final LocalDate dayFromPreviousMonth = LocalDate.now().minusMonths(1);
        final HubDailyMetricEntity hubDailyMetricEntity1 = itPersister.save(faker.entity.hubDailyMetricEntity()
                                                                                    .id(faker.entity.hubDailyMetricEntityId()
                                                                                                .serviceProviderSid(serviceProviderSid)
                                                                                                .hubSid(hubSid)
                                                                                                .day(dayFromThisMonth)
                                                                                                .movementType(HubDailyMetricEntityId
                                                                                                                      .MovementType.DD)
                                                                                                .build())
                                                                                    .build());
        final HubDailyMetricEntity hubDailyMetricEntity2 = itPersister.save(faker.entity.hubDailyMetricEntity()
                                                                                    .id(faker.entity.hubDailyMetricEntityId()
                                                                                                .serviceProviderSid(serviceProviderSid)
                                                                                                .hubSid(hubSid)
                                                                                                .day(dayFromThisMonth)
                                                                                                .movementType(HubDailyMetricEntityId
                                                                                                                      .MovementType.PUS)
                                                                                                .build())
                                                                                    .build());
        final HubDailyMetricEntity hubDailyMetricEntity3 = itPersister.save(faker.entity.hubDailyMetricEntity()
                                                                                    .id(faker.entity.hubDailyMetricEntityId()
                                                                                                .serviceProviderSid(serviceProviderSid)
                                                                                                .hubSid(hubSid)
                                                                                                .day(dayFromPreviousMonth)
                                                                                                .build())
                                                                                    .build());

        itPersister.flushAndClear1LevelCache();

        // When
        final List<PackageStatistics> packageStatistics = subject.getPackageStatistics(serviceProviderSid,
                                                                                       hubSid,
                                                                                       LocalDate.now().minusMonths(4),
                                                                                       null,
                                                                                       null,
                                                                                       Granularity.MONTHLY);

        assertThat(packageStatistics)
                .containsExactly(PackageStatistics.builder()
                                         .date(dayFromThisMonth.withDayOfMonth(1))
                                         .packagesDelivered(hubDailyMetricEntity1.getPackagesDelivered()
                                                                    + hubDailyMetricEntity2.getPackagesDelivered())
                                         .packagesClosed(hubDailyMetricEntity1.getPackagesClosed()
                                                                 + hubDailyMetricEntity2.getPackagesClosed())
                                         .packagesReceived(hubDailyMetricEntity1.getPackagesReceived()
                                                                   + hubDailyMetricEntity2.getPackagesReceived())
                                         .packagesLostAtHub(hubDailyMetricEntity1.getPackagesLostAtHub()
                                                                    + hubDailyMetricEntity2.getPackagesLostAtHub())
                                         .build(),
                                 PackageStatistics.builder()
                                         .date(dayFromPreviousMonth.withDayOfMonth(1))
                                         .packagesDelivered(hubDailyMetricEntity3.getPackagesDelivered())
                                         .packagesClosed(hubDailyMetricEntity3.getPackagesClosed())
                                         .packagesReceived(hubDailyMetricEntity3.getPackagesReceived())
                                         .packagesLostAtHub(hubDailyMetricEntity3.getPackagesLostAtHub())
                                         .build());
    }

    @Test
    void findCurrentNoAttemptsStatistics_whenFilteringByServiceProviderSid_thenSumAllNoAttemptsFromTheHubs() {

        // Given
        final UUID serviceProviderSid = UUID.randomUUID();
        final UUID hubSid1 = UUID.randomUUID();
        final UUID hubSid2 = UUID.randomUUID();
        final UUID hubSid3 = UUID.randomUUID();
        final LocalDate localDate = LocalDate.now();
        final HubDailyMetricEntity hubDailyMetricEntity1 = itPersister.save(faker.entity.hubDailyMetricEntity()
                                                                                    .id(faker.entity.hubDailyMetricEntityId()
                                                                                                .serviceProviderSid(serviceProviderSid)
                                                                                                .hubSid(hubSid1)
                                                                                                .day(localDate)
                                                                                                .build())
                                                                                    .build());
        final HubDailyMetricEntity hubDailyMetricEntity2 = itPersister.save(faker.entity.hubDailyMetricEntity()
                                                                                    .id(faker.entity.hubDailyMetricEntityId()
                                                                                                .serviceProviderSid(serviceProviderSid)
                                                                                                .hubSid(hubSid2)
                                                                                                .day(localDate)
                                                                                                .build())
                                                                                    .build());
        final HubDailyMetricEntity hubDailyMetricEntity3 = itPersister.save(faker.entity.hubDailyMetricEntity()
                                                                                    .id(faker.entity.hubDailyMetricEntityId()
                                                                                                .serviceProviderSid(serviceProviderSid)
                                                                                                .hubSid(hubSid3)
                                                                                                .day(localDate)
                                                                                                .build())
                                                                                    .build());
        itPersister.save(faker.entity.hubDailyMetricEntity()
                                 .id(faker.entity.hubDailyMetricEntityId()
                                             .serviceProviderSid(serviceProviderSid)
                                             .hubSid(hubSid3)
                                             .build())
                                 .build());

        itPersister.flushAndClear1LevelCache();

        // When
        final PackageNoAttemptsStatistics packageNoAttemptsStatistics = subject.findCurrentNoAttemptsStatistics(serviceProviderSid,
                                                                                                                null,
                                                                                                                null,
                                                                                                                null);

        assertThat(packageNoAttemptsStatistics)
                .usingRecursiveComparison()
                .isEqualTo(PackageNoAttemptsStatistics.builder()
                                   .oneDay(hubDailyMetricEntity1.getPackagesNoAttemptsOneDay()
                                                   + hubDailyMetricEntity2.getPackagesNoAttemptsOneDay()
                                                   + hubDailyMetricEntity3.getPackagesNoAttemptsOneDay())
                                   .twoDays(hubDailyMetricEntity1.getPackagesNoAttemptsTwoDays()
                                                    + hubDailyMetricEntity2.getPackagesNoAttemptsTwoDays()
                                                    + hubDailyMetricEntity3.getPackagesNoAttemptsTwoDays())
                                   .threeDays(hubDailyMetricEntity1.getPackagesNoAttemptsThreeDays()
                                                      + hubDailyMetricEntity2.getPackagesNoAttemptsThreeDays()
                                                      + hubDailyMetricEntity3.getPackagesNoAttemptsThreeDays())
                                   .overThreeDays(hubDailyMetricEntity1.getPackagesNoAttemptsOverThreeDays()
                                                          + hubDailyMetricEntity2.getPackagesNoAttemptsOverThreeDays()
                                                          + hubDailyMetricEntity3.getPackagesNoAttemptsOverThreeDays())
                                   .build());
    }

    @Test
    void findCurrentNoAttemptsStatistics_whenFilteringByHubSid_thenSumAllNoAttemptsFromThatHub() {

        // Given
        final UUID serviceProviderSid = UUID.randomUUID();
        final UUID hubSid1 = UUID.randomUUID();
        final UUID hubSid2 = UUID.randomUUID();
        final UUID hubSid3 = UUID.randomUUID();
        final LocalDate localDate = LocalDate.now();
        final HubDailyMetricEntity hubDailyMetricEntity1 = itPersister.save(faker.entity.hubDailyMetricEntity()
                                                                                    .id(faker.entity.hubDailyMetricEntityId()
                                                                                                .serviceProviderSid(serviceProviderSid)
                                                                                                .hubSid(hubSid1)
                                                                                                .day(localDate)
                                                                                                .build())
                                                                                    .build());
        itPersister.save(faker.entity.hubDailyMetricEntity()
                                 .id(faker.entity.hubDailyMetricEntityId()
                                             .serviceProviderSid(serviceProviderSid)
                                             .hubSid(hubSid2)
                                             .day(localDate)
                                             .build())
                                 .build());
        itPersister.save(faker.entity.hubDailyMetricEntity()
                                 .id(faker.entity.hubDailyMetricEntityId()
                                             .serviceProviderSid(serviceProviderSid)
                                             .hubSid(hubSid3)
                                             .day(localDate)
                                             .build())
                                 .build());
        itPersister.save(faker.entity.hubDailyMetricEntity()
                                 .id(faker.entity.hubDailyMetricEntityId()
                                             .serviceProviderSid(serviceProviderSid)
                                             .hubSid(hubSid3)
                                             .build())
                                 .build());

        itPersister.flushAndClear1LevelCache();

        // When
        final PackageNoAttemptsStatistics packageNoAttemptsStatistics = subject.findCurrentNoAttemptsStatistics(serviceProviderSid,
                                                                                                                hubSid1,
                                                                                                                null,
                                                                                                                null);

        assertThat(packageNoAttemptsStatistics)
                .usingRecursiveComparison()
                .isEqualTo(PackageNoAttemptsStatistics.builder()
                                   .oneDay(hubDailyMetricEntity1.getPackagesNoAttemptsOneDay())
                                   .twoDays(hubDailyMetricEntity1.getPackagesNoAttemptsTwoDays())
                                   .threeDays(hubDailyMetricEntity1.getPackagesNoAttemptsThreeDays())
                                   .overThreeDays(hubDailyMetricEntity1.getPackagesNoAttemptsOverThreeDays())
                                   .build());
    }
}

package com.jumia.skylens.persistence.jpa.impl;

import com.jumia.skylens.domain.catalog.Package;
import com.jumia.skylens.domain.catalog.PackageFilter;
import com.jumia.skylens.domain.catalog.PackageSummary;
import com.jumia.skylens.domain.catalog.enums.ServiceCode;
import com.jumia.skylens.domain.catalog.enums.Size;
import com.jumia.skylens.domain.catalog.pages.PageRequest;
import com.jumia.skylens.persistence.jpa.configuration.BaseTestIT;
import com.jumia.skylens.persistence.jpa.entities.PackageEntity;
import com.jumia.skylens.persistence.jpa.entities.StopEntity;
import com.jumia.skylens.persistence.jpa.entities.enums.PaymentMethodTypeEnum;
import com.jumia.skylens.persistence.jpa.entities.enums.ServiceCodeEnum;
import com.jumia.skylens.persistence.jpa.entities.enums.SizeEnum;
import com.jumia.skylens.persistence.jpa.providers.PackageFilterProvider;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.function.BiFunction;

import static org.assertj.core.api.Assertions.assertThat;

class PackageDAOImplTestIT extends BaseTestIT {

    @Autowired
    private PackageDAOImpl subject;

    @PersistenceContext
    private EntityManager entityManager;

    private final PageRequest pageRequest = PageRequest.of(0, 10);

    @Test
    void save_whenSavingPackage_thenSaveItSuccessfully() {

        // Given
        final StopEntity stop = itPersister.save(faker.entity.stop().build());
        final Package pkg = faker.domain.pkg()
                .stopId(stop.getId())
                .build();

        // When
        subject.save(pkg);

        // Then
        itPersister.flushAndClear1LevelCache();

        final List<PackageEntity> packageEntities = entityManager.createQuery("SELECT p FROM PackageEntity p", PackageEntity.class)
                .getResultList();

        assertThat(packageEntities)
                .singleElement()
                .usingRecursiveComparison()
                .ignoringFields("id", "createdAt")
                .isEqualTo(PackageEntity.builder()
                                   .packageId(pkg.getPackageId())
                                   .stopId(pkg.getStopId())
                                   .network(pkg.getNetwork())
                                   .trackingNumber(pkg.getTrackingNumber())
                                   .partnerSid(pkg.getPartnerSid())
                                   .nodeSid(pkg.getNodeSid())
                                   .nodeName(pkg.getNodeName())
                                   .driverSid(pkg.getDriverSid())
                                   .driverName(pkg.getDriverName())
                                   .paymentMethodSid(pkg.getPaymentMethodSid())
                                   .paymentMethodName(pkg.getPaymentMethodName())
                                   .paymentMethodType(PaymentMethodTypeEnum.valueOf(pkg.getPaymentMethodType().name()))
                                   .serviceCode(ServiceCodeEnum.valueOf(pkg.getServiceCode().name()))
                                   .size(SizeEnum.valueOf(pkg.getSize().name()))
                                   .address(PackageEntity.Address.builder()
                                                    .id(pkg.getAddress().id())
                                                    .email(pkg.getAddress().email())
                                                    .type(pkg.getAddress().type())
                                                    .region(pkg.getAddress().region())
                                                    .city(pkg.getAddress().city())
                                                    .build())
                                   .nodeAddress(new PackageEntity.NodeAddress(pkg.getNodeAddress().region(),
                                                                              pkg.getNodeAddress().city()))
                                   .eventDate(pkg.getEventDate()));
    }

    @Test
    void countPackagesOfSizeMByStopId_whenStopIdDoesntHaveMPackages_thenReturnZero() {

        // Given
        final StopEntity stopEntity = itPersister.save(faker.entity.stop().build());
        final StopEntity otherStopEntity = itPersister.save(faker.entity.stop().build());
        itPersister.save(faker.entity.pkg(stopEntity).size(SizeEnum.S).build());
        itPersister.save(faker.entity.pkg(otherStopEntity).size(SizeEnum.M).build());

        itPersister.flushAndClear1LevelCache();

        // When
        final int count = subject.countPackagesOfSizeMByStopId(stopEntity.getId());

        // Then
        assertThat(count).isZero();
    }

    @Test
    void countPackagesOfSizeMByStopId_whenStopIdHasMPackages_thenReturnExpectedCount() {

        // Given
        final StopEntity stopEntity = itPersister.save(faker.entity.stop().build());
        final StopEntity otherStopEntity = itPersister.save(faker.entity.stop().build());
        itPersister.save(faker.entity.pkg(stopEntity).size(SizeEnum.M).build());
        itPersister.save(faker.entity.pkg(stopEntity).size(SizeEnum.M).build());
        itPersister.save(faker.entity.pkg(stopEntity).size(SizeEnum.S).build());
        itPersister.save(faker.entity.pkg(otherStopEntity).size(SizeEnum.M).build());
        itPersister.save(faker.entity.pkg(otherStopEntity).size(SizeEnum.S).build());

        itPersister.flushAndClear1LevelCache();

        // When
        final int count = subject.countPackagesOfSizeMByStopId(stopEntity.getId());

        // Then
        assertThat(count).isEqualTo(2);
    }

    @Test
    void getPublishedPackages_whenStopsAreUnpublished_thenReturnEmptyList() {

        // Given
        final StopEntity stopEntity = itPersister.save(faker.entity.stop().published(false).build());
        itPersister.save(faker.entity.pkg(stopEntity).build());

        itPersister.flushAndClear1LevelCache();

        final PackageFilter filter = PackageFilter.builder().build();

        // When
        final List<PackageSummary> packages = subject.getPublishedPackages(pageRequest, filter);

        // Then
        assertThat(packages).isEmpty();
    }

    @Test
    void getPublishedPackages_whenStopsArePublished_thenReturnExpectedList() {

        // Given
        final StopEntity stopEntity = itPersister.save(faker.entity.stop().published(true).build());
        final PackageEntity packageEntity = itPersister.save(faker.entity.pkg(stopEntity).build());

        itPersister.flushAndClear1LevelCache();

        final PackageFilter filter = PackageFilter.builder().build();

        // When
        final List<PackageSummary> stops = subject.getPublishedPackages(pageRequest, filter);

        // Then
        assertThat(stops)
                .singleElement()
                .usingRecursiveComparison()
                .isEqualTo(PackageSummary.builder()
                                   .trackingNumber(packageEntity.getTrackingNumber())
                                   .stopId(stopEntity.getStopId())
                                   .eventDate(stopEntity.getEventDate())
                                   .size(Size.valueOf(stopEntity.getSize().name()))
                                   .serviceCode(ServiceCode.valueOf(stopEntity.getServiceCode().name()))
                                   .zone(stopEntity.getZoneName())
                                   .nodeName(stopEntity.getNodeName())
                                   .paymentMethodName(packageEntity.getPaymentMethodName())
                                   .driverName(packageEntity.getDriverName())
                                   .build());
    }

    @ParameterizedTest
    @ArgumentsSource(PackageFilterProvider.class)
    void getPublishedPackages_whenCalledWithFilter_thenReturnMatchingPackages(
            BiFunction<StopEntity, PackageEntity, PackageFilter> filterBuilder) {

        // Given
        final StopEntity stopEntity = itPersister.save(faker.entity.stop()
                                                               .published(true)
                                                               .serviceCode(ServiceCodeEnum.FCD)
                                                               .size(SizeEnum.S)
                                                               .zoneSid(UUID.randomUUID())
                                                               .build());
        final PackageEntity packageEntity = itPersister.save(faker.entity.pkg(stopEntity).build());

        final StopEntity otherStopEntity = itPersister.save(faker.entity.stop()
                                                                    .published(true)
                                                                    .stopId("OTHER-STOP")
                                                                    .serviceCode(ServiceCodeEnum.RDD)
                                                                    .size(SizeEnum.L)
                                                                    .zoneSid(UUID.randomUUID())
                                                                    .nodeSid(UUID.randomUUID())
                                                                    .build());
        itPersister.save(faker.entity.pkg(otherStopEntity).build());

        itPersister.flushAndClear1LevelCache();

        final PackageFilter filter = filterBuilder.apply(stopEntity, packageEntity);

        // When
        final List<PackageSummary> stops = subject.getPublishedPackages(pageRequest, filter);

        // Then
        assertThat(stops)
                .singleElement()
                .extracting(PackageSummary::stopId)
                .isEqualTo(stopEntity.getStopId());
    }

    @Test
    void getPublishedPackages_whenFilteringByDateFrom_thenReturnMatchingPackages() {

        // Given
        final LocalDate now = LocalDate.now();

        final StopEntity stopEntity = itPersister.save(faker.entity.stop()
                                                               .published(true)
                                                               .eventDate(now)
                                                               .build());
        itPersister.save(faker.entity.pkg(stopEntity).build());

        itPersister.save(faker.entity.stop()
                                 .published(true)
                                 .eventDate(now.minusDays(5))
                                 .build());

        itPersister.flushAndClear1LevelCache();

        final PackageFilter filter = PackageFilter.builder()
                .dateFrom(now.minusDays(1))
                .build();

        // When
        final List<PackageSummary> stops = subject.getPublishedPackages(pageRequest, filter);

        // Then
        assertThat(stops)
                .singleElement()
                .extracting(PackageSummary::stopId)
                .isEqualTo(stopEntity.getStopId());
    }

    @Test
    void getPublishedPackages_whenFilterByDateTo_thenReturnMatchingPackages() {

        // Given
        final LocalDate now = LocalDate.now();

        final StopEntity stopEntity = itPersister.save(faker.entity.stop()
                                                               .published(true)
                                                               .eventDate(now)
                                                               .build());
        itPersister.save(faker.entity.pkg(stopEntity).build());

        itPersister.save(faker.entity.stop()
                                 .published(true)
                                 .eventDate(now.plusDays(5))
                                 .build());

        itPersister.flushAndClear1LevelCache();

        final PackageFilter filter = PackageFilter.builder()
                .dateTo(now.plusDays(1))
                .build();

        // When
        final List<PackageSummary> stops = subject.getPublishedPackages(pageRequest, filter);

        // Then
        assertThat(stops)
                .singleElement()
                .extracting(PackageSummary::stopId)
                .isEqualTo(stopEntity.getStopId());
    }

    @Test
    void getPublishedPackages_whenPageRequestWithOffset_thenReturnCorrectPage() {

        // Given
        final StopEntity stopEntity = itPersister.save(faker.entity.stop().published(true).build());
        itPersister.save(faker.entity.pkg(stopEntity).build());
        itPersister.save(faker.entity.pkg(stopEntity).build());
        itPersister.save(faker.entity.pkg(stopEntity).build());
        itPersister.save(faker.entity.pkg(stopEntity).build());
        itPersister.save(faker.entity.pkg(stopEntity).build());

        itPersister.flushAndClear1LevelCache();

        final PackageFilter filter = PackageFilter.builder().build();
        final PageRequest pageRequest1 = PageRequest.of(0, 2);
        final PageRequest pageRequest2 = PageRequest.of(2, 2);

        // When
        final List<PackageSummary> page1 = subject.getPublishedPackages(pageRequest1, filter);
        final List<PackageSummary> page2 = subject.getPublishedPackages(pageRequest2, filter);

        // Then
        assertThat(page1).hasSize(2);
        assertThat(page2).hasSize(2);
        assertThat(page1).doesNotContainAnyElementsOf(page2);
    }

    @Test
    void existsByTrackingNumberAndStopId_whenTrackingNumberAndStopIdDontMatch_thenReturnFalse() {

        // Given
        final String trackingNumber = "otherTrackingNumber";
        final long stopId = 123456L;
        final StopEntity stopEntity = itPersister.save(faker.entity.stop().build());
        itPersister.save(faker.entity.pkg(stopEntity).build());

        // When
        final boolean exists = subject.existsByTrackingNumberAndStopId(trackingNumber, stopId);

        // Then
        assertThat(exists).isFalse();
    }

    @Test
    void existsByTrackingNumberAndStopId_whenTrackingNumberMatchesButStopIdDoesnt_thenReturnFalse() {

        // Given
        final long stopId = 123456L;
        final StopEntity stopEntity = itPersister.save(faker.entity.stop().build());
        final PackageEntity packageEntity = itPersister.save(faker.entity.pkg(stopEntity).build());

        // When
        final boolean exists = subject.existsByTrackingNumberAndStopId(packageEntity.getTrackingNumber(), stopId);

        // Then
        assertThat(exists).isFalse();
    }

    @Test
    void existsByTrackingNumberAndStopId_whenStopIdMatchesButTrackingNumberDoesnt_thenReturnFalse() {

        // Given
        final String trackingNumber = "otherTrackingNumber";
        final StopEntity stopEntity = itPersister.save(faker.entity.stop().build());
        itPersister.save(faker.entity.pkg(stopEntity).build());

        // When
        final boolean exists = subject.existsByTrackingNumberAndStopId(trackingNumber, stopEntity.getId());

        // Then
        assertThat(exists).isFalse();
    }

    @Test
    void existsByTrackingNumberAndStopId_whenTrackingNumberAndStopIdMatches_thenReturnTrue() {

        // Given
        final StopEntity stopEntity = itPersister.save(faker.entity.stop().build());
        final PackageEntity packageEntity = itPersister.save(faker.entity.pkg(stopEntity).build());

        // When
        final boolean exists = subject.existsByTrackingNumberAndStopId(packageEntity.getTrackingNumber(), stopEntity.getId());

        // Then
        assertThat(exists).isTrue();
    }
}

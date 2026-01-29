package com.jumia.skylens.persistence.jpa.impl;

import com.jumia.skylens.domain.catalog.Stop;
import com.jumia.skylens.domain.catalog.StopPublishingFilter;
import com.jumia.skylens.domain.catalog.StopWithPackageQuantity;
import com.jumia.skylens.domain.catalog.enums.LastMileDeliveredBy;
import com.jumia.skylens.domain.catalog.enums.PaymentMethodType;
import com.jumia.skylens.domain.catalog.enums.ServiceCode;
import com.jumia.skylens.domain.catalog.enums.Size;
import com.jumia.skylens.domain.catalog.pages.PageRequest;
import com.jumia.skylens.persistence.api.exceptions.StopAlreadyExistsException;
import com.jumia.skylens.persistence.jpa.configuration.BaseTestIT;
import com.jumia.skylens.persistence.jpa.entities.StopEntity;
import com.jumia.skylens.persistence.jpa.entities.enums.PaymentMethodTypeEnum;
import com.jumia.skylens.persistence.jpa.entities.enums.ServiceCodeEnum;
import com.jumia.skylens.persistence.jpa.entities.enums.SizeEnum;
import com.jumia.skylens.persistence.jpa.providers.StopPublishingFilterProvider;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class StopDAOImplTestIT extends BaseTestIT {

    @Autowired
    private StopDAOImpl subject;

    @PersistenceContext
    private EntityManager entityManager;

    private final PageRequest pageRequest = PageRequest.of(0, 10);

    @Test
    void save_whenSavingStopWithAnAlreadyExistingHash_thenThrowStopAlreadyExistsException() {

        // Given
        final String hash = "hash";
        itPersister.save(faker.entity.stop().stopHash(hash).build());
        final Stop stop = faker.domain.stop().stopHash(hash).build();

        itPersister.flushAndClear1LevelCache();

        // When
        final ThrowableAssert.ThrowingCallable throwable = () -> subject.save(stop);

        // Then
        assertThatThrownBy(throwable)
                .isInstanceOf(StopAlreadyExistsException.class);
    }

    @Test
    void save_whenSavingStop_thenSaveItSuccessfully() {

        // Given
        final Stop stop = faker.domain.stop().build();

        // When
        final Stop savedStop = subject.save(stop);

        // Then
        itPersister.flushAndClear1LevelCache();

        final StopEntity stopEntity = entityManager.find(StopEntity.class, savedStop.getId());

        assertThat(stopEntity)
                .usingRecursiveComparison()
                .ignoringFields("createdAt", "updatedAt")
                .isEqualTo(StopEntity.builder()
                                   .id(savedStop.getId())
                                   .stopId(savedStop.getStopId())
                                   .stopHash(savedStop.getStopHash())
                                   .network(savedStop.getNetwork())
                                   .partnerSid(savedStop.getPartnerSid())
                                   .nodeSid(savedStop.getNodeSid())
                                   .nodeName(savedStop.getNodeName())
                                   .serviceCode(ServiceCodeEnum.valueOf(savedStop.getServiceCode().name()))
                                   .size(SizeEnum.valueOf(savedStop.getSize().name()))
                                   .type(StopEntity.StopType.valueOf(savedStop.getType().name()))
                                   .lastMileDeliveredBy(StopEntity.LastMileDeliveredBy.valueOf(savedStop.getLastMileDeliveredBy().name()))
                                   .eventDate(savedStop.getEventDate())
                                   .paymentMethodType(PaymentMethodTypeEnum.valueOf(stop.getPaymentMethodType().name()))
                                   .build());
    }

    @Test
    void existsByStopHash_whenStopHashDoesntExist_thenReturnFalse() {

        // Given
        final String hash = faker.lorem().characters(32);
        itPersister.save(faker.entity.stop().build());

        itPersister.flushAndClear1LevelCache();

        // When
        final boolean exists = subject.existsByStopHash(hash);

        // Then
        assertThat(exists).isFalse();
    }

    @Test
    void existsByStopHash_whenStopHashExists_thenReturnTrue() {

        // Given
        final StopEntity stopEntity = itPersister.save(faker.entity.stop().build());

        itPersister.flushAndClear1LevelCache();

        // When
        final boolean exists = subject.existsByStopHash(stopEntity.getStopHash());

        // Then
        assertThat(exists).isTrue();
    }

    @Test
    void findByStopHash_whenStopHashDoesntExist_thenReturnEmptyOptional() {

        // Given
        final String hash = faker.lorem().characters(32);
        itPersister.save(faker.entity.stop().build());

        itPersister.flushAndClear1LevelCache();

        // When
        final Optional<Stop> stop = subject.findByStopHash(hash);

        // Then
        assertThat(stop).isEmpty();
    }

    @Test
    void findByStopHash_whenStopHashExists_thenReturnOptionalWithStop() {

        // Given
        final StopEntity stopEntity = itPersister.save(faker.entity.stop().build());

        itPersister.flushAndClear1LevelCache();

        // When
        final Optional<Stop> stop = subject.findByStopHash(stopEntity.getStopHash());

        // Then
        assertThat(stop)
                .isNotEmpty()
                .get()
                .extracting(Stop::getStopHash)
                .isEqualTo(stopEntity.getStopHash());
    }

    @ParameterizedTest
    @ArgumentsSource(StopPublishingFilterProvider.class)
    void countUnpublishedStops_whenCalledWithFilter_thenReturnCorrectCount(Function<StopEntity, StopPublishingFilter> filter) {

        // Given
        final StopEntity stopEntity = itPersister.save(faker.entity.stop().type(StopEntity.StopType.LAST_MILE).build());
        itPersister.save(faker.entity.stop().build());

        final StopPublishingFilter stopPublishingFilter = filter.apply(stopEntity);

        itPersister.flushAndClear1LevelCache();

        // When
        final long count = subject.countStopsForPublishing(stopPublishingFilter);

        // Then
        assertThat(count).isOne();
    }

    @ParameterizedTest
    @ArgumentsSource(StopPublishingFilterProvider.class)
    void getStopsForPublishing_whenCalledWithFilter_thenReturnCorrectStops(Function<StopEntity, StopPublishingFilter> filter) {

        // Given
        final StopEntity stopEntity = itPersister.save(faker.entity.stop().type(StopEntity.StopType.LAST_MILE).build());
        itPersister.save(faker.entity.stop().build());
        itPersister.save(faker.entity.pkg(stopEntity).build());
        itPersister.save(faker.entity.pkg(stopEntity).build());
        itPersister.save(faker.entity.pkg(stopEntity).build());

        final StopPublishingFilter stopPublishingFilter = filter.apply(stopEntity);

        itPersister.flushAndClear1LevelCache();

        // When
        final List<StopWithPackageQuantity> stops = subject.getStopsForPublishing(stopPublishingFilter, pageRequest);

        // Then
        assertThat(stops)
                .containsExactlyInAnyOrder(StopWithPackageQuantity.builder()
                                                   .id(stopEntity.getId())
                                                   .partnerSid(stopEntity.getPartnerSid())
                                                   .nodeSid(stopEntity.getNodeSid())
                                                   .network(stopEntity.getNetwork())
                                                   .stopId(stopEntity.getStopId())
                                                   .zoneName(stopEntity.getZoneName())
                                                   .serviceCode(ServiceCode.valueOf(stopEntity.getServiceCode().name()))
                                                   .paymentMethodType(PaymentMethodType.valueOf(stopEntity.getPaymentMethodType().name()))
                                                   .lastMileDeliveredBy(LastMileDeliveredBy.valueOf(stopEntity.getLastMileDeliveredBy()
                                                                                                            .name()))
                                                   .size(Size.valueOf(stopEntity.getSize().name()))
                                                   .quantity(3)
                                                   .eventDate(stopEntity.getEventDate())
                                                   .build());
    }

    @Test
    void markStopAsPublished_whenStopIdDoesntMatch_thenDontChangeStopPublishedField() {

        // Given
        final StopEntity stopEntity = itPersister.save(faker.entity.stop().published(false).build());

        itPersister.flushAndClear1LevelCache();

        // When
        subject.markStopAsPublished(123);

        // Then
        itPersister.flushAndClear1LevelCache();

        final StopEntity stop = entityManager.find(StopEntity.class, stopEntity.getId());

        assertThat(stop.isPublished()).isFalse();
    }

    @Test
    void markStopAsPublished_whenStopIdMatch_thenChangeStopPublishedFieldToTrue() {

        // Given
        final StopEntity stopEntity = itPersister.save(faker.entity.stop().published(false).build());

        itPersister.flushAndClear1LevelCache();

        // When
        subject.markStopAsPublished(stopEntity.getId());

        // Then
        itPersister.flushAndClear1LevelCache();

        final StopEntity stop = entityManager.find(StopEntity.class, stopEntity.getId());

        assertThat(stop.isPublished()).isTrue();
    }
}

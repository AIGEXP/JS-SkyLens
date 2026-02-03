package com.jumia.skylens.persistence.jpa.impl;

import com.jumia.skylens.domain.catalog.HubDailyMetric;
import com.jumia.skylens.persistence.jpa.configuration.BaseTestIT;
import com.jumia.skylens.persistence.jpa.entities.HubDailyMetricEntity;
import com.jumia.skylens.persistence.jpa.entities.HubDailyMetricEntityId;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
}

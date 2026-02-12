package com.jumia.skylens.persistence.jpa.impl;

import com.jumia.skylens.domain.catalog.NetworkThreshold;
import com.jumia.skylens.domain.catalog.ReportType;
import com.jumia.skylens.persistence.jpa.configuration.BaseTestIT;
import com.jumia.skylens.persistence.jpa.entities.NetworkThresholdEntity;
import com.jumia.skylens.persistence.jpa.entities.NetworkThresholdEntityId;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class NetworkThresholdDAOImplTestIT extends BaseTestIT {

    @Autowired
    private NetworkThresholdDAOImpl subject;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void save_whenSaving_thenSaveItSuccessfully() {

        // Given
        final NetworkThreshold networkThreshold = faker.domain.networkThreshold();

        // When
        subject.save(networkThreshold);

        // Then
        itPersister.flushAndClear1LevelCache();

        final NetworkThresholdEntityId entityId = NetworkThresholdEntityId.builder()
                .reportType(NetworkThresholdEntityId.ReportType.valueOf(networkThreshold.reportType().name()))
                .network(networkThreshold.network())
                .build();

        final NetworkThresholdEntity entity = entityManager.find(NetworkThresholdEntity.class, entityId);

        assertThat(entity).isNotNull();
        assertThat(entity.getValue()).isEqualByComparingTo(networkThreshold.value());
    }

    @Test
    void save_whenSavingWithSameKey_thenUpdateThreshold() {

        // Given
        final NetworkThresholdEntity existingEntity = itPersister.save(faker.entity.networkThresholdEntity().build());

        itPersister.flushAndClear1LevelCache();

        final BigDecimal newValue = BigDecimal.valueOf(0.85);
        final NetworkThreshold updatedNetworkThreshold = NetworkThreshold.builder()
                .reportType(ReportType.valueOf(existingEntity.getId().getReportType().name()))
                .network(existingEntity.getId().getNetwork())
                .value(newValue)
                .build();

        // When
        subject.save(updatedNetworkThreshold);

        // Then
        itPersister.flushAndClear1LevelCache();

        final NetworkThresholdEntity entity = entityManager.find(NetworkThresholdEntity.class, existingEntity.getId());

        assertThat(entity).isNotNull();
        assertThat(entity.getValue()).isEqualByComparingTo(newValue);
    }
}

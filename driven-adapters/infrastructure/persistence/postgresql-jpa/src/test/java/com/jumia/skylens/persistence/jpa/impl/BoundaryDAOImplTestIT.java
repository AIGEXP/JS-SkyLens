package com.jumia.skylens.persistence.jpa.impl;

import com.jumia.skylens.domain.catalog.Boundary;
import com.jumia.skylens.domain.catalog.ReportType;
import com.jumia.skylens.persistence.jpa.configuration.BaseTestIT;
import com.jumia.skylens.persistence.jpa.entities.BoundaryEntity;
import com.jumia.skylens.persistence.jpa.entities.BoundaryEntityId;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class BoundaryDAOImplTestIT extends BaseTestIT {

    @Autowired
    private BoundaryDAOImpl subject;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void save_whenSaving_thenPersistSuccessfully() {

        // Given
        final Boundary boundary = faker.domain.boundary().build();

        // When
        final Boundary saved = subject.save(boundary);

        // Then
        itPersister.flushAndClear1LevelCache();

        final BoundaryEntityId id = BoundaryEntityId.builder()
                .country(boundary.country())
                .reportType(BoundaryEntityId.ReportType.valueOf(boundary.reportType().name()))
                .build();

        final BoundaryEntity entity = entityManager.find(BoundaryEntity.class, id);

        assertThat(entity)
                .isNotNull()
                .usingRecursiveComparison()
                .ignoringFields("updatedAt")
                .isEqualTo(BoundaryEntity.builder()
                                   .id(id)
                                   .warning(boundary.warning())
                                   .critical(boundary.critical())
                                   .build());

        assertThat(saved)
                .isNotNull()
                .usingRecursiveComparison()
                .ignoringFields("updatedAt")
                .isEqualTo(Boundary.builder()
                                   .country(boundary.country())
                                   .reportType(boundary.reportType())
                                   .warning(boundary.warning())
                                   .critical(boundary.critical())
                                   .build());
    }

    @Test
    void save_whenUpdatingExistingBoundary_thenOverwriteValues() {

        // Given
        itPersister.save(faker.entity.boundaryEntity().id(faker.entity.boundaryEntityId()
                                                                  .country("NG")
                                                                  .reportType(BoundaryEntityId.ReportType.LOSS_RATE)
                                                                  .build())
                                 .warning(BigDecimal.valueOf(0.50))
                                 .critical(BigDecimal.valueOf(0.40))
                                 .build());

        itPersister.flushAndClear1LevelCache();

        final Boundary boundary = Boundary.builder()
                .country("NG")
                .reportType(ReportType.LOSS_RATE)
                .warning(BigDecimal.valueOf(0.70))
                .critical(BigDecimal.valueOf(0.60))
                .build();

        // When
        final Boundary saved = subject.save(boundary);

        // Then
        itPersister.flushAndClear1LevelCache();

        final BoundaryEntityId id = BoundaryEntityId.builder()
                .country("NG")
                .reportType(BoundaryEntityId.ReportType.LOSS_RATE)
                .build();

        final BoundaryEntity entity = entityManager.find(BoundaryEntity.class, id);

        assertThat(entity.getWarning()).isEqualByComparingTo(BigDecimal.valueOf(0.70));
        assertThat(entity.getCritical()).isEqualByComparingTo(BigDecimal.valueOf(0.60));

        assertThat(saved.warning()).isEqualByComparingTo(BigDecimal.valueOf(0.70));
        assertThat(saved.critical()).isEqualByComparingTo(BigDecimal.valueOf(0.60));
    }
}

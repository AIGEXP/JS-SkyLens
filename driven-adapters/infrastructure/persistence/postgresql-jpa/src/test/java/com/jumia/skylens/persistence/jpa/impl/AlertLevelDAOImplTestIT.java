package com.jumia.skylens.persistence.jpa.impl;

import com.jumia.skylens.domain.catalog.AlertLevel;
import com.jumia.skylens.domain.catalog.ReportType;
import com.jumia.skylens.persistence.jpa.configuration.BaseTestIT;
import com.jumia.skylens.persistence.jpa.entities.AlertLevelEntity;
import com.jumia.skylens.persistence.jpa.entities.AlertLevelEntityId;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class AlertLevelDAOImplTestIT extends BaseTestIT {

    @Autowired
    private AlertLevelDAOImpl subject;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void save_whenSaving_thenPersistSuccessfully() {

        // Given
        final AlertLevel alertLevel = faker.domain.alertLevel().build();

        // When
        final AlertLevel saved = subject.save(alertLevel);

        // Then
        itPersister.flushAndClear1LevelCache();

        final AlertLevelEntityId id = AlertLevelEntityId.builder()
                .country(alertLevel.country())
                .reportType(AlertLevelEntityId.ReportType.valueOf(alertLevel.reportType().name()))
                .build();

        final AlertLevelEntity entity = entityManager.find(AlertLevelEntity.class, id);

        assertThat(entity)
                .isNotNull()
                .usingRecursiveComparison()
                .ignoringFields("updatedAt")
                .isEqualTo(AlertLevelEntity.builder()
                                   .id(id)
                                   .warningValue(alertLevel.warningValue())
                                   .criticalValue(alertLevel.criticalValue())
                                   .build());

        assertThat(saved)
                .isNotNull()
                .usingRecursiveComparison()
                .ignoringFields("updatedAt")
                .isEqualTo(AlertLevel.builder()
                                   .country(alertLevel.country())
                                   .reportType(alertLevel.reportType())
                                   .warningValue(alertLevel.warningValue())
                                   .criticalValue(alertLevel.criticalValue())
                                   .build());
    }

    @Test
    void save_whenUpdatingExistingAlertLevel_thenOverwriteValues() {

        // Given
        itPersister.save(faker.entity.alertLevelEntity().id(faker.entity.alertLevelEntityId()
                                                                  .country("NG")
                                                                  .reportType(AlertLevelEntityId.ReportType.LOSS_RATE)
                                                                  .build())
                                 .warningValue(BigDecimal.valueOf(0.50))
                                 .criticalValue(BigDecimal.valueOf(0.40))
                                 .build());

        itPersister.flushAndClear1LevelCache();

        final AlertLevel alertLevel = AlertLevel.builder()
                .country("NG")
                .reportType(ReportType.LOSS_RATE)
                .warningValue(BigDecimal.valueOf(0.70))
                .criticalValue(BigDecimal.valueOf(0.60))
                .build();

        // When
        final AlertLevel saved = subject.save(alertLevel);

        // Then
        itPersister.flushAndClear1LevelCache();

        final AlertLevelEntityId id = AlertLevelEntityId.builder()
                .country("NG")
                .reportType(AlertLevelEntityId.ReportType.LOSS_RATE)
                .build();

        final AlertLevelEntity entity = entityManager.find(AlertLevelEntity.class, id);

        assertThat(entity.getWarningValue()).isEqualByComparingTo(BigDecimal.valueOf(0.70));
        assertThat(entity.getCriticalValue()).isEqualByComparingTo(BigDecimal.valueOf(0.60));

        assertThat(saved.warningValue()).isEqualByComparingTo(BigDecimal.valueOf(0.70));
        assertThat(saved.criticalValue()).isEqualByComparingTo(BigDecimal.valueOf(0.60));
    }
}

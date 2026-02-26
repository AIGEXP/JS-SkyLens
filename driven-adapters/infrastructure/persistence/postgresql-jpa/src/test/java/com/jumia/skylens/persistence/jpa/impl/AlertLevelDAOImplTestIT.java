package com.jumia.skylens.persistence.jpa.impl;

import com.jumia.skylens.domain.catalog.AlertLevel;
import com.jumia.skylens.domain.catalog.ReportType;
import com.jumia.skylens.persistence.jpa.configuration.BaseTestIT;
import com.jumia.skylens.persistence.jpa.entities.AlertLevelEntity;
import com.jumia.skylens.persistence.jpa.entities.AlertLevelEntityId;
import com.jumia.skylens.persistence.jpa.entities.enums.ReportTypeEnum;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Optional;

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
                .reportType(ReportTypeEnum.valueOf(alertLevel.reportType().name()))
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
        itPersister.save(faker.entity.alertLevelEntity()
                                 .id(faker.entity.alertLevelEntityId()
                                             .country("NG")
                                             .reportType(ReportTypeEnum.LOSS_RATE)
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
                .reportType(ReportTypeEnum.LOSS_RATE)
                .build();

        final AlertLevelEntity entity = entityManager.find(AlertLevelEntity.class, id);

        assertThat(entity.getWarningValue()).isEqualByComparingTo(BigDecimal.valueOf(0.70));
        assertThat(entity.getCriticalValue()).isEqualByComparingTo(BigDecimal.valueOf(0.60));

        assertThat(saved.warningValue()).isEqualByComparingTo(BigDecimal.valueOf(0.70));
        assertThat(saved.criticalValue()).isEqualByComparingTo(BigDecimal.valueOf(0.60));
    }

    @Test
    void findByCountryAndReportType_whenNoValueExist_thenReturnEmptyOptional() {

        // Given
        final String country = "NG";
        final ReportType reportType = ReportType.SUCCESS_RATE;

        itPersister.save(buildAlertLevelEntity("CI", ReportTypeEnum.LOSS_RATE));
        itPersister.save(buildAlertLevelEntity(country, ReportTypeEnum.LOSS_RATE));
        itPersister.save(buildAlertLevelEntity("CI", ReportTypeEnum.SUCCESS_RATE));

        itPersister.flushAndClear1LevelCache();

        // When
        final Optional<AlertLevel> alertLevel = subject.findByCountryAndReportType(country, reportType);

        // Then
        assertThat(alertLevel).isEmpty();
    }

    @Test
    void findByCountryAndReportType_whenValueExists_thenReturnOptionalWithIt() {

        // Given
        final String country = "NG";
        final ReportType reportType = ReportType.SUCCESS_RATE;

        final AlertLevelEntity alertLevelEntity = buildAlertLevelEntity(country, ReportTypeEnum.SUCCESS_RATE);
        itPersister.save(alertLevelEntity);

        itPersister.flushAndClear1LevelCache();

        // When
        final Optional<AlertLevel> alertLevel = subject.findByCountryAndReportType(country, reportType);

        // Then
        assertThat(alertLevel)
                .contains(AlertLevel.builder()
                                  .country(alertLevelEntity.getId().getCountry())
                                  .reportType(ReportType.valueOf(alertLevelEntity.getId().getReportType().name()))
                                  .warningValue(alertLevelEntity.getWarningValue())
                                  .criticalValue(alertLevelEntity.getCriticalValue())
                                  .build());
    }

    private AlertLevelEntity buildAlertLevelEntity(String country, ReportTypeEnum reportTypeEnum) {

        return faker.entity.alertLevelEntity()
                .id(AlertLevelEntityId.builder()
                            .country(country)
                            .reportType(reportTypeEnum)
                            .build())
                .build();
    }
}

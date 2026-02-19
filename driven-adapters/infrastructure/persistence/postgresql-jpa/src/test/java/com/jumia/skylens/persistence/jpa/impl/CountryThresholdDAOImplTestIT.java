package com.jumia.skylens.persistence.jpa.impl;

import com.jumia.skylens.domain.catalog.CountryThreshold;
import com.jumia.skylens.domain.catalog.ReportType;
import com.jumia.skylens.persistence.jpa.configuration.BaseTestIT;
import com.jumia.skylens.persistence.jpa.entities.CountryThresholdEntity;
import com.jumia.skylens.persistence.jpa.entities.CountryThresholdEntityId;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class CountryThresholdDAOImplTestIT extends BaseTestIT {

    @Autowired
    private CountryThresholdDAOImpl subject;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void save_whenSaving_thenSaveItSuccessfully() {

        // Given
        final CountryThreshold countryThreshold = faker.domain.countryThreshold();

        // When
        subject.save(countryThreshold);

        // Then
        itPersister.flushAndClear1LevelCache();

        final CountryThresholdEntityId entityId = CountryThresholdEntityId.builder()
                .reportType(CountryThresholdEntityId.ReportType.valueOf(countryThreshold.reportType().name()))
                .country(countryThreshold.country())
                .build();

        final CountryThresholdEntity entity = entityManager.find(CountryThresholdEntity.class, entityId);

        assertThat(entity).isNotNull();
        assertThat(entity.getValue()).isEqualByComparingTo(countryThreshold.value());
    }

    @Test
    void save_whenSavingWithSameKey_thenUpdateThreshold() {

        // Given
        final CountryThresholdEntity existingEntity = itPersister.save(faker.entity.countryThresholdEntity().build());

        itPersister.flushAndClear1LevelCache();

        final BigDecimal newValue = BigDecimal.valueOf(0.85);
        final CountryThreshold updatedCountryThreshold = CountryThreshold.builder()
                .reportType(ReportType.valueOf(existingEntity.getId().getReportType().name()))
                .country(existingEntity.getId().getCountry())
                .value(newValue)
                .build();

        // When
        subject.save(updatedCountryThreshold);

        // Then
        itPersister.flushAndClear1LevelCache();

        final CountryThresholdEntity entity = entityManager.find(CountryThresholdEntity.class, existingEntity.getId());

        assertThat(entity).isNotNull();
        assertThat(entity.getValue()).isEqualByComparingTo(newValue);
    }

    @Test
    void findByCountryAndReportType_whenExists_thenReturnCountryThreshold() {

        // Given
        final CountryThresholdEntity existingEntity = itPersister.save(faker.entity.countryThresholdEntity().build());

        itPersister.flushAndClear1LevelCache();

        final String country = existingEntity.getId().getCountry();
        final ReportType reportType = ReportType.valueOf(existingEntity.getId().getReportType().name());

        // When
        final CountryThreshold result = subject.findByCountryAndReportType(country, reportType);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.country()).isEqualTo(country);
        assertThat(result.reportType()).isEqualTo(reportType);
        assertThat(result.value()).isEqualByComparingTo(existingEntity.getValue());
    }

    @Test
    void findByCountryAndReportType_whenNotExists_thenReturnNull() {

        // When
        final CountryThreshold result = subject.findByCountryAndReportType("ZZ", ReportType.SUCCESS_RATE);

        // Then
        assertThat(result).isNull();
    }
}

package com.jumia.skylens.persistence.jpa.converters;

import com.jumia.skylens.domain.catalog.CountryThreshold;
import com.jumia.skylens.persistence.jpa.entities.CountryThresholdEntity;
import com.jumia.skylens.persistence.jpa.entities.CountryThresholdEntityId;
import com.jumia.skylens.persistence.jpa.entities.enums.ReportTypeEnum;
import com.jumia.skylens.persistence.jpa.fakers.Faker;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CountryThresholdEntityConverterTest {

    private final CountryThresholdEntityConverter subject = new CountryThresholdEntityConverterImpl();

    private final Faker faker = new Faker();

    @Test
    void convert() {

        // Given
        final CountryThreshold countryThreshold = faker.domain.countryThreshold();

        // When
        final CountryThresholdEntity result = subject.convert(countryThreshold);

        // Then
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(CountryThresholdEntity.builder()
                                   .id(CountryThresholdEntityId.builder()
                                               .reportType(ReportTypeEnum.valueOf(countryThreshold.reportType().name()))
                                               .country(countryThreshold.country())
                                               .build())
                                   .value(countryThreshold.value())
                                   .build());
    }

    @Test
    void convert_whenEntityToDomin_convertSuccessfully() {

        // Given
        final CountryThresholdEntity entity = faker.entity.countryThresholdEntity().build();

        // When
        final CountryThreshold result = subject.convert(entity);

        // Then
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(CountryThreshold.builder()
                                   .reportType(com.jumia.skylens.domain.catalog.ReportType
                                                       .valueOf(entity.getId().getReportType().name()))
                                   .country(entity.getId().getCountry())
                                   .value(entity.getValue())
                                   .build());
    }
}

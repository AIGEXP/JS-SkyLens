package com.jumia.skylens.persistence.jpa.converters;

import com.jumia.skylens.domain.catalog.NetworkThreshold;
import com.jumia.skylens.persistence.jpa.entities.NetworkThresholdEntity;
import com.jumia.skylens.persistence.jpa.entities.NetworkThresholdEntityId;
import com.jumia.skylens.persistence.jpa.fakers.Faker;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NetworkThresholdEntityConverterTest {

    private final NetworkThresholdEntityConverter subject = new NetworkThresholdEntityConverterImpl();

    private final Faker faker = new Faker();

    @Test
    void convert() {

        // Given
        final NetworkThreshold networkThreshold = faker.domain.networkThreshold();

        // When
        final NetworkThresholdEntity result = subject.convert(networkThreshold);

        // Then
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(NetworkThresholdEntity.builder()
                                   .id(NetworkThresholdEntityId.builder()
                                               .reportType(NetworkThresholdEntityId.ReportType
                                                                   .valueOf(networkThreshold.reportType().name()))
                                               .network(networkThreshold.network())
                                               .build())
                                   .value(networkThreshold.value())
                                   .build());
    }

    @Test
    void convert_whenEntityToDomin_convertSuccessfully() {

        // Given
        final NetworkThresholdEntity entity = faker.entity.networkThresholdEntity().build();

        // When
        final NetworkThreshold result = subject.convert(entity);

        // Then
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(NetworkThreshold.builder()
                                   .reportType(com.jumia.skylens.domain.catalog.ReportType
                                                       .valueOf(entity.getId().getReportType().name()))
                                   .network(entity.getId().getNetwork())
                                   .value(entity.getValue())
                                   .build());
    }
}

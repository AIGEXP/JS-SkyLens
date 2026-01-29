package com.jumia.skylens.persistence.jpa.converters;

import com.jumia.skylens.domain.catalog.PackageSummary;
import com.jumia.skylens.domain.catalog.enums.ServiceCode;
import com.jumia.skylens.domain.catalog.enums.Size;
import com.jumia.skylens.persistence.jpa.fakers.Faker;
import com.jumia.skylens.persistence.jpa.projections.PackageProjection;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PackageSummaryConverterTest {

    private final PackageSummaryConverter subject = new PackageSummaryConverterImpl();

    private final Faker faker = new Faker();

    @Test
    void convert() {

        // Given
        final PackageProjection packageProjection = faker.entity.stopProjection().build();

        // When
        final PackageSummary result = subject.convert(packageProjection);

        // Then
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(PackageSummary.builder()
                                   .stopId(packageProjection.stopId())
                                   .serviceCode(ServiceCode.valueOf(packageProjection.serviceCode().name()))
                                   .size(Size.valueOf(packageProjection.size().name()))
                                   .paymentMethodName(packageProjection.paymentMethodName())
                                   .eventDate(packageProjection.eventDate())
                                   .driverName(packageProjection.driverName())
                                   .nodeName(packageProjection.nodeName())
                                   .build());
    }
}

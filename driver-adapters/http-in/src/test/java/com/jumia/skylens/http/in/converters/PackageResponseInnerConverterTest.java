package com.jumia.skylens.http.in.converters;

import com.jumia.skylens.domain.catalog.PackageSummary;
import com.jumia.skylens.http.in.model.PackageResponseInner;
import com.jumia.skylens.http.in.model.PackageResponseInnerDriver;
import com.jumia.skylens.http.in.model.PackageResponseInnerNode;
import com.jumia.skylens.http.in.model.PackageResponseInnerPaymentMethod;
import com.jumia.skylens.http.in.model.Size;
import com.jumia.skylens.test.fakers.DomainFaker;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PackageResponseInnerConverterTest {

    private final DomainFaker faker = new DomainFaker();

    private final PackageResponseInnerConverter subject = new PackageResponseInnerConverterImpl();

    @Test
    void convert() {

        // Given
        final PackageSummary packageSummary = faker.packageSummary().build();

        // When
        final PackageResponseInner result = subject.convert(packageSummary);

        // Then
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(buildPackageResponse(packageSummary));
    }

    private PackageResponseInner buildPackageResponse(PackageSummary packageSummary) {

        return PackageResponseInner.builder()
                .stopId(packageSummary.stopId())
                .eventDate(packageSummary.eventDate())
                .size(Size.valueOf(packageSummary.size().name()))
                .serviceCode(packageSummary.serviceCode().name())
                .node(PackageResponseInnerNode.builder()
                              .name(packageSummary.nodeName())
                              .build())
                .paymentMethod(PackageResponseInnerPaymentMethod.builder()
                                       .name(packageSummary.paymentMethodName())
                                       .build())
                .driver(PackageResponseInnerDriver.builder()
                                .name(packageSummary.driverName())
                                .build())
                .build();
    }
}

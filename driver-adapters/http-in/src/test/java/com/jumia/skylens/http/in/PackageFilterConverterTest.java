package com.jumia.skylens.http.in;

import com.jumia.skylens.domain.catalog.PackageFilter;
import com.jumia.skylens.domain.catalog.enums.Size;
import com.jumia.skylens.http.in.converters.PackageFilterConverter;
import com.jumia.skylens.http.in.converters.PackageFilterConverterImpl;
import com.jumia.skylens.http.in.fakers.RestFaker;
import com.jumia.skylens.http.in.model.PackageFilterRequest;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class PackageFilterConverterTest {

    private final RestFaker faker = new RestFaker();

    private final PackageFilterConverter packageFilterConverter = new PackageFilterConverterImpl();

    @Test
    void convert() {

        // Given
        final UUID partnerSid = UUID.randomUUID();
        final PackageFilterRequest packageFilterRequest = faker.packageFilterRequest().build();

        // When
        final PackageFilter result = packageFilterConverter.convert(packageFilterRequest, partnerSid);

        // Then
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(PackageFilter.builder()
                                   .partnerSid(partnerSid)
                                   .trackingNumber(packageFilterRequest.getTrackingNumber())
                                   .stopId(packageFilterRequest.getStopId())
                                   .serviceCode(packageFilterRequest.getServiceCode())
                                   .size(Optional.ofNullable(packageFilterRequest.getSize())
                                                 .map(size -> Size.valueOf(size.name()))
                                                 .orElse(null))
                                   .zoneSid(packageFilterRequest.getZoneSid())
                                   .paymentMethodSid(packageFilterRequest.getPaymentMethodSid())
                                   .nodeName(packageFilterRequest.getNodeName())
                                   .driverName(packageFilterRequest.getDriverName())
                                   .dateFrom(packageFilterRequest.getDateFrom())
                                   .dateTo(packageFilterRequest.getDateTo())
                                   .build());
    }
}

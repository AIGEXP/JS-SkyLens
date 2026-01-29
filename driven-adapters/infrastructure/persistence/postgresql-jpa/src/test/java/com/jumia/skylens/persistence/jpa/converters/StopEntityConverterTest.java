package com.jumia.skylens.persistence.jpa.converters;

import com.jumia.skylens.domain.catalog.Stop;
import com.jumia.skylens.persistence.jpa.entities.StopEntity;
import com.jumia.skylens.persistence.jpa.entities.enums.PaymentMethodTypeEnum;
import com.jumia.skylens.persistence.jpa.entities.enums.ServiceCodeEnum;
import com.jumia.skylens.persistence.jpa.entities.enums.SizeEnum;
import com.jumia.skylens.persistence.jpa.fakers.Faker;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StopEntityConverterTest {

    private final StopEntityConverter subject = new StopEntityConverterImpl();

    private final Faker faker = new Faker();

    @Test
    void convert_whenCalled_thenConvertSuccessfully() {

        // Given
        final Stop stop = faker.domain.stop().build();

        // When
        final StopEntity stopEntity = subject.convert(stop);

        // Then
        assertThat(stopEntity)
                .usingRecursiveComparison()
                .isEqualTo(StopEntity.builder()
                                   .stopId(stop.getStopId())
                                   .stopHash(stop.getStopHash())
                                   .network(stop.getNetwork())
                                   .partnerSid(stop.getPartnerSid())
                                   .nodeSid(stop.getNodeSid())
                                   .nodeName(stop.getNodeName())
                                   .serviceCode(ServiceCodeEnum.valueOf(stop.getServiceCode().name()))
                                   .size(SizeEnum.valueOf(stop.getSize().name()))
                                   .type(StopEntity.StopType.valueOf(stop.getType().name()))
                                   .lastMileDeliveredBy(StopEntity.LastMileDeliveredBy.valueOf(stop.getLastMileDeliveredBy().name()))
                                   .paymentMethodType(PaymentMethodTypeEnum.valueOf(stop.getPaymentMethodType().name()))
                                   .eventDate(stop.getEventDate())
                                   .build());
    }
}

package com.jumia.skylens.http.in.converters;

import com.jumia.skylens.domain.catalog.DateRange;
import com.jumia.skylens.domain.catalog.HubDailyMetric;
import com.jumia.skylens.domain.catalog.MetricsFilter;
import com.jumia.skylens.http.in.model.MovementType;
import com.jumia.skylens.http.in.model.PaymentType;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ValueMapping;

import java.util.UUID;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MetricsFilterConverter {

    default MetricsFilter convert(UUID serviceProviderSid,
                                  com.jumia.skylens.http.in.model.DateRange dateRange,
                                  UUID hubSid,
                                  PaymentType paymentType,
                                  MovementType movementType) {

        return MetricsFilter.builder()
                .serviceProviderSid(serviceProviderSid)
                .hubSid(hubSid)
                .dateRange(convertDateRange(dateRange))
                .paymentType(convertPaymentType(paymentType))
                .movementType(convertMovementType(movementType))
                .build();
    }

    default MetricsFilter convert(UUID serviceProviderSid,
                                  UUID hubSid,
                                  PaymentType paymentType,
                                  MovementType movementType) {

        return MetricsFilter.builder()
                .serviceProviderSid(serviceProviderSid)
                .hubSid(hubSid)
                .paymentType(convertPaymentType(paymentType))
                .movementType(convertMovementType(movementType))
                .build();
    }

    DateRange convertDateRange(com.jumia.skylens.http.in.model.DateRange dateRange);

    HubDailyMetric.PaymentType convertPaymentType(PaymentType paymentType);

    @ValueMapping(source = "DOOR", target = "DD")
    HubDailyMetric.MovementType convertMovementType(MovementType movementType);
}

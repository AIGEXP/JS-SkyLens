package com.jumia.skylens.http.in.converters;

import com.jumia.skylens.domain.catalog.DateRange;
import com.jumia.skylens.domain.catalog.SuccessRateMetricsFilter;
import com.jumia.skylens.http.in.model.MovementType;
import com.jumia.skylens.http.in.model.PaymentType;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.UUID;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SuccessRateMetricsFilterConverter {

    default SuccessRateMetricsFilter convert(String country,
                                             UUID serviceProviderSid,
                                             com.jumia.skylens.http.in.model.DateRange dateRange,
                                             UUID hubSid,
                                             PaymentType paymentType,
                                             MovementType movementType) {

        return SuccessRateMetricsFilter.builder()
                .country(country)
                .serviceProviderSid(serviceProviderSid)
                .hubSid(hubSid)
                .dateRange(convertDateRange(dateRange))
                .paymentType(convertPaymentType(paymentType))
                .movementType(convertMovementType(movementType))
                .build();
    }

    DateRange convertDateRange(com.jumia.skylens.http.in.model.DateRange dateRange);

    com.jumia.skylens.domain.catalog.PaymentType convertPaymentType(PaymentType paymentType);

    com.jumia.skylens.domain.catalog.MovementType convertMovementType(MovementType movementType);
}

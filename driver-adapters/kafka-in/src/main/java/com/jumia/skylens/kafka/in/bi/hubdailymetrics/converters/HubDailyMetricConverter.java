package com.jumia.skylens.kafka.in.bi.hubdailymetrics.converters;

import com.jumia.skylens.commons.converters.Converter;
import com.jumia.skylens.domain.catalog.HubDailyMetric;
import com.jumia.skylens.domain.catalog.MovementType;
import com.jumia.skylens.domain.catalog.PaymentType;
import com.jumia.skylens.kafka.in.bi.hubdailymetrics.dtos.HubDailyMetricDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ValueMapping;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface HubDailyMetricConverter extends Converter<HubDailyMetricDTO, HubDailyMetric> {

    @Override
    @Mapping(target = "day", source = "day", dateFormat = "yyyyMMdd")
    @Mapping(target = "packagesNoAttemptsOverThreeDays",
            expression = "java(source.packagesNoAttemptsFourDays() + source.packagesNoAttemptsOverFourDays())")
    @Mapping(target = "paymentType", source = "prePaid")
    HubDailyMetric convert(HubDailyMetricDTO source);

    default PaymentType convertPaymentType(boolean isPrePaid) {

        return isPrePaid ? PaymentType.PRE : PaymentType.POST;
    }

    @ValueMapping(source = "DD", target = "DOOR")
    MovementType convertMovementType(HubDailyMetricDTO.MovementType movementType);
}

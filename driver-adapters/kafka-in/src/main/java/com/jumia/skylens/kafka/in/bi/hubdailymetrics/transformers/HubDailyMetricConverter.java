package com.jumia.skylens.kafka.in.bi.hubdailymetrics.transformers;

import com.jumia.skylens.commons.converters.Converter;
import com.jumia.skylens.domain.catalog.HubDailyMetric;
import com.jumia.skylens.kafka.in.bi.hubdailymetrics.dtos.HubDailyMetricDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface HubDailyMetricConverter extends Converter<HubDailyMetricDTO, HubDailyMetric> {

    @Override
    @Mapping(target = "day", source = "day", dateFormat = "yyyyMMdd")
    @Mapping(target = "paymentType", source = "prePaid")
    HubDailyMetric convert(HubDailyMetricDTO source);

    default HubDailyMetric.PaymentType convertPaymentType(boolean isPrePaid) {

        return isPrePaid ? HubDailyMetric.PaymentType.PRE : HubDailyMetric.PaymentType.POST;
    }
}

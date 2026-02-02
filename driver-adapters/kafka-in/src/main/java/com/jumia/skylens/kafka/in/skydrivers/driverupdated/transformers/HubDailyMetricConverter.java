package com.jumia.skylens.kafka.in.skydrivers.driverupdated.transformers;

import com.jumia.skylens.commons.converters.Converter;
import com.jumia.skylens.domain.catalog.HubDailyMetric;
import com.jumia.skylens.kafka.in.skydrivers.driverupdated.dtos.HubDailyMetricDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface HubDailyMetricConverter extends Converter<HubDailyMetricDTO, HubDailyMetric> {

    @Override
    @Mapping(target = "day", source = "day", dateFormat = "yyyyMMdd")
    HubDailyMetric convert(HubDailyMetricDTO source);
}

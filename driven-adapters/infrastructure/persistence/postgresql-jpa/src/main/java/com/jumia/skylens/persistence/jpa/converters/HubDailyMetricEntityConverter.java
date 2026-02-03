package com.jumia.skylens.persistence.jpa.converters;

import com.jumia.skylens.commons.converters.Converter;
import com.jumia.skylens.domain.catalog.HubDailyMetric;
import com.jumia.skylens.persistence.jpa.entities.HubDailyMetricEntity;
import com.jumia.skylens.persistence.jpa.entities.HubDailyMetricEntityId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface HubDailyMetricEntityConverter extends Converter<HubDailyMetric, HubDailyMetricEntity> {

    @Override
    @Mapping(target = "id", source = ".")
    @Mapping(target = "createdAt", ignore = true)
    HubDailyMetricEntity convert(HubDailyMetric source);

    HubDailyMetricEntityId toEntityId(HubDailyMetric source);
}

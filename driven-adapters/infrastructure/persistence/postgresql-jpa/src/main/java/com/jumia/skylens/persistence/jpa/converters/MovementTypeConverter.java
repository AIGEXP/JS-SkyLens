package com.jumia.skylens.persistence.jpa.converters;

import com.jumia.skylens.commons.converters.Converter;
import com.jumia.skylens.domain.catalog.MovementType;
import com.jumia.skylens.persistence.jpa.entities.HubDailyMetricEntityId;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ValueMapping;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MovementTypeConverter extends Converter<MovementType, HubDailyMetricEntityId.MovementType> {

    @Override
    @ValueMapping(source = "DOOR", target = "DD")
    HubDailyMetricEntityId.MovementType convert(MovementType source);
}

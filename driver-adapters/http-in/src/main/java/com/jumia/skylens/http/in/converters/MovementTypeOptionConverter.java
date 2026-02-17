package com.jumia.skylens.http.in.converters;

import com.jumia.skylens.commons.converters.Converter;
import com.jumia.skylens.domain.catalog.MovementType;
import com.jumia.skylens.http.in.model.MovementTypeOption;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MovementTypeOptionConverter extends Converter<MovementType, MovementTypeOption> {

    @Override
    @Mapping(target = "value", expression = "java(com.jumia.skylens.http.in.model.MovementType.fromValue(source.name()))")
    @Mapping(target = "description", expression = "java(toDescription(source))")
    MovementTypeOption convert(MovementType source);

    default String toDescription(MovementType source) {

        return switch (source) {
            case DOOR -> "Door Delivery";
            case PUS -> "Pick Up Delivery";
        };
    }
}

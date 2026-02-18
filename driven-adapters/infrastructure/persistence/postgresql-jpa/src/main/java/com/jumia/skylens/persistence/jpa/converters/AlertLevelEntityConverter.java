package com.jumia.skylens.persistence.jpa.converters;

import com.jumia.skylens.commons.converters.Converter;
import com.jumia.skylens.domain.catalog.AlertLevel;
import com.jumia.skylens.persistence.jpa.entities.AlertLevelEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AlertLevelEntityConverter extends Converter<AlertLevel, AlertLevelEntity> {

    @Override
    @Mapping(target = "id", source = ".")
    @Mapping(target = "updatedAt", ignore = true)
    AlertLevelEntity convert(AlertLevel source);

    @Mapping(target = "country", source = "id.country")
    @Mapping(target = "reportType", source = "id.reportType")
    AlertLevel convert(AlertLevelEntity entity);
}

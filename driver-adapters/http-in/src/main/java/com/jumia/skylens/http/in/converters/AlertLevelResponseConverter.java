package com.jumia.skylens.http.in.converters;

import com.jumia.skylens.commons.converters.Converter;
import com.jumia.skylens.domain.catalog.AlertLevel;
import com.jumia.skylens.http.in.model.AlertLevelResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AlertLevelResponseConverter extends Converter<AlertLevel, AlertLevelResponse> {

}

package com.jumia.skylens.http.in.converters;

import com.jumia.skylens.commons.converters.Converter;
import com.jumia.skylens.domain.catalog.AlertLevel;
import com.jumia.skylens.http.in.model.AlertLevelRequest;
import com.jumia.skylens.http.in.model.ReportType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AlertLevelConverter extends Converter.WithThreeSources<AlertLevelRequest, String, ReportType, AlertLevel> {

    @Override
    @Mapping(target = "country", source = "country")
    @Mapping(target = "reportType", source = "reportType")
    @Mapping(target = "warningValue", source = "source.warningValue")
    @Mapping(target = "criticalValue", source = "source.criticalValue")
    AlertLevel convert(AlertLevelRequest source, String country, ReportType reportType);
}

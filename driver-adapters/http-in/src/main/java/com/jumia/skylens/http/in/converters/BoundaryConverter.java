package com.jumia.skylens.http.in.converters;

import com.jumia.skylens.commons.converters.Converter;
import com.jumia.skylens.domain.catalog.Boundary;
import com.jumia.skylens.http.in.model.BoundaryRequest;
import com.jumia.skylens.http.in.model.ReportType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BoundaryConverter extends Converter.WithThreeSources<BoundaryRequest, String, ReportType, Boundary> {

    @Override
    @Mapping(target = "country", source = "country")
    @Mapping(target = "reportType", source = "reportType")
    @Mapping(target = "warning", source = "source.warning")
    @Mapping(target = "critical", source = "source.critical")
    Boundary convert(BoundaryRequest source, String country, ReportType reportType);
}

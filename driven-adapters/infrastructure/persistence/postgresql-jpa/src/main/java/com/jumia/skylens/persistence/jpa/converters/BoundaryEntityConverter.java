package com.jumia.skylens.persistence.jpa.converters;

import com.jumia.skylens.commons.converters.Converter;
import com.jumia.skylens.domain.catalog.Boundary;
import com.jumia.skylens.persistence.jpa.entities.BoundaryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BoundaryEntityConverter extends Converter<Boundary, BoundaryEntity> {

    @Override
    @Mapping(target = "id", source = ".")
    @Mapping(target = "updatedAt", ignore = true)
    BoundaryEntity convert(Boundary source);

    @Mapping(target = "country", source = "id.country")
    @Mapping(target = "reportType", source = "id.reportType")
    Boundary convert(BoundaryEntity entity);
}

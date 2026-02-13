package com.jumia.skylens.http.in.converters;

import com.jumia.skylens.commons.converters.Converter;
import com.jumia.skylens.domain.catalog.Boundary;
import com.jumia.skylens.http.in.model.BoundaryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BoundaryResponseConverter extends Converter<Boundary, BoundaryResponse> {

}

package com.jumia.skylens.http.in.converters;

import com.jumia.skylens.commons.converters.Converter;
import com.jumia.skylens.domain.catalog.PackageNoAttemptsStatistics;
import com.jumia.skylens.http.in.model.NoAttemptsMetricsResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface NoAttemptsMetricResponseConverter extends Converter<PackageNoAttemptsStatistics, NoAttemptsMetricsResponse> {

}

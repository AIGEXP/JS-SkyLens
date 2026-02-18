package com.jumia.skylens.http.in.converters;

import com.jumia.skylens.commons.converters.Converter;
import com.jumia.skylens.domain.catalog.SuccessRateMetrics;
import com.jumia.skylens.http.in.model.SuccessRateMetricsResponse;
import com.jumia.skylens.http.in.model.SuccessRateMetricsResponseInner;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SuccessRateMetricResponseConverter extends Converter<SuccessRateMetrics, SuccessRateMetricsResponseInner> {

}

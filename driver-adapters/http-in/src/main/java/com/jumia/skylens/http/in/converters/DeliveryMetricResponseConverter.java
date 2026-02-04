package com.jumia.skylens.http.in.converters;

import com.jumia.skylens.commons.converters.Converter;
import com.jumia.skylens.domain.catalog.PackageMetrics;
import com.jumia.skylens.http.in.model.DeliveryMetricsResponseInner;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DeliveryMetricResponseConverter extends Converter<PackageMetrics, DeliveryMetricsResponseInner> {

}

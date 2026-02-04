package com.jumia.skylens.http.in.converters;

import com.jumia.skylens.commons.converters.Converter;
import com.jumia.skylens.domain.catalog.PackageMetrics;
import com.jumia.skylens.http.in.model.LossRateMetricsResponseInner;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LossRateMetricResponseConverter extends Converter<PackageMetrics, LossRateMetricsResponseInner> {

    @Override
    @Mapping(target = "packagesLost", source = "packagesLostAtHub")
    LossRateMetricsResponseInner convert(PackageMetrics source);
}

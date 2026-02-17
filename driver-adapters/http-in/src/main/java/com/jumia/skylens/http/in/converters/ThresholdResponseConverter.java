package com.jumia.skylens.http.in.converters;

import com.jumia.skylens.commons.converters.Converter;
import com.jumia.skylens.domain.catalog.CountryThreshold;
import com.jumia.skylens.http.in.model.ThresholdResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ThresholdResponseConverter extends Converter<CountryThreshold, ThresholdResponse> {

    @Override
    @Mapping(target = "targetRate", source = "value")
    ThresholdResponse convert(CountryThreshold source);

    default OffsetDateTime map(LocalDateTime value) {

        return value != null ? value.atOffset(ZoneOffset.UTC) : null;
    }
}

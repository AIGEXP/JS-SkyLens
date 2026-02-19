package com.jumia.skylens.persistence.jpa.converters;

import com.jumia.skylens.commons.converters.Converter;
import com.jumia.skylens.domain.catalog.CountryThreshold;
import com.jumia.skylens.domain.catalog.ReportType;
import com.jumia.skylens.persistence.jpa.entities.CountryThresholdEntity;
import com.jumia.skylens.persistence.jpa.entities.CountryThresholdEntityId;
import com.jumia.skylens.persistence.jpa.entities.enums.ReportTypeEnum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CountryThresholdEntityConverter extends Converter<CountryThreshold, CountryThresholdEntity> {

    @Override
    @Mapping(target = "id", source = ".")
    @Mapping(target = "updatedAt", ignore = true)
    CountryThresholdEntity convert(CountryThreshold source);

    @Mapping(target = "reportType", source = "id.reportType")
    @Mapping(target = "country", source = "id.country")
    CountryThreshold convert(CountryThresholdEntity source);

    CountryThresholdEntityId toEntityId(CountryThreshold source);

    default CountryThresholdEntityId toEntityId(String country, ReportType reportType) {

        return CountryThresholdEntityId.builder()
                .country(country)
                .reportType(ReportTypeEnum.valueOf(reportType.name()))
                .build();
    }
}

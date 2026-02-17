package com.jumia.skylens.http.in.converters;

import com.jumia.skylens.domain.catalog.CountryThreshold;
import com.jumia.skylens.http.in.model.ReportType;
import com.jumia.skylens.http.in.model.ThresholdRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CountryThresholdConverter {

    default CountryThreshold convert(String country, ReportType reportType, ThresholdRequest request) {

        return CountryThreshold.builder()
                .reportType(convertReportType(reportType))
                .country(country)
                .value(request.getTargetRate())
                .build();
    }

    com.jumia.skylens.domain.catalog.ReportType convertReportType(ReportType reportType);
}

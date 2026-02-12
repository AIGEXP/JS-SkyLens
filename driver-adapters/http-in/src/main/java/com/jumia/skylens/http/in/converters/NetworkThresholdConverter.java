package com.jumia.skylens.http.in.converters;

import com.jumia.skylens.domain.catalog.NetworkThreshold;
import com.jumia.skylens.http.in.model.ReportType;
import com.jumia.skylens.http.in.model.ThresholdRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface NetworkThresholdConverter {

    default NetworkThreshold convert(String country, ReportType reportType, ThresholdRequest request) {

        return NetworkThreshold.builder()
                .reportType(convertReportType(reportType))
                .network(country)
                .value(request.getTargetRate())
                .build();
    }

    com.jumia.skylens.domain.catalog.ReportType convertReportType(ReportType reportType);
}

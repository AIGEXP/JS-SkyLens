package com.jumia.skylens.domain.impl;

import com.jumia.skylens.domain.GetCountryThresholdUseCase;
import com.jumia.skylens.domain.catalog.CountryThreshold;
import com.jumia.skylens.domain.catalog.ReportType;
import com.jumia.skylens.persistence.api.CountryThresholdDAO;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetCountryThresholdUseCaseImpl implements GetCountryThresholdUseCase {

    private final CountryThresholdDAO countryThresholdDAO;

    @Override
    public CountryThreshold run(final String country, final ReportType reportType) {

        return countryThresholdDAO.findByCountryAndReportType(country, reportType);
    }
}

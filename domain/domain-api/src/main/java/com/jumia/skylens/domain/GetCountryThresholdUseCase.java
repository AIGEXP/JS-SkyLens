package com.jumia.skylens.domain;

import com.jumia.skylens.domain.catalog.CountryThreshold;
import com.jumia.skylens.domain.catalog.ReportType;

public interface GetCountryThresholdUseCase {

    CountryThreshold run(String country, ReportType reportType);
}

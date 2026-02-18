package com.jumia.skylens.persistence.api;

import com.jumia.skylens.domain.catalog.CountryThreshold;
import com.jumia.skylens.domain.catalog.ReportType;

public interface CountryThresholdDAO {

    CountryThreshold save(CountryThreshold countryThreshold);

    CountryThreshold findByCountryAndReportType(String country, ReportType reportType);
}

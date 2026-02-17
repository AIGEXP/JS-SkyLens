package com.jumia.skylens.domain;

import com.jumia.skylens.domain.catalog.CountryThreshold;

public interface UpsertCountryThresholdUseCase {

    CountryThreshold run(CountryThreshold countryThreshold);
}

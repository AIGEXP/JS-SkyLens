package com.jumia.skylens.domain.impl;

import com.jumia.skylens.domain.UpsertCountryThresholdUseCase;
import com.jumia.skylens.domain.catalog.CountryThreshold;
import com.jumia.skylens.persistence.api.CountryThresholdDAO;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UpsertCountryThresholdUseCaseImpl implements UpsertCountryThresholdUseCase {

    private final CountryThresholdDAO countryThresholdDAO;

    @Override
    public CountryThreshold run(final CountryThreshold countryThreshold) {

        return countryThresholdDAO.save(countryThreshold);
    }
}

package com.jumia.skylens.http.in.services;

import com.jumia.skylens.domain.UpsertCountryThresholdUseCase;
import com.jumia.skylens.domain.catalog.CountryThreshold;
import com.jumia.skylens.http.in.converters.CountryThresholdConverter;
import com.jumia.skylens.http.in.converters.ThresholdResponseConverter;
import com.jumia.skylens.http.in.model.ReportType;
import com.jumia.skylens.http.in.model.ThresholdRequest;
import com.jumia.skylens.http.in.model.ThresholdResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConfigurationService {

    private final UpsertCountryThresholdUseCase upsertCountryThresholdUseCase;

    private final CountryThresholdConverter countryThresholdConverter;

    private final ThresholdResponseConverter thresholdResponseConverter;

    public ThresholdResponse setThresholdTarget(final String country, final ReportType reportType, final ThresholdRequest request) {

        final CountryThreshold countryThreshold = countryThresholdConverter.convert(country, reportType, request);

        final CountryThreshold savedCountryThreshold = upsertCountryThresholdUseCase.run(countryThreshold);

        return thresholdResponseConverter.convert(savedCountryThreshold);
    }
}

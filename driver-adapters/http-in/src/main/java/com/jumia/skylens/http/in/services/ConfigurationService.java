package com.jumia.skylens.http.in.services;

import com.jumia.skylens.domain.SaveAlertLevelUseCase;
import com.jumia.skylens.domain.UpsertCountryThresholdUseCase;
import com.jumia.skylens.domain.catalog.AlertLevel;
import com.jumia.skylens.domain.catalog.CountryThreshold;
import com.jumia.skylens.http.in.converters.AlertLevelConverter;
import com.jumia.skylens.http.in.converters.AlertLevelResponseConverter;
import com.jumia.skylens.http.in.converters.CountryThresholdConverter;
import com.jumia.skylens.http.in.converters.ThresholdResponseConverter;
import com.jumia.skylens.http.in.model.AlertLevelRequest;
import com.jumia.skylens.http.in.model.AlertLevelResponse;
import com.jumia.skylens.http.in.model.ReportType;
import com.jumia.skylens.http.in.model.ThresholdRequest;
import com.jumia.skylens.http.in.model.ThresholdResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConfigurationService {

    private final UpsertCountryThresholdUseCase upsertCountryThresholdUseCase;

    private final SaveAlertLevelUseCase saveAlertLevelUseCase;

    private final CountryThresholdConverter countryThresholdConverter;

    private final ThresholdResponseConverter thresholdResponseConverter;

    private final AlertLevelConverter alertLevelConverter;

    private final AlertLevelResponseConverter alertLevelResponseConverter;

    public ThresholdResponse setThresholdTarget(final String country, final ReportType reportType, final ThresholdRequest request) {

        final CountryThreshold countryThreshold = countryThresholdConverter.convert(country, reportType, request);

        final CountryThreshold savedCountryThreshold = upsertCountryThresholdUseCase.run(countryThreshold);

        return thresholdResponseConverter.convert(savedCountryThreshold);
    }

    public AlertLevelResponse setAlertLevel(final String country,
                                            final ReportType reportType,
                                            final AlertLevelRequest alertLevelRequest) {

        final AlertLevel alertLevel = alertLevelConverter.convert(alertLevelRequest, country, reportType);

        final AlertLevel saved = saveAlertLevelUseCase.run(alertLevel);

        return alertLevelResponseConverter.convert(saved);
    }
}

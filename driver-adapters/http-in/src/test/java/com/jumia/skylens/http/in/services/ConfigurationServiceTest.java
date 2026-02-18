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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConfigurationServiceTest {

    @Mock
    private UpsertCountryThresholdUseCase upsertCountryThresholdUseCase;

    @Mock
    private SaveAlertLevelUseCase saveAlertLevelUseCase;

    @Mock
    private CountryThresholdConverter countryThresholdConverter;

    @Mock
    private ThresholdResponseConverter thresholdResponseConverter;

    @Mock
    private AlertLevelConverter alertLevelConverter;

    @Mock
    private AlertLevelResponseConverter alertLevelResponseConverter;

    @InjectMocks
    private ConfigurationService subject;

    @Test
    void setThresholdTarget_whenCalled_thenCallUseCase() {

        // Given
        final String country = "CI";
        final ReportType reportType = ReportType.SUCCESS_RATE;
        final ThresholdRequest request = mock(ThresholdRequest.class);
        final CountryThreshold countryThreshold = mock(CountryThreshold.class);
        final CountryThreshold savedCountryThreshold = mock(CountryThreshold.class);
        final ThresholdResponse thresholdResponse = mock(ThresholdResponse.class);

        when(countryThresholdConverter.convert(country, reportType, request)).thenReturn(countryThreshold);
        when(upsertCountryThresholdUseCase.run(countryThreshold)).thenReturn(savedCountryThreshold);
        when(thresholdResponseConverter.convert(savedCountryThreshold)).thenReturn(thresholdResponse);

        // When
        final ThresholdResponse result = subject.setThresholdTarget(country, reportType, request);

        // Then
        assertThat(result).isEqualTo(thresholdResponse);

        verify(countryThresholdConverter).convert(country, reportType, request);
        verify(upsertCountryThresholdUseCase).run(countryThreshold);
        verify(thresholdResponseConverter).convert(savedCountryThreshold);
    }

    @Test
    void setAlertLevel_whenCalled_thenConvertAndSaveAndReturnResponse() {

        // Given
        final String country = "CI";
        final ReportType reportType = ReportType.SUCCESS_RATE;
        final AlertLevelRequest alertLevelRequest = mock(AlertLevelRequest.class);
        final AlertLevel alertLevel = mock(AlertLevel.class);
        final AlertLevel saved = mock(AlertLevel.class);
        final AlertLevelResponse alertLevelResponse = mock(AlertLevelResponse.class);

        when(alertLevelConverter.convert(alertLevelRequest, country, reportType)).thenReturn(alertLevel);
        when(saveAlertLevelUseCase.run(alertLevel)).thenReturn(saved);
        when(alertLevelResponseConverter.convert(saved)).thenReturn(alertLevelResponse);

        // When
        final AlertLevelResponse result = subject.setAlertLevel(country, reportType, alertLevelRequest);

        // Then
        assertThat(result).isEqualTo(alertLevelResponse);

        verify(alertLevelConverter).convert(alertLevelRequest, country, reportType);
        verify(saveAlertLevelUseCase).run(alertLevel);
        verify(alertLevelResponseConverter).convert(saved);
    }
}

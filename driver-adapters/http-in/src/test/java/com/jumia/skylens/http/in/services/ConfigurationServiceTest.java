package com.jumia.skylens.http.in.services;

import com.jumia.skylens.domain.UpsertCountryThresholdUseCase;
import com.jumia.skylens.domain.catalog.CountryThreshold;
import com.jumia.skylens.http.in.converters.CountryThresholdConverter;
import com.jumia.skylens.http.in.converters.ThresholdResponseConverter;
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
    private CountryThresholdConverter countryThresholdConverter;

    @Mock
    private ThresholdResponseConverter thresholdResponseConverter;

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
}

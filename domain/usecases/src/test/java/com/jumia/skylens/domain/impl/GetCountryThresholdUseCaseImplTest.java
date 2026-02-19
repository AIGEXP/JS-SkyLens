package com.jumia.skylens.domain.impl;

import com.jumia.skylens.domain.catalog.CountryThreshold;
import com.jumia.skylens.domain.catalog.ReportType;
import com.jumia.skylens.persistence.api.CountryThresholdDAO;
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
class GetCountryThresholdUseCaseImplTest {

    @Mock
    private CountryThresholdDAO countryThresholdDAO;

    @InjectMocks
    private GetCountryThresholdUseCaseImpl subject;

    @Test
    void run_whenCalled_thenCallDAO() {

        // Given
        final String country = "CI";
        final ReportType reportType = ReportType.SUCCESS_RATE;
        final CountryThreshold countryThreshold = mock(CountryThreshold.class);

        when(countryThresholdDAO.findByCountryAndReportType(country, reportType)).thenReturn(countryThreshold);

        // When
        final CountryThreshold result = subject.run(country, reportType);

        // Then
        assertThat(result).isEqualTo(countryThreshold);

        verify(countryThresholdDAO).findByCountryAndReportType(country, reportType);
    }
}

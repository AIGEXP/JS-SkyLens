package com.jumia.skylens.domain.impl;

import com.jumia.skylens.domain.catalog.CountryThreshold;
import com.jumia.skylens.persistence.api.CountryThresholdDAO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpsertCountryThresholdUseCaseImplTest {

    @Mock
    private CountryThresholdDAO countryThresholdDAO;

    @InjectMocks
    private UpsertCountryThresholdUseCaseImpl subject;

    @Test
    void run_whenCalled_thenCallDAO() {

        // Given
        final CountryThreshold countryThreshold = mock(CountryThreshold.class);
        final CountryThreshold savedCountryThreshold = mock(CountryThreshold.class);

        when(countryThresholdDAO.save(countryThreshold)).thenReturn(savedCountryThreshold);

        // When
        final CountryThreshold result = subject.run(countryThreshold);

        // Then
        assertThat(result).isEqualTo(savedCountryThreshold);
    }
}

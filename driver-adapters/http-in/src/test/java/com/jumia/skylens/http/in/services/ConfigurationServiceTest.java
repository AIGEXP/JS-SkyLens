package com.jumia.skylens.http.in.services;

import com.jumia.skylens.domain.UpsertNetworkThresholdUseCase;
import com.jumia.skylens.domain.catalog.NetworkThreshold;
import com.jumia.skylens.http.in.converters.NetworkThresholdConverter;
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
    private UpsertNetworkThresholdUseCase upsertNetworkThresholdUseCase;

    @Mock
    private NetworkThresholdConverter networkThresholdConverter;

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
        final NetworkThreshold networkThreshold = mock(NetworkThreshold.class);
        final NetworkThreshold savedNetworkThreshold = mock(NetworkThreshold.class);
        final ThresholdResponse thresholdResponse = mock(ThresholdResponse.class);

        when(networkThresholdConverter.convert(country, reportType, request)).thenReturn(networkThreshold);
        when(upsertNetworkThresholdUseCase.run(networkThreshold)).thenReturn(savedNetworkThreshold);
        when(thresholdResponseConverter.convert(savedNetworkThreshold)).thenReturn(thresholdResponse);

        // When
        final ThresholdResponse result = subject.setThresholdTarget(country, reportType, request);

        // Then
        assertThat(result).isEqualTo(thresholdResponse);

        verify(networkThresholdConverter).convert(country, reportType, request);
        verify(upsertNetworkThresholdUseCase).run(networkThreshold);
        verify(thresholdResponseConverter).convert(savedNetworkThreshold);
    }
}

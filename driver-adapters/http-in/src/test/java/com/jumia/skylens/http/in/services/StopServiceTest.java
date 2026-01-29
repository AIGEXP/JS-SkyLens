package com.jumia.skylens.http.in.services;

import com.jumia.skylens.domain.PublishLastMileStopsUseCase;
import com.jumia.skylens.domain.catalog.StopPublishingFilter;
import com.jumia.skylens.http.in.converters.StopPublishingFilterConverter;
import com.jumia.skylens.http.in.model.StopPublishingRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StopServiceTest {

    @Mock
    private PublishLastMileStopsUseCase publishLastMileStopsUseCase;

    @Mock
    private StopPublishingFilterConverter stopPublishingFilterConverter;

    @InjectMocks
    private StopService subject;

    @Test
    void publishLastMileStops_whenCalled_thenCallUseCase() {

        // Given
        final StopPublishingRequest stopPublishingRequest = mock(StopPublishingRequest.class);
        final StopPublishingFilter stopPublishingFilter = mock(StopPublishingFilter.class);

        when(stopPublishingFilterConverter.convert(any())).thenReturn(stopPublishingFilter);

        // When
        subject.publishLastMileStops(stopPublishingRequest);

        // Then
        verify(stopPublishingFilterConverter).convert(stopPublishingRequest);
        verify(publishLastMileStopsUseCase).run(stopPublishingFilter);
    }
}

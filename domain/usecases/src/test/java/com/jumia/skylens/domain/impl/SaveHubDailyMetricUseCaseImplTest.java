package com.jumia.skylens.domain.impl;

import com.jumia.skylens.domain.catalog.HubDailyMetric;
import com.jumia.skylens.persistence.api.HubDailyMetricDAO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SaveHubDailyMetricUseCaseImplTest {

    @Mock
    private HubDailyMetricDAO hubDailyMetricDAO;

    @InjectMocks
    private SaveHubDailyMetricUseCaseImpl subject;

    @Test
    void run_whenCalled_thenCallDAO() {

        // Given
        final HubDailyMetric hubDailyMetric = mock(HubDailyMetric.class);

        // When
        subject.run(hubDailyMetric);

        // Then
        verify(hubDailyMetricDAO).save(hubDailyMetric);
    }
}

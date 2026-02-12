package com.jumia.skylens.domain.impl;

import com.jumia.skylens.domain.catalog.NetworkThreshold;
import com.jumia.skylens.persistence.api.NetworkThresholdDAO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpsertNetworkThresholdUseCaseImplTest {

    @Mock
    private NetworkThresholdDAO networkThresholdDAO;

    @InjectMocks
    private UpsertNetworkThresholdUseCaseImpl subject;

    @Test
    void run_whenCalled_thenCallDAO() {

        // Given
        final NetworkThreshold networkThreshold = mock(NetworkThreshold.class);
        final NetworkThreshold savedNetworkThreshold = mock(NetworkThreshold.class);

        when(networkThresholdDAO.save(networkThreshold)).thenReturn(savedNetworkThreshold);

        // When
        final NetworkThreshold result = subject.run(networkThreshold);

        // Then
        assertThat(result).isEqualTo(savedNetworkThreshold);
    }
}

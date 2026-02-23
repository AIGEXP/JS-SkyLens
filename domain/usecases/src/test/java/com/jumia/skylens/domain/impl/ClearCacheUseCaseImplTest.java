package com.jumia.skylens.domain.impl;

import com.jumia.skylens.ports.http.out.api.cache.CountryCacheManagement;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ClearCacheUseCaseImplTest {

    @Mock
    private CountryCacheManagement countryCacheManagement;

    @InjectMocks
    private ClearCacheUseCaseImpl subject;

    @Test
    void run_whenCalled_thenClearCache() {

        // When
        subject.run();

        // Then
        verify(countryCacheManagement).clearCache();
    }
}

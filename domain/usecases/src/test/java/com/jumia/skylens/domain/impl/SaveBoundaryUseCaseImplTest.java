package com.jumia.skylens.domain.impl;

import com.jumia.skylens.domain.catalog.Boundary;
import com.jumia.skylens.persistence.api.BoundaryDAO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SaveBoundaryUseCaseImplTest {

    @Mock
    private BoundaryDAO boundaryDAO;

    @InjectMocks
    private SaveBoundaryUseCaseImpl subject;

    @Test
    void run_whenCalled_thenCallDAOAndReturnResult() {

        // Given
        final Boundary boundary = mock(Boundary.class);
        final Boundary saved = mock(Boundary.class);

        when(boundaryDAO.save(boundary)).thenReturn(saved);

        // When
        final Boundary result = subject.run(boundary);

        // Then
        assertThat(result).isEqualTo(saved);
    }
}

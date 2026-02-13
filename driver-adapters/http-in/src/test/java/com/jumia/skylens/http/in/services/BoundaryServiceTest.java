package com.jumia.skylens.http.in.services;

import com.jumia.skylens.domain.SaveBoundaryUseCase;
import com.jumia.skylens.domain.catalog.Boundary;
import com.jumia.skylens.http.in.converters.BoundaryConverter;
import com.jumia.skylens.http.in.converters.BoundaryResponseConverter;
import com.jumia.skylens.http.in.model.BoundaryRequest;
import com.jumia.skylens.http.in.model.BoundaryResponse;
import com.jumia.skylens.http.in.model.ReportType;
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
class BoundaryServiceTest {

    @Mock
    private SaveBoundaryUseCase saveBoundaryUseCase;

    @Mock
    private BoundaryConverter boundaryConverter;

    @Mock
    private BoundaryResponseConverter boundaryResponseConverter;

    @InjectMocks
    private BoundaryService subject;

    @Test
    void save_whenCalled_thenConvertAndSaveAndReturnResponse() {

        // Given
        final String country = "CI";
        final ReportType reportType = ReportType.SUCCESS_RATE;
        final BoundaryRequest boundaryRequest = mock(BoundaryRequest.class);
        final Boundary boundary = mock(Boundary.class);
        final Boundary saved = mock(Boundary.class);
        final BoundaryResponse boundaryResponse = mock(BoundaryResponse.class);

        when(boundaryConverter.convert(boundaryRequest, country, reportType)).thenReturn(boundary);
        when(saveBoundaryUseCase.run(boundary)).thenReturn(saved);
        when(boundaryResponseConverter.convert(saved)).thenReturn(boundaryResponse);

        // When
        final BoundaryResponse result = subject.save(country, reportType, boundaryRequest);

        // Then
        assertThat(result).isEqualTo(boundaryResponse);

        verify(boundaryConverter).convert(boundaryRequest, country, reportType);
        verify(saveBoundaryUseCase).run(boundary);
        verify(boundaryResponseConverter).convert(saved);
    }
}

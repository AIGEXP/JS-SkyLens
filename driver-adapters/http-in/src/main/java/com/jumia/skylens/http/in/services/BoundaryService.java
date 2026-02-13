package com.jumia.skylens.http.in.services;

import com.jumia.skylens.domain.SaveBoundaryUseCase;
import com.jumia.skylens.domain.catalog.Boundary;
import com.jumia.skylens.http.in.converters.BoundaryConverter;
import com.jumia.skylens.http.in.converters.BoundaryResponseConverter;
import com.jumia.skylens.http.in.model.BoundaryRequest;
import com.jumia.skylens.http.in.model.BoundaryResponse;
import com.jumia.skylens.http.in.model.ReportType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoundaryService {

    private final SaveBoundaryUseCase saveBoundaryUseCase;

    private final BoundaryConverter boundaryConverter;

    private final BoundaryResponseConverter boundaryResponseConverter;

    public BoundaryResponse save(final String country,
                                 final ReportType reportType,
                                 final BoundaryRequest boundaryRequest) {

        final Boundary boundary = boundaryConverter.convert(boundaryRequest, country, reportType);

        final Boundary saved = saveBoundaryUseCase.run(boundary);

        return boundaryResponseConverter.convert(saved);
    }
}

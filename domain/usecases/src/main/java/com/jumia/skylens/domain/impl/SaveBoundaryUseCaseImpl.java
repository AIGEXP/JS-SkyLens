package com.jumia.skylens.domain.impl;

import com.jumia.skylens.domain.SaveBoundaryUseCase;
import com.jumia.skylens.domain.catalog.Boundary;
import com.jumia.skylens.domain.impl.validators.BoundaryValidator;
import com.jumia.skylens.persistence.api.BoundaryDAO;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SaveBoundaryUseCaseImpl implements SaveBoundaryUseCase {

    private final BoundaryDAO boundaryDAO;

    @Override
    public Boundary run(final Boundary boundary) {

        BoundaryValidator.validate(boundary);

        return boundaryDAO.save(boundary);
    }
}

package com.jumia.skylens.domain;

import com.jumia.skylens.domain.catalog.Boundary;

@FunctionalInterface
public interface SaveBoundaryUseCase {

    Boundary run(Boundary boundary);
}

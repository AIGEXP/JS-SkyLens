package com.jumia.skylens.persistence.api;

import com.jumia.skylens.domain.catalog.Boundary;

public interface BoundaryDAO {

    Boundary save(Boundary boundary);
}

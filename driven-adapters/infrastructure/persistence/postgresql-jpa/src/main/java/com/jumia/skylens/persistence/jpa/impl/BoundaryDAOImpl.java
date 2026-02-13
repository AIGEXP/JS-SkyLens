package com.jumia.skylens.persistence.jpa.impl;

import com.jumia.skylens.domain.catalog.Boundary;
import com.jumia.skylens.persistence.api.BoundaryDAO;
import com.jumia.skylens.persistence.jpa.converters.BoundaryEntityConverter;
import com.jumia.skylens.persistence.jpa.entities.BoundaryEntity;
import com.jumia.skylens.persistence.jpa.repositories.BoundaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoundaryDAOImpl implements BoundaryDAO {

    private final BoundaryRepository boundaryRepository;

    private final BoundaryEntityConverter boundaryEntityConverter;

    @Override
    public Boundary save(final Boundary boundary) {

        final BoundaryEntity entity = boundaryEntityConverter.convert(boundary);

        return boundaryEntityConverter.convert(boundaryRepository.save(entity));
    }
}

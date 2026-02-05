package com.jumia.skylens.domain.impl;

import com.jumia.skylens.domain.ListMovementTypeUseCase;
import com.jumia.skylens.domain.catalog.MovementType;

import java.util.List;

public class ListMovementTypeUseCaseImpl implements ListMovementTypeUseCase {

    @Override
    public List<MovementType> run() {

        return List.of(MovementType.values());
    }
}

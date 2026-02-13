package com.jumia.skylens.domain;

import com.jumia.skylens.domain.catalog.MovementType;

import java.util.List;

@FunctionalInterface
public interface ListMovementTypeUseCase {

    List<MovementType> run();
}

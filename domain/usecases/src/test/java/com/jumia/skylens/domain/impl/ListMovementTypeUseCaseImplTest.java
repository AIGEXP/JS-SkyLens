package com.jumia.skylens.domain.impl;

import com.jumia.skylens.domain.ListMovementTypeUseCase;
import com.jumia.skylens.domain.catalog.MovementType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ListMovementTypeUseCaseImplTest {

    private final ListMovementTypeUseCase subject = new ListMovementTypeUseCaseImpl();

    @Test
    void run_whenListMovementTypes_thenReturnAllMovementTypes() {

        // Given
        // When
        final List<MovementType> movementTypes = subject.run();

        // Then
        assertThat(movementTypes)
                .isNotEmpty()
                .isEqualTo(List.of(MovementType.values()));
    }
}

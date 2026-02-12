package com.jumia.skylens.http.in.converters;

import com.jumia.skylens.domain.catalog.MovementType;
import com.jumia.skylens.http.in.model.MovementTypeOption;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MovementTypeOptionConverterTest {

    private final MovementTypeOptionConverter subject = new MovementTypeOptionConverterImpl();

    @Test
    void convert_whenCalled_thenConvertSuccessfully() {

        // Given
        final MovementType movementType = MovementType.DOOR;

        // When
        final MovementTypeOption movementTypeOption = subject.convert(movementType);

        // Then
        assertThat(movementTypeOption.getValue()).isEqualTo(com.jumia.skylens.http.in.model.MovementType.DOOR);
        assertThat(movementTypeOption.getDescription()).isEqualTo("Door Delivery");
    }

    @Test
    void convert_whenCalledWithPus_thenConvertSuccessfully() {

        // Given
        final MovementType movementType = MovementType.PUS;

        // When
        final MovementTypeOption movementTypeOption = subject.convert(movementType);

        // Then
        assertThat(movementTypeOption.getValue()).isEqualTo(com.jumia.skylens.http.in.model.MovementType.PUS);
        assertThat(movementTypeOption.getDescription()).isEqualTo("Pick Up Delivery");
    }
}

package com.jumia.skylens.domain.impl;

import com.jumia.skylens.domain.ListPaymentTypeUseCase;
import com.jumia.skylens.domain.catalog.PaymentType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ListPaymentTypeUseCaseImplTest {

    private final ListPaymentTypeUseCase subject = new ListPaymentTypeUseCaseImpl();

    @Test
    void run_whenListPaymentTypes_thenReturnAllPaymentTypes() {

        // Given
        // When
        final List<PaymentType> paymentTypes = subject.run();

        // Then
        assertThat(paymentTypes)
                .isNotEmpty()
                .isEqualTo(List.of(PaymentType.values()));
    }
}

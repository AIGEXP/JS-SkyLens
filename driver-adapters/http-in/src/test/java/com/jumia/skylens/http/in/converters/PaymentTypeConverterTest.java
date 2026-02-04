package com.jumia.skylens.http.in.converters;

import com.jumia.skylens.domain.catalog.enums.PaymentMethodType;
import com.jumia.skylens.http.in.model.PaymentType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PaymentTypeConverterTest {

    private final PaymentTypeConverter subject = new PaymentTypeConverterImpl();

    @Test
    void convert_whenCalled_thenConvertSuccessfully() {

        // Given
        final PaymentMethodType paymentMethodType = PaymentMethodType.PRE;

        // When
        final PaymentType paymentType = subject.convert(paymentMethodType);

        // Then
        assertThat(paymentType).isEqualTo(PaymentType.PRE);
    }
}

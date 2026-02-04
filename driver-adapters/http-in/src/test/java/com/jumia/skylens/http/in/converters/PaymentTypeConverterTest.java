package com.jumia.skylens.http.in.converters;

import com.jumia.skylens.domain.catalog.PaymentType;
import com.jumia.skylens.http.in.model.PaymentTypeOption;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PaymentTypeConverterTest {

    private final PaymentTypeConverter subject = new PaymentTypeConverterImpl();

    @Test
    void convert_whenCalled_thenConvertSuccessfully() {

        // Given
        final PaymentType paymentType = PaymentType.PRE;

        // When
        final PaymentTypeOption paymentTypeOption = subject.convert(paymentType);

        // Then
        assertThat(paymentTypeOption.getValue()).isEqualTo(com.jumia.skylens.http.in.model.PaymentType.PRE);
        assertThat(paymentTypeOption.getDescription()).isEqualTo("Pre Paid");
    }

    @Test
    void convert_whenCalledWithPost_thenConvertSuccessfully() {

        // Given
        final PaymentType paymentType = PaymentType.POST;

        // When
        final PaymentTypeOption paymentTypeOption = subject.convert(paymentType);

        // Then
        assertThat(paymentTypeOption.getValue()).isEqualTo(com.jumia.skylens.http.in.model.PaymentType.POST);
        assertThat(paymentTypeOption.getDescription()).isEqualTo("Post Paid");
    }
}

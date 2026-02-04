package com.jumia.skylens.domain;

import com.jumia.skylens.domain.catalog.enums.PaymentMethodType;

import java.util.List;

public interface ListPaymentTypeUseCase {

    List<PaymentMethodType> run();
}

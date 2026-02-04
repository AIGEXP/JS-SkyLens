package com.jumia.skylens.domain;

import com.jumia.skylens.domain.catalog.PaymentType;

import java.util.List;

public interface ListPaymentTypeUseCase {

    List<PaymentType> run();
}

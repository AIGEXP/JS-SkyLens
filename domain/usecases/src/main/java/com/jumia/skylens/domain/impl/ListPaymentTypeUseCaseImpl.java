package com.jumia.skylens.domain.impl;

import com.jumia.skylens.domain.ListPaymentTypeUseCase;
import com.jumia.skylens.domain.catalog.enums.PaymentMethodType;

import java.util.List;

public class ListPaymentTypeUseCaseImpl implements ListPaymentTypeUseCase {

    @Override
    public List<PaymentMethodType> run() {

        return List.of(PaymentMethodType.values());
    }
}

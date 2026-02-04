package com.jumia.skylens.domain.impl;

import com.jumia.skylens.domain.ListPaymentTypeUseCase;
import com.jumia.skylens.domain.catalog.PaymentType;

import java.util.List;

public class ListPaymentTypeUseCaseImpl implements ListPaymentTypeUseCase {

    @Override
    public List<PaymentType> run() {

        return List.of(PaymentType.values());
    }
}

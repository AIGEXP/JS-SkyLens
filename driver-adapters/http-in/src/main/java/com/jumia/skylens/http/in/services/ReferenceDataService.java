package com.jumia.skylens.http.in.services;

import com.jumia.skylens.domain.ListDateRangeUseCase;
import com.jumia.skylens.domain.ListPaymentTypeUseCase;
import com.jumia.skylens.domain.catalog.enums.DateRangeType;
import com.jumia.skylens.domain.catalog.enums.PaymentMethodType;
import com.jumia.skylens.http.in.converters.ListDateRangeConverter;
import com.jumia.skylens.http.in.converters.PaymentTypeConverter;
import com.jumia.skylens.http.in.model.DateRangeOption;
import com.jumia.skylens.http.in.model.PaymentType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReferenceDataService {

    private final ListDateRangeUseCase listDateRangeUseCase;

    private final ListPaymentTypeUseCase listPaymentTypeUseCase;

    private final ListDateRangeConverter listDateRangeConverter;

    private final PaymentTypeConverter paymentTypeConverter;

    public List<DateRangeOption> listDateRangeTypes() {

        final List<DateRangeType> dateRangeTypes = listDateRangeUseCase.run();
        return listDateRangeConverter.convert(dateRangeTypes);
    }

    public List<PaymentType> listPaymentTypes() {

        final List<PaymentMethodType> paymentMethodTypes = listPaymentTypeUseCase.run();

        return paymentMethodTypes.stream()
                .map(paymentTypeConverter::convert)
                .toList();
    }
}

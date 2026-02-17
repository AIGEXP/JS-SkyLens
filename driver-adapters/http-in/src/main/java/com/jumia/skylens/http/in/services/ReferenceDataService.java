package com.jumia.skylens.http.in.services;

import com.jumia.skylens.domain.ListDateRangeUseCase;
import com.jumia.skylens.domain.ListMovementTypeUseCase;
import com.jumia.skylens.domain.ListPaymentTypeUseCase;
import com.jumia.skylens.domain.catalog.DateRange;
import com.jumia.skylens.domain.catalog.MovementType;
import com.jumia.skylens.domain.catalog.PaymentType;
import com.jumia.skylens.http.in.converters.ListDateRangeConverter;
import com.jumia.skylens.http.in.converters.MovementTypeOptionConverter;
import com.jumia.skylens.http.in.converters.PaymentTypeConverter;
import com.jumia.skylens.http.in.model.DateRangeOption;
import com.jumia.skylens.http.in.model.MovementTypeOption;
import com.jumia.skylens.http.in.model.PaymentTypeOption;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReferenceDataService {

    private final ListDateRangeUseCase listDateRangeUseCase;

    private final ListPaymentTypeUseCase listPaymentTypeUseCase;

    private final ListMovementTypeUseCase listMovementTypeUseCase;

    private final ListDateRangeConverter listDateRangeConverter;

    private final PaymentTypeConverter paymentTypeConverter;

    private final MovementTypeOptionConverter movementTypeOptionConverter;

    public List<DateRangeOption> listDateRanges() {

        final List<DateRange> dateRanges = listDateRangeUseCase.run();
        return listDateRangeConverter.convert(dateRanges);
    }

    public List<PaymentTypeOption> listPaymentTypes() {

        final List<PaymentType> paymentTypes = listPaymentTypeUseCase.run();

        return paymentTypes.stream()
                .map(paymentTypeConverter::convert)
                .toList();
    }

    public List<MovementTypeOption> listMovementTypes() {

        final List<MovementType> movementTypes = listMovementTypeUseCase.run();

        return movementTypes.stream()
                .map(movementTypeOptionConverter::convert)
                .toList();
    }
}

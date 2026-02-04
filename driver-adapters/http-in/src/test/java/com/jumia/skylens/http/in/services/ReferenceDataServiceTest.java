package com.jumia.skylens.http.in.services;

import com.jumia.skylens.domain.ListDateRangeUseCase;
import com.jumia.skylens.domain.ListPaymentTypeUseCase;
import com.jumia.skylens.domain.catalog.enums.DateRangeType;
import com.jumia.skylens.domain.catalog.enums.PaymentMethodType;
import com.jumia.skylens.http.in.converters.ListDateRangeConverter;
import com.jumia.skylens.http.in.converters.PaymentTypeConverter;
import com.jumia.skylens.http.in.model.DateRange;
import com.jumia.skylens.http.in.model.DateRangeOption;
import com.jumia.skylens.http.in.model.PaymentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReferenceDataServiceTest {

    @Mock
    private ListDateRangeUseCase listDateRangeUseCase;

    @Mock
    private ListPaymentTypeUseCase listPaymentTypeUseCase;

    @Mock
    private ListDateRangeConverter listDateRangeConverter;

    @Mock
    private PaymentTypeConverter paymentTypeConverter;

    @InjectMocks
    private ReferenceDataService subject;

    @Test
    void listDateRange_whenCalled_thenReturnAllDateRangeTypes() {

        // Given
        final List<DateRangeType> dateRangeTypes = List.of(DateRangeType.values());
        final List<DateRangeOption> expectedDateRange = Stream.of(DateRangeType.values())
                .map(dateRange -> new DateRangeOption(DateRange.valueOf(dateRange.name()), dateRange.getDescription()))
                .toList();

        // When
        when(listDateRangeUseCase.run()).thenReturn(dateRangeTypes);
        when(listDateRangeConverter.convert(dateRangeTypes)).thenReturn(expectedDateRange);
        final List<DateRangeOption> actualDateRanges = subject.listDateRangeTypes();

        // Then
        assertEquals(expectedDateRange, actualDateRanges);
    }

    @Test
    void listPaymentTypes_whenCalled_thenReturnAllPaymentTypes() {

        // Given
        final List<PaymentMethodType> paymentMethodTypes = List.of(PaymentMethodType.values());
        final List<PaymentType> expectedPaymentTypes = List.of(PaymentType.PRE, PaymentType.POS);

        // When
        when(listPaymentTypeUseCase.run()).thenReturn(paymentMethodTypes);
        when(paymentTypeConverter.convert(PaymentMethodType.PRE)).thenReturn(PaymentType.PRE);
        when(paymentTypeConverter.convert(PaymentMethodType.POS)).thenReturn(PaymentType.POS);

        final List<PaymentType> actualPaymentTypes = subject.listPaymentTypes();

        // Then
        assertEquals(expectedPaymentTypes, actualPaymentTypes);
    }
}

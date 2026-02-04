package com.jumia.skylens.http.in.services;

import com.jumia.skylens.domain.ListDateRangeUseCase;
import com.jumia.skylens.domain.ListPaymentTypeUseCase;
import com.jumia.skylens.domain.catalog.DateRange;
import com.jumia.skylens.domain.catalog.PaymentType;
import com.jumia.skylens.http.in.converters.ListDateRangeConverter;
import com.jumia.skylens.http.in.converters.PaymentTypeConverter;
import com.jumia.skylens.http.in.model.DateRangeOption;
import com.jumia.skylens.http.in.model.PaymentTypeOption;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReferenceDataServiceTest {

    private static final Map<DateRange, String> DESCRIPTIONS = Map.of(
            DateRange.CURRENT_WEEK, "Current Week",
            DateRange.LAST_WEEK, "Last Week",
            DateRange.LAST_FOUR_WEEKS, "Last 4 Weeks",
            DateRange.LAST_THREE_MONTHS, "Last 3 Months"
    );

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
    void listDateRanges_whenCalled_thenReturnAllDateRanges() {

        // Given
        final List<DateRange> dateRanges = List.of(DateRange.values());
        final List<DateRangeOption> expectedDateRanges = dateRanges.stream()
                .map(dateRange -> new DateRangeOption(
                        com.jumia.skylens.http.in.model.DateRange.valueOf(dateRange.name()),
                        DESCRIPTIONS.get(dateRange)))
                .toList();

        // When
        when(listDateRangeUseCase.run()).thenReturn(dateRanges);
        when(listDateRangeConverter.convert(dateRanges)).thenReturn(expectedDateRanges);
        final List<DateRangeOption> actualDateRanges = subject.listDateRanges();

        // Then
        assertEquals(expectedDateRanges, actualDateRanges);
    }

    @Test
    void listPaymentTypes_whenCalled_thenReturnAllPaymentTypes() {

        // Given
        final List<PaymentType> paymentTypes = List.of(PaymentType.values());
        final PaymentTypeOption preOption = new PaymentTypeOption(com.jumia.skylens.http.in.model.PaymentType.PRE, "Pre Paid");
        final PaymentTypeOption postOption = new PaymentTypeOption(com.jumia.skylens.http.in.model.PaymentType.POST, "Post Paid");
        final List<PaymentTypeOption> expectedPaymentTypes = List.of(preOption, postOption);

        // When
        when(listPaymentTypeUseCase.run()).thenReturn(paymentTypes);
        when(paymentTypeConverter.convert(PaymentType.PRE)).thenReturn(preOption);
        when(paymentTypeConverter.convert(PaymentType.POST)).thenReturn(postOption);

        final List<PaymentTypeOption> actualPaymentTypes = subject.listPaymentTypes();

        // Then
        assertEquals(expectedPaymentTypes, actualPaymentTypes);
    }
}

package com.jumia.skylens.http.in.controllers;

import com.jumia.skylens.http.in.model.DateRangeOption;
import com.jumia.skylens.http.in.model.PaymentType;
import com.jumia.skylens.http.in.services.ReferenceDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReferenceDataController implements ReferenceDataApi {

    private final ReferenceDataService referenceDataService;

    @Override
    public List<DateRangeOption> getDateRanges() {

        return referenceDataService.listDateRangeTypes();
    }

    @Override
    public List<PaymentType> getPaymentTypes() {

        return referenceDataService.listPaymentTypes();
    }
}

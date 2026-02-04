package com.jumia.skylens.http.in.services;

import com.jumia.skylens.domain.ListDateRangeUseCase;
import com.jumia.skylens.domain.catalog.DateRange;
import com.jumia.skylens.http.in.converters.ListDateRangeConverter;
import com.jumia.skylens.http.in.model.DateRangeOption;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReferenceDataService {

    private final ListDateRangeUseCase listDateRangeUseCase;

    private final ListDateRangeConverter listDateRangeConverter;

    public List<DateRangeOption> listDateRanges() {

        final List<DateRange> dateRanges = listDateRangeUseCase.run();
        return listDateRangeConverter.convert(dateRanges);
    }
}

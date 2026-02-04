package com.jumia.skylens.domain.impl;

import com.jumia.skylens.domain.ListDateRangeUseCase;
import com.jumia.skylens.domain.catalog.DateRange;

import java.util.List;

public class ListDateRangeUseCaseImpl implements ListDateRangeUseCase {

    @Override
    public List<DateRange> run() {

        return List.of(DateRange.values());
    }
}

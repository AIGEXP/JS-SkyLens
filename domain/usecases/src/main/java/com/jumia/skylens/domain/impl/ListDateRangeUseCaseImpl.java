package com.jumia.skylens.domain.impl;

import com.jumia.skylens.domain.ListDateRangeUseCase;
import com.jumia.skylens.domain.catalog.enums.DateRangeType;

import java.util.List;

public class ListDateRangeUseCaseImpl implements ListDateRangeUseCase {

    @Override
    public List<DateRangeType> run() {

        return List.of(DateRangeType.values());
    }
}

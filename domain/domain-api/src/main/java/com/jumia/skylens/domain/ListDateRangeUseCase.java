package com.jumia.skylens.domain;

import com.jumia.skylens.domain.catalog.enums.DateRangeType;

import java.util.List;

public interface ListDateRangeUseCase {

    List<DateRangeType> run();
}

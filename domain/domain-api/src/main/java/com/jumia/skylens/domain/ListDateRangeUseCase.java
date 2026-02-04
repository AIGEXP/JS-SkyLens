package com.jumia.skylens.domain;

import com.jumia.skylens.domain.catalog.DateRange;

import java.util.List;

public interface ListDateRangeUseCase {

    List<DateRange> run();
}

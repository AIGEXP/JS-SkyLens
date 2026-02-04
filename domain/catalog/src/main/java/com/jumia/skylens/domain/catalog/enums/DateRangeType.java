package com.jumia.skylens.domain.catalog.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum DateRangeType {

    CURRENT_WEEK("Current Week"),
    LAST_WEEK("Last Week"),
    LAST_FOUR_WEEKS("Last Four Weeks"),
    LAST_THREE_MONTHS("Last Three Months");

    private final String description;
}

package com.jumia.skylens.domain.catalog;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

public enum DateRange {
    CURRENT_WEEK,
    LAST_WEEK,
    LAST_FOUR_WEEKS,
    LAST_THREE_MONTHS;

    public LocalDate startDate() {

        final LocalDate today = LocalDate.now();

        return switch (this) {
            case CURRENT_WEEK -> today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
            case LAST_WEEK -> today.with(TemporalAdjusters.previous(DayOfWeek.MONDAY)).minusWeeks(1);
            case LAST_FOUR_WEEKS -> today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).minusWeeks(4);
            case LAST_THREE_MONTHS -> today.withDayOfMonth(1).minusMonths(3);
        };
    }

    public Granularity granularity() {

        return switch (this) {
            case CURRENT_WEEK, LAST_WEEK -> Granularity.DAILY;
            case LAST_FOUR_WEEKS -> Granularity.WEEKLY;
            case LAST_THREE_MONTHS -> Granularity.MONTHLY;
        };
    }
}

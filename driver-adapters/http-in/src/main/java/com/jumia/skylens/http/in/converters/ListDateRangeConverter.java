package com.jumia.skylens.http.in.converters;

import com.jumia.skylens.commons.converters.Converter;
import com.jumia.skylens.domain.catalog.DateRange;
import com.jumia.skylens.http.in.model.DateRangeOption;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ListDateRangeConverter extends Converter<List<DateRange>, List<DateRangeOption>> {

    @Override
    List<DateRangeOption> convert(List<DateRange> source);

    @Mapping(target = "value", expression = "java(com.jumia.skylens.http.in.model.DateRange.fromValue(source.name()))")
    @Mapping(target = "description", expression = "java(toDescription(source))")
    DateRangeOption toDateRangeOption(DateRange source);

    default String toDescription(DateRange source) {

        return switch (source) {
            case CURRENT_WEEK -> "Current Week";
            case LAST_WEEK -> "Last Week";
            case LAST_FOUR_WEEKS -> "Last 4 Weeks";
            case LAST_THREE_MONTHS -> "Last 3 Months";
        };
    }
}

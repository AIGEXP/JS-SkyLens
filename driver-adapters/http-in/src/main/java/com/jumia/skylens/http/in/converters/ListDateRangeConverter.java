package com.jumia.skylens.http.in.converters;

import com.jumia.skylens.commons.converters.Converter;
import com.jumia.skylens.domain.catalog.enums.DateRangeType;
import com.jumia.skylens.http.in.model.DateRangeOption;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ListDateRangeConverter extends Converter<List<DateRangeType>, List<DateRangeOption>> {

    @Override
    List<DateRangeOption> convert(List<DateRangeType> source);

    @Mapping(target = "value", expression = "java(com.jumia.skylens.http.in.model.DateRange.fromValue(source.name()))")
    DateRangeOption toDateRangeOption(DateRangeType source);
}

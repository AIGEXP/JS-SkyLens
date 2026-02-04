package com.jumia.skylens.http.in.converters;

import com.jumia.skylens.commons.converters.Converter;
import com.jumia.skylens.domain.catalog.PaymentType;
import com.jumia.skylens.http.in.model.PaymentTypeOption;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PaymentTypeConverter extends Converter<PaymentType, PaymentTypeOption> {

    @Override
    @Mapping(target = "value", expression = "java(com.jumia.skylens.http.in.model.PaymentType.fromValue(source.name()))")
    @Mapping(target = "description", expression = "java(toDescription(source))")
    PaymentTypeOption convert(PaymentType source);

    default String toDescription(PaymentType source) {

        return switch (source) {
            case PRE -> "Pre Paid";
            case POST -> "Post Paid";
        };
    }
}

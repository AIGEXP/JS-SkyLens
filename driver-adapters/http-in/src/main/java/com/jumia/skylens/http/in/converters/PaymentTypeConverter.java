package com.jumia.skylens.http.in.converters;

import com.jumia.skylens.commons.converters.Converter;
import com.jumia.skylens.domain.catalog.enums.PaymentMethodType;
import com.jumia.skylens.http.in.model.PaymentType;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PaymentTypeConverter extends Converter<PaymentMethodType, PaymentType> {

    @Override
    PaymentType convert(PaymentMethodType source);
}

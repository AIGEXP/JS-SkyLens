package com.jumia.skylens.http.out.skynet.converters;

import com.jumia.skylens.domain.catalog.ServiceProvider;
import com.jumia.skylens.http.out.skynet.responses.ServiceProviderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ServiceProviderConverter {

    @Mapping(target = "network", source = "network.code")
    ServiceProvider convert(ServiceProviderResponse serviceProviderResponse);
}

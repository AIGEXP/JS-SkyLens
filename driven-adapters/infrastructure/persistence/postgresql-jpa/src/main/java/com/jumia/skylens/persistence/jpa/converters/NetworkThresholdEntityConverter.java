package com.jumia.skylens.persistence.jpa.converters;

import com.jumia.skylens.commons.converters.Converter;
import com.jumia.skylens.domain.catalog.NetworkThreshold;
import com.jumia.skylens.persistence.jpa.entities.NetworkThresholdEntity;
import com.jumia.skylens.persistence.jpa.entities.NetworkThresholdEntityId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface NetworkThresholdEntityConverter extends Converter<NetworkThreshold, NetworkThresholdEntity> {

    @Override
    @Mapping(target = "id", source = ".")
    @Mapping(target = "updatedAt", ignore = true)
    NetworkThresholdEntity convert(NetworkThreshold source);

    @Mapping(target = "reportType", source = "id.reportType")
    @Mapping(target = "network", source = "id.network")
    NetworkThreshold convert(NetworkThresholdEntity source);

    NetworkThresholdEntityId toEntityId(NetworkThreshold source);
}

package com.jumia.skylens.http.in.converters;

import com.jumia.skylens.http.in.model.CacheResponseInner;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;
import java.util.Map;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CacheResponseConverter {

    default List<CacheResponseInner> convert(Map<String, String> source) {

        return source.entrySet().stream()
                .map(entry -> CacheResponseInner.builder()
                        .key(entry.getKey())
                        .value(entry.getValue())
                        .build())
                .toList();
    }
}

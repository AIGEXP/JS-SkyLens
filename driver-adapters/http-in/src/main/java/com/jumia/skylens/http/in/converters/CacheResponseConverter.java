package com.jumia.skylens.http.in.converters;

import com.jumia.skylens.http.in.model.CacheResponseInner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class CacheResponseConverter {

    public List<CacheResponseInner> convert(Map<String, String> source) {

        return source.entrySet().stream()
                .map(entry -> CacheResponseInner.builder()
                        .key(entry.getKey())
                        .value(entry.getValue())
                        .build())
                .toList();
    }
}

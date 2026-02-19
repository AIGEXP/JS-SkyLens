package com.jumia.skylens.http.in.acl.resources;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum CountryResource {
    THRESHOLD_READ("lpmt_threshold_read");

    private static final Map<String, CountryResource> RESOURCES = Arrays.stream(values())
            .collect(Collectors.toMap(countryResource -> countryResource.getValue().toLowerCase(), Function.identity()));

    private final String value;

    public static Optional<CountryResource> of(String resourceString) {

        return Optional.ofNullable(RESOURCES.get(resourceString.toLowerCase()));
    }
}

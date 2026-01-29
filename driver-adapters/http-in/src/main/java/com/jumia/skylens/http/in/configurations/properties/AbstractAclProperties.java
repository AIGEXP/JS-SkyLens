package com.jumia.skylens.http.in.configurations.properties;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EqualsAndHashCode
@Getter
public abstract class AbstractAclProperties implements AclProperties {

    private final String url;

    private final Integer readTimeout;

    private final boolean defaultInstance;

    private final String issuer;
}

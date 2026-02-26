package com.jumia.skylens.cache.country.configuration;

import java.time.Duration;

public interface CountryCacheProperties {

    boolean enabled();

    Duration expiration();

    int maxSize();
}

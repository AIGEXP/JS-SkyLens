package com.jumia.skylens.commons.configurations;

import java.time.Duration;

public interface CacheProperties {

    boolean enabled();

    Duration expiration();

    int maxSize();
}

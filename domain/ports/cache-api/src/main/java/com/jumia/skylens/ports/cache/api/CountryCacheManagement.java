package com.jumia.skylens.ports.cache.api;

import java.util.Map;

public interface CountryCacheManagement {

    boolean isEnabled();

    void clearCache();

    Map<String, String> getCache();
}

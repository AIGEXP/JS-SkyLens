package com.jumia.skylens.ports.http.out.api.cache;

import java.util.Map;

public interface CountryCacheManagement {

    boolean isEnabled();

    void clearCache();

    Map<String, String> getCache();
}

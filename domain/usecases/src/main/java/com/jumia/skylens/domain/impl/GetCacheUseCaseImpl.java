package com.jumia.skylens.domain.impl;

import com.jumia.skylens.domain.GetCacheUseCase;
import com.jumia.skylens.ports.cache.api.CountryCacheManagement;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class GetCacheUseCaseImpl implements GetCacheUseCase {

    private final CountryCacheManagement countryCacheManagement;

    @Override
    public Map<String, String> run() {

        return countryCacheManagement.getCache();
    }
}

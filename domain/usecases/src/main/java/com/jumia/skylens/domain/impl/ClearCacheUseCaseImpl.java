package com.jumia.skylens.domain.impl;

import com.jumia.skylens.domain.ClearCacheUseCase;
import com.jumia.skylens.ports.cache.api.CountryCacheManagement;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ClearCacheUseCaseImpl implements ClearCacheUseCase {

    private final CountryCacheManagement countryCacheManagement;

    @Override
    public void run() {

        countryCacheManagement.clearCache();
    }
}

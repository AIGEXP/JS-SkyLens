package com.jumia.skylens.domain.impl;

import com.jumia.skylens.domain.ClearCacheUseCase;
import com.jumia.skylens.ports.http.out.api.cache.CountryCacheManagement;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ClearCacheUseCaseImpl implements ClearCacheUseCase {

    private final CountryCacheManagement countryCacheManagement;

    @Override
    public void run() {

        countryCacheManagement.clearCache();
    }
}

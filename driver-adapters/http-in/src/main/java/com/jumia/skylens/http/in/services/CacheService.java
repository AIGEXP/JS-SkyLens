package com.jumia.skylens.http.in.services;

import com.jumia.skylens.domain.ClearCacheUseCase;
import com.jumia.skylens.domain.GetCacheUseCase;
import com.jumia.skylens.http.in.converters.CacheResponseConverter;
import com.jumia.skylens.http.in.model.CacheResponseInner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CacheService {

    private final ClearCacheUseCase clearCacheUseCase;

    private final GetCacheUseCase getCacheUseCase;

    private final CacheResponseConverter cacheResponseConverter;

    public void clearCache() {

        clearCacheUseCase.run();
    }

    public List<CacheResponseInner> listCache() {

        return cacheResponseConverter.convert(getCacheUseCase.run());
    }
}

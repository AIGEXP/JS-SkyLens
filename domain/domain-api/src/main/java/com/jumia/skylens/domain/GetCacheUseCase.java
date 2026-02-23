package com.jumia.skylens.domain;

import java.util.Map;

@FunctionalInterface
public interface GetCacheUseCase {

    Map<String, String> run();
}

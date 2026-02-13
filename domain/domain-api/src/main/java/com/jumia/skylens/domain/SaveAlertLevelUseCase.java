package com.jumia.skylens.domain;

import com.jumia.skylens.domain.catalog.AlertLevel;

@FunctionalInterface
public interface SaveAlertLevelUseCase {

    AlertLevel run(AlertLevel alertLevel);
}

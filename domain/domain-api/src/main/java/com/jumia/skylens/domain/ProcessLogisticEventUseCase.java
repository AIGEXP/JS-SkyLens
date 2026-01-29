package com.jumia.skylens.domain;

import com.jumia.skylens.domain.catalog.LogisticEvent;

@FunctionalInterface
public interface ProcessLogisticEventUseCase {

    void run(LogisticEvent logisticEvent);
}

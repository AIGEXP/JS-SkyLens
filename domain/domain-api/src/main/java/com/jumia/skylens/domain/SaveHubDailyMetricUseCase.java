package com.jumia.skylens.domain;

import com.jumia.skylens.domain.catalog.HubDailyMetric;

@FunctionalInterface
public interface SaveHubDailyMetricUseCase {

    void run(HubDailyMetric hubDailyMetric);
}

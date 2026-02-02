package com.jumia.skylens.domain.impl;

import com.jumia.skylens.domain.SaveHubDailyMetricUseCase;
import com.jumia.skylens.domain.catalog.HubDailyMetric;
import com.jumia.skylens.persistence.api.HubDailyMetricDAO;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SaveHubDailyMetricUseCaseImpl implements SaveHubDailyMetricUseCase {

    private final HubDailyMetricDAO hubDailyMetricDAO;

    @Override
    public void run(final HubDailyMetric hubDailyMetric) {

        hubDailyMetricDAO.save(hubDailyMetric);
    }
}

package com.jumia.skylens.persistence.jpa.impl;

import com.jumia.skylens.domain.catalog.HubDailyMetric;
import com.jumia.skylens.persistence.api.HubDailyMetricDAO;
import com.jumia.skylens.persistence.jpa.converters.HubDailyMetricEntityConverter;
import com.jumia.skylens.persistence.jpa.entities.HubDailyMetricEntity;
import com.jumia.skylens.persistence.jpa.repositories.HubDailyMetricRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HubDailyMetricDAOImpl implements HubDailyMetricDAO {

    private final HubDailyMetricRepository hubDailyMetricRepository;

    private final HubDailyMetricEntityConverter hubDailyMetricEntityConverter;

    @Override
    public void save(HubDailyMetric hubDailyMetric) {

        final HubDailyMetricEntity entity = hubDailyMetricEntityConverter.convert(hubDailyMetric);

        hubDailyMetricRepository.save(entity);
    }
}

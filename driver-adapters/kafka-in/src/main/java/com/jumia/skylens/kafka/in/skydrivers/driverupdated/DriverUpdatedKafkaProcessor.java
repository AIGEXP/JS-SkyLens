package com.jumia.skylens.kafka.in.skydrivers.driverupdated;

import com.jumia.skylens.domain.SaveHubDailyMetricUseCase;
import com.jumia.skylens.domain.catalog.HubDailyMetric;
import com.jumia.skylens.kafka.in.AbstractKafkaProcessor;
import com.jumia.skylens.kafka.in.KafkaReceiveSystem;
import com.jumia.skylens.kafka.in.skydrivers.driverupdated.dtos.HubDailyMetricDTO;
import com.jumia.skylens.kafka.in.skydrivers.driverupdated.transformers.HubDailyMetricConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DriverUpdatedKafkaProcessor extends AbstractKafkaProcessor<HubDailyMetricDTO> {

    private final HubDailyMetricConverter hubDailyMetricConverter;

    private final SaveHubDailyMetricUseCase saveHubDailyMetricUseCase;

    @Override
    public KafkaReceiveSystem getSystem() {

        return KafkaReceiveSystem.BI;
    }

    @Override
    public void process(final HubDailyMetricDTO payload) {

        final HubDailyMetric hubDailyMetric = hubDailyMetricConverter.convert(payload);

        saveHubDailyMetricUseCase.run(hubDailyMetric);
    }
}

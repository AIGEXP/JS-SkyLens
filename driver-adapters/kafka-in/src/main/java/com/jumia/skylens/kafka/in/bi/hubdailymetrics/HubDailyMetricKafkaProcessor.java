package com.jumia.skylens.kafka.in.bi.hubdailymetrics;

import com.jumia.skylens.domain.SaveHubDailyMetricUseCase;
import com.jumia.skylens.domain.catalog.HubDailyMetric;
import com.jumia.skylens.kafka.in.AbstractKafkaProcessor;
import com.jumia.skylens.kafka.in.KafkaReceiveSystem;
import com.jumia.skylens.kafka.in.bi.hubdailymetrics.converters.HubDailyMetricConverter;
import com.jumia.skylens.kafka.in.bi.hubdailymetrics.dtos.HubDailyMetricDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HubDailyMetricKafkaProcessor extends AbstractKafkaProcessor<HubDailyMetricDTO> {

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

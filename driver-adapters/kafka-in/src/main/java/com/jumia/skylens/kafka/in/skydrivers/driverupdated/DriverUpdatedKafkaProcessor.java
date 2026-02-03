package com.jumia.skylens.kafka.in.skydrivers.driverupdated;

import com.jumia.skylens.kafka.in.AbstractKafkaProcessor;
import com.jumia.skylens.kafka.in.KafkaReceiveSystem;
import com.jumia.skylens.kafka.in.skydrivers.driverupdated.dtos.HubPerformanceMetricsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DriverUpdatedKafkaProcessor extends AbstractKafkaProcessor<HubPerformanceMetricsDTO> {

    /*private final SaveExternalDriverCommandTransformer saveExternalDriverCommandTransformer;

    private final SaveExternalDriverUseCase saveExternalDriverUseCase;*/

    @Override
    public KafkaReceiveSystem getSystem() {

        return KafkaReceiveSystem.SKY_DRIVERS;
    }

    @Override
    public void process(final HubPerformanceMetricsDTO payload) {

        /*final SaveExternalDriverCommand saveExternalDriverCommand = saveExternalDriverCommandTransformer.transform(payload);

        saveExternalDriverUseCase.run(saveExternalDriverCommand);*/
    }
}

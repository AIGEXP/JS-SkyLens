package com.jumia.skylens.kafka.in.skydrivers.driverupdated;

import com.jumia.skylens.kafka.in.configuration.BaseTestKafkaIn;
import com.jumia.skylens.kafka.in.skydrivers.driverupdated.dtos.HubPerformanceMetricsDTO;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import java.util.Map;

@Import({DriverUpdatedKafkaListenerImpl.class, DriverUpdatedKafkaProcessor.class})
class DriverUpdatedKafkaListenerTest extends BaseTestKafkaIn {

    private static final String TOPIC = "services_all_skylens_topic_bi-reports";

    @MockitoSpyBean
    private DriverUpdatedKafkaProcessor driverUpdatedKafkaProcessor;

    @DynamicPropertySource
    private static void overrideProperties(DynamicPropertyRegistry registry) {

        registry.add("app.kafka.in.topic", () -> TOPIC);
        registry.add("spring.kafka.listener.concurrency", () -> 1);
        registry.add("spring.kafka.consumer.group-id", () -> "skylens-test");
    }

    @Test
    void onMessage_whenMessageIsReceived_thenProcessItCorrectly() {

        // Given
        final HubPerformanceMetricsDTO hubPerformanceMetricsDTO = faker.hubPerformanceMetricsDTO().build();
        //final SaveExternalDriverCommand saveExternalDriverCommand = Mockito.mock(SaveExternalDriverCommand.class);
        //final Map<String, String> headers = Map.of(MessageHeaders.NETWORK_HEADER_NAME, BASE_TENANT);

        //when(saveExternalDriverCommandTransformer.transform(any())).thenReturn(saveExternalDriverCommand);

        // When
        publishMessage(TOPIC, hubPerformanceMetricsDTO, Map.of());

        // Then
        /*await()
                .atMost(5, TimeUnit.SECONDS)
                .untilAsserted(() -> verify(driverUpdatedKafkaProcessor).process(any()));

        final ArgumentCaptor<DriverUpdatedDTO> argumentCaptor = ArgumentCaptor.forClass(DriverUpdatedDTO.class);

        verify(saveExternalDriverCommandTransformer).transform(argumentCaptor.capture());
        verify(saveExternalDriverUseCase).run(saveExternalDriverCommand);

        assertThat(argumentCaptor.getValue())
                .usingRecursiveComparison()
                .isEqualTo(driverUpdatedDTO);*/
    }
}

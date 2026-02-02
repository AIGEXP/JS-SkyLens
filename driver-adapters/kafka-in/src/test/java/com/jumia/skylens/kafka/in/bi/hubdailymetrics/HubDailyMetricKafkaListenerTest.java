package com.jumia.skylens.kafka.in.bi.hubdailymetrics;

import com.jumia.skylens.domain.SaveHubDailyMetricUseCase;
import com.jumia.skylens.domain.catalog.HubDailyMetric;
import com.jumia.skylens.kafka.in.bi.hubdailymetrics.dtos.HubDailyMetricDTO;
import com.jumia.skylens.kafka.in.bi.hubdailymetrics.converters.HubDailyMetricConverter;
import com.jumia.skylens.kafka.in.configuration.BaseTestKafkaIn;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Import({HubDailyMetricKafkaListenerImpl.class, HubDailyMetricKafkaProcessor.class})
class HubDailyMetricKafkaListenerTest extends BaseTestKafkaIn {

    private static final String TOPIC = "services_all_skylens_topic_bi-reports";

    @MockitoSpyBean
    private HubDailyMetricKafkaProcessor hubDailyMetricKafkaProcessor;

    @MockitoSpyBean
    private HubDailyMetricConverter hubDailyMetricConverter;

    @MockitoBean
    private SaveHubDailyMetricUseCase saveHubDailyMetricUseCase;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {

        registry.add("app.kafka.in.topic", () -> TOPIC);
        registry.add("spring.kafka.listener.concurrency", () -> 1);
        registry.add("spring.kafka.consumer.group-id", () -> "skylens");
    }

    @Test
    void onMessage_whenMessageIsReceived_thenProcessItCorrectly() {

        // Given
        final HubDailyMetricDTO hubDailyMetricDTO = faker.hubDailyMetricDTO().build();
        final HubDailyMetric hubDailyMetric = mock(HubDailyMetric.class);

        when(hubDailyMetricConverter.convert(any())).thenReturn(hubDailyMetric);

        // When
        publishMessage(TOPIC, hubDailyMetricDTO);

        // Then.
        await()
                .atMost(5, TimeUnit.SECONDS)
                .untilAsserted(() -> verify(hubDailyMetricKafkaProcessor).process(any()));

        final ArgumentCaptor<HubDailyMetricDTO> argumentCaptor = ArgumentCaptor.forClass(HubDailyMetricDTO.class);

        verify(hubDailyMetricConverter).convert(argumentCaptor.capture());
        verify(saveHubDailyMetricUseCase).run(hubDailyMetric);

        assertThat(argumentCaptor.getValue())
                .usingRecursiveComparison()
                .isEqualTo(hubDailyMetricDTO);
    }
}

package com.jumia.skylens.acceptance.utils;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Configuration;
import tools.jackson.databind.json.JsonMapper;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class LogisticEventPublisher {

    private static final String EXCHANGE = "hmt.logistics";

    private static final String ROUTING_KEY = "hmt.NG.packages";

    private static final String LOGISTIC_EVENT_NAME = "PACKAGE_SERVICE_SUCCESSFUL_ATTEMPT_CONFIRMED";

    private final RabbitTemplate rabbitTemplate;

    private final JsonMapper jsonMapper = new JsonMapper();

    public void publish(Object payload) {

        final Message message = new Message(jsonMapper.writeValueAsBytes(payload));
        final Map<String, @Nullable Object> headers = message.getMessageProperties().getHeaders();
        headers.put("event", LOGISTIC_EVENT_NAME);
        headers.put("eventDate", Instant.now().minus(1, ChronoUnit.DAYS));
        headers.put("network", "ng");

        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, message);
    }
}

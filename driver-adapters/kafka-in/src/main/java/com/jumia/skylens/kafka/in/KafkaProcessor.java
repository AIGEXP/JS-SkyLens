package com.jumia.skylens.kafka.in;

import tools.jackson.databind.json.JsonMapper;

public interface KafkaProcessor<T, C extends KafkaProcessorContext<T>> {

    void execute(C context);

    T convertPayload(final String payload, final JsonMapper jsonMapper);

    KafkaReceiveSystem getSystem();
}

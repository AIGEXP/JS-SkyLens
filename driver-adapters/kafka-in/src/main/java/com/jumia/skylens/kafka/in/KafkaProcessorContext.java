package com.jumia.skylens.kafka.in;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.Map;

public record KafkaProcessorContext<T>(T payload,
                                       Map<String, Object> headers,
                                       ConsumerRecord<String, String> consumerRecord) {

}

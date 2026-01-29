package com.jumia.skylens.telemetry.api.types;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TimerType implements MeterType {
    RABBITMQ_MESSAGE_PROCESSING("rabbitmq_message_processing_seconds", "Time to process RabbitMQ messages");

    private final String name;

    private final String description;
}

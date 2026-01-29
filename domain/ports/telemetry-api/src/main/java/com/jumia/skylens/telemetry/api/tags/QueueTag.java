package com.jumia.skylens.telemetry.api.tags;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class QueueTag implements MeterTag {

    private final String queueName;

    @Override
    public String getKey() {
        return "queue";
    }

    @Override
    public String getValue() {
        return queueName;
    }
}

package com.jumia.skylens.telemetry.api.types;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CounterType implements MeterType {
    EVENT_PUBLISH_FAILED("event_published_failed", "Event publish have failed");

    private final String name;

    private final String description;
}

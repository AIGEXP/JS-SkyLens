package com.jumia.skylens.telemetry.api.tags;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProcessorTag implements MeterTag {

    private final String processorName;

    public static ProcessorTag firstMile() {
        return new ProcessorTag("first_mile");
    }

    public static ProcessorTag lastMile() {
        return new ProcessorTag("last_mile");
    }

    @Override
    public String getKey() {
        return "processor";
    }

    @Override
    public String getValue() {
        return processorName;
    }
}

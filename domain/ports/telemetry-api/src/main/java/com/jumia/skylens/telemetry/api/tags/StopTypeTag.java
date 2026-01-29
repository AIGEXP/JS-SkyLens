package com.jumia.skylens.telemetry.api.tags;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StopTypeTag implements MeterTag {

    private final String stopType;

    public static StopTypeTag firstMile() {
        return new StopTypeTag("first_mile");
    }

    public static StopTypeTag lastMile() {
        return new StopTypeTag("last_mile");
    }

    @Override
    public String getKey() {
        return "stop_type";
    }

    @Override
    public String getValue() {
        return stopType;
    }
}

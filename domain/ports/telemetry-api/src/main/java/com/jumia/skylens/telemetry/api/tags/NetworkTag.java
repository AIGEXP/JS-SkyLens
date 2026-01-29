package com.jumia.skylens.telemetry.api.tags;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NetworkTag implements MeterTag {

    private final String network;

    @Override
    public String getKey() {
        return "network";
    }

    @Override
    public String getValue() {
        return network != null ? network : "unknown";
    }
}

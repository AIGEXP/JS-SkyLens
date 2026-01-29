package com.jumia.skylens.telemetry.api.tags;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OutcomeTag implements MeterTag {

    private final String outcome;

    public static OutcomeTag success() {
        return new OutcomeTag("success");
    }

    public static OutcomeTag failure() {
        return new OutcomeTag("failure");
    }

    public static OutcomeTag discarded() {
        return new OutcomeTag("discarded");
    }

    @Override
    public String getKey() {
        return "outcome";
    }

    @Override
    public String getValue() {
        return outcome;
    }
}

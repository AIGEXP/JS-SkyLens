package com.jumia.skylens.telemetry.api.types;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DistributionSummaryType implements MeterType {
    EMPTY("test_metric", "Test metric");

    private final String name;

    private final String description;
}

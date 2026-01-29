package com.jumia.skylens.telemetry.api;

import com.jumia.skylens.telemetry.api.tags.MeterTag;
import com.jumia.skylens.telemetry.api.types.CounterType;

import java.util.Set;

public interface CounterIncrementer {

    void increment(CounterType counterType, Set<MeterTag> tags);
}

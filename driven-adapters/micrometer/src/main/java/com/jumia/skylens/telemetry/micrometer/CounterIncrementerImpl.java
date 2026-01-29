package com.jumia.skylens.telemetry.micrometer;

import com.jumia.skylens.telemetry.api.CounterIncrementer;
import com.jumia.skylens.telemetry.api.tags.MeterTag;
import com.jumia.skylens.telemetry.api.types.CounterType;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.binder.BaseUnits;
import lombok.RequiredArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@SuppressFBWarnings(value = { "EI_EXPOSE_REP" }, justification = "I prefer to suppress these FindBugs warnings")
public class CounterIncrementerImpl implements CounterIncrementer {

    private final MeterRegistry meterRegistry;

    @Override
    public void increment(CounterType counterType, Set<MeterTag> tags) {

        final Set<Tag> micrometerTags = tags.stream().map(t -> Tag.of(t.getKey(), t.getValue())).collect(Collectors.toUnmodifiableSet());

        final Counter counter = Counter.builder(counterType.getName())
                .description(counterType.getDescription())
                .tags(micrometerTags)
                .baseUnit(BaseUnits.EVENTS)
                .register(meterRegistry);

        counter.increment();
    }
}

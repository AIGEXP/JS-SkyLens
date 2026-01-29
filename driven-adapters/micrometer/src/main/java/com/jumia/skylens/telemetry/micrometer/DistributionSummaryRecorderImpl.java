package com.jumia.skylens.telemetry.micrometer;

import com.jumia.skylens.telemetry.api.DistributionSummaryRecorder;
import com.jumia.skylens.telemetry.api.tags.MeterTag;
import com.jumia.skylens.telemetry.api.types.DistributionSummaryType;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import lombok.RequiredArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class DistributionSummaryRecorderImpl implements DistributionSummaryRecorder {

    private final MeterRegistry meterRegistry;

    @Override
    public void record(DistributionSummaryType summaryType, long amount, Set<MeterTag> tags) {

        final Set<Tag> micrometerTags = tags.stream()
                .map(tag -> Tag.of(tag.getKey(), tag.getValue()))
                .collect(Collectors.toUnmodifiableSet());

        final DistributionSummary summary = DistributionSummary.builder(summaryType.getName())
                .description(summaryType.getDescription())
                .tags(micrometerTags)
                .register(meterRegistry);

        summary.record(amount);
    }
}

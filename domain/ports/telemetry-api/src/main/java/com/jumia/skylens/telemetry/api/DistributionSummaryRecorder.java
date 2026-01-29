package com.jumia.skylens.telemetry.api;

import com.jumia.skylens.telemetry.api.tags.MeterTag;
import com.jumia.skylens.telemetry.api.types.DistributionSummaryType;

import java.util.Set;

public interface DistributionSummaryRecorder {

    void record(DistributionSummaryType summaryType, long amount, Set<MeterTag> tags);
}

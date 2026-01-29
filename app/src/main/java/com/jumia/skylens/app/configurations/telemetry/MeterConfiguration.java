package com.jumia.skylens.app.configurations.telemetry;

import com.jumia.skylens.telemetry.api.CounterIncrementer;
import com.jumia.skylens.telemetry.api.DistributionSummaryRecorder;
import com.jumia.skylens.telemetry.micrometer.CounterIncrementerImpl;
import com.jumia.skylens.telemetry.micrometer.DistributionSummaryRecorderImpl;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@AllArgsConstructor
@Configuration
public class MeterConfiguration {

    @Bean
    CounterIncrementer counterIncrementer(MeterRegistry meterRegistry) {

        return new CounterIncrementerImpl(meterRegistry);
    }

    @Bean
    DistributionSummaryRecorder distributionSummaryRecorder(MeterRegistry meterRegistry) {

        return new DistributionSummaryRecorderImpl(meterRegistry);
    }

    @Bean
    MdcLogbackMetrics mdcLogbackMetrics() {

        return new MdcLogbackMetrics();
    }
}

package com.jumia.skylens.app.configurations.telemetry;

import com.jumia.skylens.telemetry.api.tags.ApplicationTag;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import org.springframework.boot.micrometer.metrics.autoconfigure.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MeterCustomizerConfiguration {

    @Bean
    MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() {

        return registry -> {
            final ApplicationTag applicationTag = new ApplicationTag();
            registry.config().commonTags(Tags.of(applicationTag.getKey(), applicationTag.getValue()));
        };
    }
}

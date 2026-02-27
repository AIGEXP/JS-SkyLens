package com.jumia.skylens.http.out.configuration;

import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.micrometer.tagged.TaggedCircuitBreakerMetrics;
import io.github.resilience4j.micrometer.tagged.TaggedRetryMetrics;
import io.github.resilience4j.retry.RetryRegistry;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ResilienceConfiguration {

    @Bean
    public CircuitBreakerRegistry circuitBreakerRegistry(final MeterRegistry meterRegistry) {

        final CircuitBreakerRegistry circuitBreakerRegistry = CircuitBreakerRegistry.ofDefaults();

        TaggedCircuitBreakerMetrics
                .ofCircuitBreakerRegistry(circuitBreakerRegistry)
                .bindTo(meterRegistry);

        return circuitBreakerRegistry;
    }

    @Bean
    public RetryRegistry retryRegistry(final MeterRegistry meterRegistry) {

        final RetryRegistry retryRegistry = RetryRegistry.ofDefaults();

        TaggedRetryMetrics
                .ofRetryRegistry(retryRegistry)
                .bindTo(meterRegistry);

        return retryRegistry;
    }
}

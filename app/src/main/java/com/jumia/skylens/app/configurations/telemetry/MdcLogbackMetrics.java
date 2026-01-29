package com.jumia.skylens.app.configurations.telemetry;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.LoggerContextListener;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.MeterBinder;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;


public class MdcLogbackMetrics implements MeterBinder, AutoCloseable {

    private final LoggerContext loggerContext;

    private final Map<MeterRegistry, MetricsTurboFilter> metricsTurboFilters = new HashMap<>();

    public MdcLogbackMetrics() {

        this.loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();

        loggerContext.addListener(new LoggerContextListener() {

            @Override
            public boolean isResetResistant() {

                return true;
            }

            @Override
            public void onReset(LoggerContext context) {

                // re-add turbo filter because reset clears the turbo filter list
                synchronized (metricsTurboFilters) {
                    for (MetricsTurboFilter metricsTurboFilter : metricsTurboFilters.values()) {
                        loggerContext.addTurboFilter(metricsTurboFilter);
                    }
                }
            }

            @Override
            public void onStart(LoggerContext context) {
                // no-op
            }

            @Override
            public void onStop(LoggerContext context) {
                // no-op
            }

            @Override
            public void onLevelChange(Logger logger, Level level) {
                // no-op
            }
        });
    }

    @Override
    public void bindTo(MeterRegistry registry) {

        final MetricsTurboFilter filter = new MetricsTurboFilter(registry);
        synchronized (metricsTurboFilters) {
            metricsTurboFilters.put(registry, filter);
            loggerContext.addTurboFilter(filter);
        }
    }

    @Override
    public void close() {

        synchronized (metricsTurboFilters) {
            for (MetricsTurboFilter metricsTurboFilter : metricsTurboFilters.values()) {
                loggerContext.getTurboFilterList().remove(metricsTurboFilter);
            }
        }
    }
}


package com.jumia.skylens.app.configurations.telemetry;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.turbo.TurboFilter;
import ch.qos.logback.core.spi.FilterReply;
import com.jumia.skylens.commons.logging.LoggingUtils;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.BaseUnits;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.slf4j.Marker;

import java.util.Optional;

@RequiredArgsConstructor
class MetricsTurboFilter extends TurboFilter {

    private final MeterRegistry registry;

    @Override
    public FilterReply decide(Marker marker,
                              Logger logger,
                              Level level,
                              String format,
                              Object[] params,
                              Throwable throwable) {

        // When filter is asked for decision for an isDebugEnabled call or similar test, there is no message (ie format)
        // and no intention to log anything with this call. We will not increment counters and can return immediately and
        // avoid the relatively expensive ThreadLocal access below. See also logback Logger.callTurboFilters().
        if (format == null) {
            return FilterReply.NEUTRAL;
        }

        // cannot use logger.isEnabledFor(level), as it would cause a StackOverflowError by calling this filter again!
        if (level.isGreaterOrEqual(logger.getEffectiveLevel())) {
            switch (level.toInt()) {
                case Level.ERROR_INT:
                    count("error", "Number of error level events that made it to the logs").increment();
                    break;
                case Level.WARN_INT:
                    count("warn", "Number of warn level events that made it to the logs").increment();
                    break;
                case Level.INFO_INT:
                    count("info", "Number of info level events that made it to the logs").increment();
                    break;
                case Level.DEBUG_INT:
                    count("debug", "Number of debug level events that made it to the logs").increment();
                    break;
                case Level.TRACE_INT:
                    count("trace", "Number of trace level events that made it to the logs").increment();
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + level);
            }
        }

        return FilterReply.NEUTRAL;
    }

    private Counter count(String error, String meterDescription) {

        final String system = Optional.ofNullable(MDC.get(LoggingUtils.SYSTEM_CONTEXT_KEY)).orElse("none");
        final String network = Optional.ofNullable(MDC.get(LoggingUtils.NETWORK_CONTEXT_KEY)).orElse("none");
        final String communicationType = Optional.ofNullable(MDC.get(LoggingUtils.COMMUNICATION_TYPE_CONTEXT_KEY)).orElse("none");

        return Counter.builder("logback.events")
                .tags("level", error).tags(LoggingUtils.SYSTEM_CONTEXT_KEY, system,
                                           LoggingUtils.NETWORK_CONTEXT_KEY, network,
                                           LoggingUtils.COMMUNICATION_TYPE_CONTEXT_KEY, communicationType)
                .description(meterDescription)
                .baseUnit(BaseUnits.EVENTS)
            .register(this.registry);
    }
}

package com.jumia.skylens.commons.logging;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.MDC;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * This class is responsible to deal with log context for the variables network, system and communicationType.<p/>
 * For the logContext variable {@see pt.aig.aigx.loggingcontext.context.LoggingContextUtils}
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LoggingUtils {

    public static final String SYSTEM_CONTEXT_KEY = "system";

    public static final String NETWORK_CONTEXT_KEY = "network";

    public static final String COMMUNICATION_TYPE_CONTEXT_KEY = "communicationType";

    private static final String LOG_CONTEXT = "logContext";

    private static final String DEFAULT = "all";

    public static void clearLogContext() {

        MDC.clear();
    }

    public static Map<String, String> getCurrentContext() {

        return MDC.getCopyOfContextMap();
    }

    public static void setLogContext(Map<String, String> context) {

        MDC.clear();
        MDC.setContextMap(context);
    }

    public static void setLogContext(LogContext context) {

        final String system = context.getSystem().toLowerCase();
        final String network = getOrDefault(context.getNetwork()).toLowerCase();
        final String communicationType = context.getCommunicationType();
        final Map<String, String> headers = context.getHeaders();
        final String logContext = getLogContext(system, network, communicationType);

        final Map<String, String> contextMap = buildContextMap(system, network, communicationType, logContext, headers);

        setLogContext(contextMap);
    }

    private static Map<String, String> buildContextMap(String system,
                                                       String network,
                                                       String communicationType,
                                                       String logContext,
                                                       Map<String, String> headers) {

        final Map<String, String> contextMap = new HashMap<>();
        contextMap.put(SYSTEM_CONTEXT_KEY, system);
        contextMap.put(NETWORK_CONTEXT_KEY, network);
        contextMap.put(COMMUNICATION_TYPE_CONTEXT_KEY, communicationType);
        contextMap.put(LOG_CONTEXT, logContext);
        Optional.of(headers).ifPresent(contextMap::putAll);
        return contextMap;
    }

    private static String getOrDefault(String value) {

        return Optional.ofNullable(value)
                .orElse(DEFAULT);
    }

    private static String getLogContext(String system, String network, String communicationType) {

        return String.format("%s.%s.%s", system, network, communicationType);
    }

    public static void runWithLogContext(Runnable runnable, LogContext context) {

        final Map<String, String> contextMap = MDC.getCopyOfContextMap();

        try {
            setLogContext(context);
            runnable.run();
        } finally {
            Optional.ofNullable(contextMap)
                    .ifPresent(MDC::setContextMap);
        }
    }

    public static <T> T getWithLogContext(Supplier<T> supplier, LogContext context) {

        final Map<String, String> contextMap = MDC.getCopyOfContextMap();

        try {
            setLogContext(context);
            return supplier.get();
        } finally {
            Optional.ofNullable(contextMap)
                    .ifPresent(MDC::setContextMap);
        }
    }
}

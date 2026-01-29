package com.jumia.skylens.commons.logging;

import lombok.Value;

import java.util.Map;

@Value(staticConstructor = "of")
public class LogContext {

    String system;

    String network;

    String communicationType;

    Map<String, String> headers;

    public static LogContext of(String system, String network, String communicationType) {

        return new LogContext(system, network, communicationType, Map.of());
    }

    public Map<String, String> getHeaders() {

        return Map.copyOf(headers);
    }
}

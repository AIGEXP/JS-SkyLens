package com.jumia.skylens.http.out.configuration;

import java.time.Duration;

public interface HttpOutProperties {

    String host();

    int readTimeout();

    int connectionTimeout();

    int maxIdleConnections();

    int keepAlive();

    CircuitBreakerProperties circuitBreaker();

    RetryProperties retry();

    interface CircuitBreakerProperties {

        Integer failureRateThreshold();

        Integer slidingWindowSize();

        Integer permittedNumberOfCallsInHalfOpenState();

        Duration waitDurationInOpenState();
    }

    interface RetryProperties {

        BackoffProperties backoff();

        Integer maxAttempts();

        interface BackoffProperties {

            Duration initialInterval();

            Double multiplier();

            Duration maxInterval();
        }
    }
}

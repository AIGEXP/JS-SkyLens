package com.jumia.skylens.http.out.factories;

import com.jumia.skylens.http.out.configuration.HttpOutProperties;
import com.jumia.skylens.ports.http.out.api.exceptions.HttpException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.core.IntervalFunction;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.ResourceAccessException;

import java.net.SocketTimeoutException;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResilienceFactory {

    public static CircuitBreaker createCircuitBreaker(final CircuitBreakerRegistry circuitBreakerRegistry,
                                                      final String name,
                                                      final HttpOutProperties.CircuitBreakerProperties circuitBreakerProperties) {

        final CircuitBreakerConfig.Builder builder = CircuitBreakerConfig.custom()
                .automaticTransitionFromOpenToHalfOpenEnabled(true);

        if (circuitBreakerProperties == null) {
            final CircuitBreaker cb = CircuitBreaker.of(name, builder.build());
            cb.transitionToDisabledState();
            return cb;
        }

        Optional.ofNullable(circuitBreakerProperties.failureRateThreshold()).ifPresent(builder::failureRateThreshold);
        Optional.ofNullable(circuitBreakerProperties.slidingWindowSize()).ifPresent(builder::slidingWindowSize);
        Optional.ofNullable(circuitBreakerProperties.waitDurationInOpenState()).ifPresent(builder::waitDurationInOpenState);
        Optional.ofNullable(circuitBreakerProperties.permittedNumberOfCallsInHalfOpenState())
                .ifPresent(builder::permittedNumberOfCallsInHalfOpenState);

        return circuitBreakerRegistry.circuitBreaker(name, builder.build());
    }

    public static Retry createRetry(final RetryRegistry retryRegistry,
                                    final String name,
                                    final HttpOutProperties.RetryProperties retryProperties) {

        final RetryConfig.Builder<Object> builder = RetryConfig.custom()
                .retryOnException(ResilienceFactory::shouldRetry);

        if (retryProperties != null) {

            if (retryProperties.backoff() != null) {
                builder.intervalFunction(IntervalFunction.ofExponentialBackoff(
                        retryProperties.backoff().initialInterval(),
                        retryProperties.backoff().multiplier(),
                        retryProperties.backoff().maxInterval()));
            }

            if (retryProperties.maxAttempts() != null) {
                builder.maxAttempts(retryProperties.maxAttempts());
            }
        }

        return retryRegistry.retry(name, builder.build());
    }

    private static boolean shouldRetry(final Throwable throwable) {

        return switch (throwable) {
            case HttpException e -> HttpStatusCode.valueOf(e.getHttpCode()).is5xxServerError();
            case ResourceAccessException e -> e.getCause() instanceof SocketTimeoutException;
            default -> false;
        };
    }
}

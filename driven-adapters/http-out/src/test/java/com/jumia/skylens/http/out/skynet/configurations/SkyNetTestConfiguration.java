package com.jumia.skylens.http.out.skynet.configurations;

import com.jumia.skylens.http.out.configuration.ResilienceConfiguration;
import com.jumia.skylens.test.testcontainers.MockServerContainerSingleton;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import io.micrometer.observation.ObservationRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import java.time.Duration;

@Import(ResilienceConfiguration.class)
@ComponentScan(basePackages = {
        "com.jumia.skylens.http.out.skynet"
})
public class SkyNetTestConfiguration {

    private static final String TEST_AUTH_TOKEN = "Bearer test-token-12345";

    @Bean
    ObservationRegistry observationRegistry() {

        return ObservationRegistry.create();
    }

    @Bean
    MeterRegistry meterRegistry() {

        return new SimpleMeterRegistry();
    }

    @Bean
    public SkyNetProperties skyNetProperties() {

        return new SkyNetProperties() {

            @Override
            public String authorizationToken() {

                return TEST_AUTH_TOKEN;
            }

            @Override
            public String host() {

                return MockServerContainerSingleton.getInstance().getEndpoint();
            }

            @Override
            public int readTimeout() {

                return 5000;
            }

            @Override
            public int connectionTimeout() {

                return 3000;
            }

            @Override
            public int maxIdleConnections() {

                return 10;
            }

            @Override
            public int keepAlive() {

                return 30000;
            }

            @Override
            public CircuitBreakerProperties circuitBreaker() {

                return new CircuitBreakerProperties() {

                    @Override
                    public Integer failureRateThreshold() {

                        return 50;
                    }

                    @Override
                    public Integer slidingWindowSize() {

                        return 10;
                    }

                    @Override
                    public Integer permittedNumberOfCallsInHalfOpenState() {

                        return 10;
                    }

                    @Override
                    public Duration waitDurationInOpenState() {

                        return Duration.ofSeconds(1);
                    }
                };
            }

            @Override
            public RetryProperties retry() {

                return new RetryProperties() {

                    @Override
                    public Integer maxAttempts() {

                        return 3;
                    }

                    @Override
                    public BackoffProperties backoff() {

                        return null;
                    }
                };
            }
        };
    }
}

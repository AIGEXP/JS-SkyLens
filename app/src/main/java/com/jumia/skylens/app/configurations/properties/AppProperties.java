package com.jumia.skylens.app.configurations.properties;

import com.jumia.skylens.commons.configurations.CacheProperties;
import com.jumia.skylens.http.in.configurations.PaginationConfiguration;
import com.jumia.skylens.http.out.skynet.configuration.SkyNetProperties;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.boot.convert.DurationUnit;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Value
@RequiredArgsConstructor(onConstructor = @__({@ConstructorBinding}))
@ConfigurationProperties("app")
public class AppProperties {

    HttpProperties http;

    PaginationProperties pagination;

    AclServiceProperties aclService;

    CorsProperties cors;

    @Value
    @RequiredArgsConstructor(onConstructor = @__({@ConstructorBinding}))
    @ConfigurationProperties("app.cors")
    public static class CorsProperties {

        public static final Duration DEFAULT_MAX_AGE = Duration.ofSeconds(1800);

        @org.springframework.beans.factory.annotation.Value("false")
        boolean enabled;

        String webMapping;

        List<String> allowedOrigins;

        List<String> allowedMethods;

        List<String> allowedHeaders;

        @DurationUnit(ChronoUnit.SECONDS)
        Duration maxAge = DEFAULT_MAX_AGE;
    }

    @Value
    @RequiredArgsConstructor(onConstructor = @__({@ConstructorBinding}))
    @ConfigurationProperties("app.pagination")
    public static class PaginationProperties implements PaginationConfiguration {

        Integer maxLimit;

        Integer maxExportLimit;

        Integer defaultLimit;
    }

    @Value
    @RequiredArgsConstructor(onConstructor = @__({@ConstructorBinding}))
    public static class AclServiceProperties {

        boolean enabled;

        AclPropertiesImpl internal;

        AclCachePropertiesImpl cache;
    }

    @Value
    @RequiredArgsConstructor(onConstructor = @__({@ConstructorBinding}))
    public static class HttpProperties {

        HttpOutProperties out;

        @Value
        @RequiredArgsConstructor(onConstructor = @__({@ConstructorBinding}))
        public static class HttpOutProperties {

            SkyNetPropertiesImpl skynet;

            @ConfigurationProperties("app.http.out.skynet")
            public record SkyNetPropertiesImpl(CachePropertiesImpl cache) implements SkyNetProperties {

            }
        }
    }

    public record CachePropertiesImpl(boolean enabled, Duration expiration, int maxSize) implements CacheProperties {

    }
}

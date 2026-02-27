package com.jumia.skylens.http.out.factories;

import com.jumia.skylens.http.out.configuration.HttpOutProperties;
import com.jumia.skylens.http.out.converter.ExceptionConverter;
import io.micrometer.observation.ObservationRegistry;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.core5.util.TimeValue;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.util.concurrent.TimeUnit;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RestClientFactory {

    private static final ExceptionConverter EXCEPTION_CONVERTER = new ExceptionConverter();

    public static RestClient.Builder buildRestClient(final HttpOutProperties httpOutProperties,
                                                     final ObservationRegistry observationRegistry) {

        final HttpClient httpClient = buildHttpClient(httpOutProperties);

        return RestClient.builder()
                .observationRegistry(observationRegistry)
                .requestFactory(new HttpComponentsClientHttpRequestFactory(httpClient))
                .defaultStatusHandler(HttpStatusCode::isError, (_, response) -> {
                    throw EXCEPTION_CONVERTER.convert(response.getStatusCode());
                });
    }

    private static HttpClient buildHttpClient(HttpOutProperties httpOutProperties) {

        return HttpClientBuilder.create()
                .setConnectionManager(PoolingHttpClientConnectionManagerBuilder.create()
                                              .setDefaultConnectionConfig(ConnectionConfig.custom()
                                                                                  .setConnectTimeout(httpOutProperties.connectionTimeout(),
                                                                                                     TimeUnit.MILLISECONDS)
                                                                                  .setSocketTimeout(httpOutProperties.readTimeout(),
                                                                                                    TimeUnit.MILLISECONDS)
                                                                                  .build())
                                              .setMaxConnTotal(httpOutProperties.maxIdleConnections())
                                              .build())
                .setKeepAliveStrategy(((_, _) -> TimeValue.ofMilliseconds(httpOutProperties.keepAlive())))
                .build();
    }
}

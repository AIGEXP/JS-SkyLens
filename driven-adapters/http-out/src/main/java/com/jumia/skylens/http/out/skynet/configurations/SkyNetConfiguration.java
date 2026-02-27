package com.jumia.skylens.http.out.skynet.configurations;

import com.jumia.skylens.http.out.factories.ResilienceFactory;
import com.jumia.skylens.http.out.interceptors.HttpRequestLogInterceptor;
import com.jumia.skylens.http.out.skynet.client.SkyNetClient;
import com.jumia.skylens.http.out.skynet.converters.ServiceProviderConverter;
import com.jumia.skylens.http.out.skynet.impl.ServiceProviderFetcherImpl;
import com.jumia.skylens.ports.http.out.api.countries.ServiceProviderFetcher;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryRegistry;
import io.micrometer.observation.ObservationRegistry;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import static com.jumia.skylens.http.out.factories.RestClientFactory.buildRestClient;

@Configuration
public class SkyNetConfiguration {

    private static final String SYSTEM_NAME = "skynet";

    @Bean
    public ServiceProviderFetcher countryFetcher(final SkyNetClient skyNetClient,
                                                 final ServiceProviderConverter serviceProviderConverter,
                                                 final @Qualifier("skyNetCircuitBreaker") CircuitBreaker circuitBreaker,
                                                 final @Qualifier("skyNetRetry") Retry retry) {

        return new ServiceProviderFetcherImpl(skyNetClient, serviceProviderConverter, circuitBreaker, retry);
    }

    @Bean
    public SkyNetClient skyNetClient(final RestClient skyNetRestClient) {

        final HttpServiceProxyFactory factory = HttpServiceProxyFactory.builder()
                .exchangeAdapter(RestClientAdapter.create(skyNetRestClient))
                .build();

        return factory.createClient(SkyNetClient.class);
    }

    @Bean
    public RestClient skyNetRestClient(final SkyNetProperties skyNetProperties, final ObservationRegistry observationRegistry) {

        return buildRestClient(skyNetProperties, observationRegistry)
                .baseUrl(skyNetProperties.host())
                .defaultHeader(HttpHeaders.AUTHORIZATION, skyNetProperties.authorizationToken())
                .requestInterceptor(new HttpRequestLogInterceptor(SYSTEM_NAME))
                .build();
    }

    @Bean
    public CircuitBreaker skyNetCircuitBreaker(final CircuitBreakerRegistry circuitBreakerRegistry,
                                               final SkyNetProperties skyNetProperties) {

        return ResilienceFactory.createCircuitBreaker(circuitBreakerRegistry, SYSTEM_NAME, skyNetProperties.circuitBreaker());
    }

    @Bean
    public Retry skyNetRetry(final RetryRegistry retryRegistry, final SkyNetProperties skyNetProperties) {

        return ResilienceFactory.createRetry(retryRegistry, SYSTEM_NAME, skyNetProperties.retry());
    }
}

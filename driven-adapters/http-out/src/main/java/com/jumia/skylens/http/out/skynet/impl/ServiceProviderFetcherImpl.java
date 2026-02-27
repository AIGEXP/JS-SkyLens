package com.jumia.skylens.http.out.skynet.impl;

import com.jumia.skylens.domain.catalog.ServiceProvider;
import com.jumia.skylens.http.out.skynet.client.SkyNetClient;
import com.jumia.skylens.http.out.skynet.converters.ServiceProviderConverter;
import com.jumia.skylens.http.out.skynet.responses.ServiceProviderResponse;
import com.jumia.skylens.ports.http.out.api.countries.ServiceProviderFetcher;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.decorators.Decorators;
import io.github.resilience4j.retry.Retry;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class ServiceProviderFetcherImpl implements ServiceProviderFetcher {

    private final SkyNetClient skyNetClient;

    private final ServiceProviderConverter serviceProviderConverter;

    private final CircuitBreaker circuitBreaker;

    private final Retry retry;

    @Override
    public ServiceProvider get(final UUID serviceProviderSid) {

        final ServiceProviderResponse serviceProviderResponse = Decorators
                .ofSupplier(() -> skyNetClient.getServiceProviders(serviceProviderSid))
                .withCircuitBreaker(circuitBreaker)
                .withRetry(retry)
                .decorate()
                .get();

        return serviceProviderConverter.convert(serviceProviderResponse);
    }
}

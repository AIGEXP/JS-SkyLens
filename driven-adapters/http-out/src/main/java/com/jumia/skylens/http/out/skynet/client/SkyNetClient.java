package com.jumia.skylens.http.out.skynet.client;

import com.jumia.skylens.http.out.skynet.responses.ServiceProviderResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

import java.util.UUID;

public interface SkyNetClient {

    @GetExchange("/api/v1/service-providers/sid/{serviceProviderSid}")
    ServiceProviderResponse getServiceProviders(@PathVariable UUID serviceProviderSid);
}

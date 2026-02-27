package com.jumia.skylens.ports.http.out.api.countries;

import com.jumia.skylens.domain.catalog.ServiceProvider;

import java.util.UUID;

public interface ServiceProviderFetcher {

    ServiceProvider get(UUID serviceProviderSid);
}

package com.jumia.skylens.acceptance.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;

@Configuration
public class HttpTemplateConfiguration {

    @Value("${API_HOST:localhost}")
    private String host;

    @Value("${API_PORT:8080}")
    private int port;

    @Bean
    public RestClient restClient() {

        return RestClient.builder()
                .uriBuilderFactory(new DefaultUriBuilderFactory(UriComponentsBuilder.newInstance()
                                                                        .scheme("http")
                                                                        .host(host)
                                                                        .port(port)))
                .build();
    }
}

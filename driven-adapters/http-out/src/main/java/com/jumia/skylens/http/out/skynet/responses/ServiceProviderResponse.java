package com.jumia.skylens.http.out.skynet.responses;

public record ServiceProviderResponse(Network network) {

    public record Network(String code) {

    }
}

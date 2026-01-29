package com.jumia.skylens.acceptance.providers;

import lombok.Builder;

import java.util.Map;
import java.util.UUID;

@Builder
//@SuppressFBWarnings(value = {"EI_EXPOSE_REP"}, justification = "I prefer to suppress these FindBugs warnings")
public record LogisticEventDTO(long id,
                               String trackingNumber,
                               long currentSubRequestId,
                               BusinessPartner currentBusinessPartner,
                               Map<Long, SubRequest> subRequests,
                               String size) {

    public SubRequest currentSubRequest() {

        return subRequests.get(currentSubRequestId);
    }

    @lombok.Builder
    public record SubRequest(String status, ServiceCode service, Map<Long, Attempt> attempts, Invoice invoice, Address address) {

    }

    public record ServiceCode(String code) {

    }

    @lombok.Builder
    public record Attempt(String status, boolean successful, Driver driver) {

    }

    @lombok.Builder
    public record Driver(String sid, String name, ServiceProvider serviceProvider) {

    }

    public record Invoice(PaymentMethod paymentMethod) {

    }

    @lombok.Builder
    public record Address(long id, String email, String type, String region, String city) {

    }

    public record PaymentMethod(UUID sid, String name, String type) {

    }

    @lombok.Builder
    public record BusinessPartner(UUID sid, String name, ServiceProvider serviceProvider, Network network, String region, String city) {

    }

    @lombok.Builder
    public record ServiceProvider(UUID sid, String type) {

    }

    public record Network(String code) {

    }
}

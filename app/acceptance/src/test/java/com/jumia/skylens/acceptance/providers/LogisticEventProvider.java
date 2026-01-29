package com.jumia.skylens.acceptance.providers;

import net.datafaker.Faker;

import java.util.Map;
import java.util.UUID;

public class LogisticEventProvider {

    private static final Faker FAKER = new Faker();

    public static LogisticEventDTO firstMileEvent(UUID partnerSid) {

        return logisticEventDTO("FCP", partnerSid).build();
    }

    public static LogisticEventDTO lastMileEvent(UUID partnerSid) {

        return logisticEventDTO("FDD", partnerSid).build();
    }

    public static LogisticEventDTO.Builder logisticEventDTO(String serviceCode, UUID serviceProviderSid) {

        final long currentSubRequestId = FAKER.number().positive();
        final LogisticEventDTO.BusinessPartner businessPartner = businessPartner(serviceProviderSid).build();
        final LogisticEventDTO.SubRequest subRequest = subRequest(serviceCode).build();

        return LogisticEventDTO.builder()
                .id(FAKER.number().positive())
                .trackingNumber(FAKER.lorem().word())
                .currentSubRequestId(currentSubRequestId)
                .currentBusinessPartner(businessPartner)
                .subRequests(Map.of(currentSubRequestId, subRequest))
                .size("S");
    }

    public static LogisticEventDTO.SubRequest.Builder subRequest(String serviceCode) {

        final LogisticEventDTO.Invoice invoice = invoice();
        final LogisticEventDTO.Attempt attempt = attempt().build();
        final LogisticEventDTO.Address address = logisticEventAddress().build();
        final Map<Long, LogisticEventDTO.Attempt> attempts = Map.of((long) FAKER.number().positive(), attempt);

        return LogisticEventDTO.SubRequest.builder()
                .status("COMPLETE")
                .service(serviceCode(serviceCode))
                .invoice(invoice)
                .attempts(attempts)
                .address(address);
    }

    public static LogisticEventDTO.Address.Builder logisticEventAddress() {

        return LogisticEventDTO.Address.builder()
                .id(FAKER.number().positive())
                .email(FAKER.internet().emailAddress())
                .type(FAKER.lorem().word())
                .region(FAKER.address().state())
                .city(FAKER.address().city());
    }

    public static LogisticEventDTO.Attempt.Builder attempt() {

        final LogisticEventDTO.Driver driver = driver().build();

        return LogisticEventDTO.Attempt.builder()
                .status("CONFIRMED")
                .successful(true)
                .driver(driver);
    }

    public static LogisticEventDTO.Driver.Builder driver() {

        return LogisticEventDTO.Driver.builder()
                .sid(FAKER.lorem().characters())
                .name(FAKER.lordOfTheRings().character());
    }

    public static LogisticEventDTO.ServiceCode serviceCode(String code) {

        return new LogisticEventDTO.ServiceCode(code);
    }

    public static LogisticEventDTO.Invoice invoice() {

        final LogisticEventDTO.PaymentMethod paymentMethod = paymentMethod();

        return new LogisticEventDTO.Invoice(paymentMethod);
    }

    public static LogisticEventDTO.PaymentMethod paymentMethod() {

        return new LogisticEventDTO.PaymentMethod(UUID.randomUUID(),
                                                  FAKER.lorem().characters(100),
                                                  "PSPT");
    }

    public static LogisticEventDTO.BusinessPartner.Builder businessPartner(UUID serviceProviderSid) {

        final LogisticEventDTO.ServiceProvider serviceProvider = serviceProvider(serviceProviderSid).build();

        return LogisticEventDTO.BusinessPartner.builder()
                .sid(UUID.randomUUID())
                .name(FAKER.lorem().word())
                .serviceProvider(serviceProvider);
    }

    public static LogisticEventDTO.ServiceProvider.Builder serviceProvider(UUID serviceProviderSid) {

        return LogisticEventDTO.ServiceProvider.builder()
                .sid(serviceProviderSid)
                .type("THIRD_PARTY");
    }
}

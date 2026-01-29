package com.jumia.skylens.domain.catalog;

import com.jumia.skylens.domain.catalog.enums.PaymentMethodType;
import com.jumia.skylens.domain.catalog.enums.ServiceCode;
import com.jumia.skylens.domain.catalog.enums.Size;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
public class Package {

    private long packageId;

    private String trackingNumber;

    private long stopId;

    private String network;

    private UUID partnerSid;

    private UUID nodeSid;

    private String nodeName;

    private String driverSid;

    private String driverName;

    private UUID paymentMethodSid;

    private String paymentMethodName;

    private PaymentMethodType paymentMethodType;

    private ServiceCode serviceCode;

    private Size size;

    private Address address;

    private NodeAddress nodeAddress;

    private Instant eventDate;

    @lombok.Builder
    public record Address(long id, String email, String type, String region, String city) {

    }

    @lombok.Builder
    public record NodeAddress(String region, String city) {

    }
}

package com.jumia.skylens.domain.catalog;

import com.jumia.skylens.domain.catalog.enums.LastMileDeliveredBy;
import com.jumia.skylens.domain.catalog.enums.PaymentMethodType;
import com.jumia.skylens.domain.catalog.enums.ServiceCode;
import com.jumia.skylens.domain.catalog.enums.Size;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
public class Stop {

    private Long id;

    private String stopId;

    private String stopHash;

    private String network;

    private UUID partnerSid;

    private UUID nodeSid;

    private String nodeName;

    private ServiceCode serviceCode;

    private PaymentMethodType paymentMethodType;

    private UUID zoneSid;

    private String zoneName;

    private Size size;

    private Type type;

    private LastMileDeliveredBy lastMileDeliveredBy;

    private LocalDate eventDate;

    private boolean published;

    public enum Type {
        FIRST_MILE,
        LAST_MILE
    }
}

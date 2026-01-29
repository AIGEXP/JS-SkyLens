package com.jumia.skylens.domain.catalog.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ServiceCode {
    // First Mile Services
    FCP(ServiceType.FIRST_MILE, Flow.FORWARD, Method.PICKUP),
    FCD(ServiceType.FIRST_MILE, Flow.FORWARD, Method.DROPOFF),
    RDD(ServiceType.FIRST_MILE, Flow.REVERSE, Method.HOME_DELIVERY),
    RDP(ServiceType.FIRST_MILE, Flow.REVERSE, Method.PICKUP),
    // Last Mile Services
    FRD(ServiceType.LAST_MILE, Flow.FORWARD, Method.HOME_DELIVERY),
    FDD(ServiceType.LAST_MILE, Flow.FORWARD, Method.HOME_DELIVERY),
    FDP(ServiceType.LAST_MILE, Flow.FORWARD, Method.PICKUP),
    RCP(ServiceType.LAST_MILE, Flow.REVERSE, Method.PICKUP),
    RCD(ServiceType.LAST_MILE, Flow.REVERSE, Method.DROPOFF),
    FRP(ServiceType.LAST_MILE, Flow.FORWARD, Method.PICKUP),
    FVD(ServiceType.LAST_MILE, Flow.FORWARD, Method.HOME_DELIVERY);

    private static final int SERVICE_CODE_LENGTH = 3;

    private final ServiceType serviceType;

    private final Flow flow;

    private final Method method;

    public static boolean isLastMileCode(final String code) {

        try {
            return fromCode(code).isLastMile();
        } catch (IllegalArgumentException _) {
            return false;
        }
    }

    public static boolean isFirstMileCode(final String code) {

        try {
            return fromCode(code).isFirstMile();
        } catch (IllegalArgumentException _) {
            return false;
        }
    }

    public static ServiceCode fromCode(final String code) {

        return ServiceCode.valueOf(trim(code));
    }

    private static String trim(final String code) {

        if (code == null || code.length() < SERVICE_CODE_LENGTH) {
            throw new IllegalArgumentException("Invalid service code: " + code);
        }

        return code.substring(0, SERVICE_CODE_LENGTH);
    }

    private boolean isFirstMile() {

        return serviceType == ServiceType.FIRST_MILE;
    }

    private boolean isLastMile() {

        return serviceType == ServiceType.LAST_MILE;
    }

    public boolean isPickup() {

        return method == Method.PICKUP;
    }

    private enum ServiceType {
        FIRST_MILE,
        LAST_MILE
    }

    public enum Flow {
        FORWARD,
        REVERSE
    }

    public enum Method {
        HOME_DELIVERY,
        DROPOFF,
        PICKUP
    }
}

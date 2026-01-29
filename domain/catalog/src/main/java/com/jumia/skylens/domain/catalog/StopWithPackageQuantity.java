package com.jumia.skylens.domain.catalog;

import com.jumia.skylens.domain.catalog.enums.LastMileDeliveredBy;
import com.jumia.skylens.domain.catalog.enums.PaymentMethodType;
import com.jumia.skylens.domain.catalog.enums.ServiceCode;
import com.jumia.skylens.domain.catalog.enums.Size;
import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public record StopWithPackageQuantity(long id,
                                      UUID partnerSid,
                                      UUID nodeSid,
                                      String network,
                                      String stopId,
                                      String zoneName,
                                      ServiceCode serviceCode,
                                      PaymentMethodType paymentMethodType,
                                      LastMileDeliveredBy lastMileDeliveredBy,
                                      Size size,
                                      long quantity,
                                      LocalDate eventDate) {

}

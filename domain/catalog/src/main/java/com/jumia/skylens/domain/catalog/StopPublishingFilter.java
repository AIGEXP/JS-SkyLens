package com.jumia.skylens.domain.catalog;

import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public record StopPublishingFilter(UUID partnerSid,
                                   String network,
                                   LocalDate date,
                                   LocalDate dateFrom,
                                   LocalDate dateTo,
                                   Boolean published) {

}

package com.jumia.skylens.domain.catalog;

import lombok.Builder;

@Builder
public record PackageNoAttemptsStatistics(int oneDay,
                                          int twoDays,
                                          int threeDays,
                                          int overThreeDays) {

}

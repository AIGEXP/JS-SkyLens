package com.jumia.skylens.domain.impl.validators;

import com.jumia.skylens.domain.catalog.Boundary;
import com.jumia.skylens.domain.catalog.exceptions.InvalidBoundaryException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BoundaryValidator {

    public static void validate(Boundary boundary) {

        if (StringUtils.isBlank(boundary.country())) {
            throw InvalidBoundaryException.withMessage("Country must not be null or blank");
        }

        if (boundary.reportType() == null) {
            throw InvalidBoundaryException.withMessage("Report type must not be null");
        }

        validateThreshold(boundary.warning(), "Warning");
        validateThreshold(boundary.critical(), "Critical");

        if (boundary.warning().compareTo(boundary.critical()) <= 0) {
            throw InvalidBoundaryException.withMessage("Warning must be strictly greater than critical");
        }
    }

    private static void validateThreshold(BigDecimal value, String fieldName) {

        if (value == null) {
            throw InvalidBoundaryException.withMessage(fieldName + " must not be null");
        }

        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw InvalidBoundaryException.withMessage(fieldName + " must be between 0 and 1 inclusive");
        }

        if (value.compareTo(BigDecimal.ONE) > 0) {
            throw InvalidBoundaryException.withMessage(fieldName + " must be between 0 and 1 inclusive");
        }
    }
}

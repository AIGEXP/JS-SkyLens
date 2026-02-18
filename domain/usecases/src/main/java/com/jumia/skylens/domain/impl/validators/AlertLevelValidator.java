package com.jumia.skylens.domain.impl.validators;

import com.jumia.skylens.domain.catalog.AlertLevel;
import com.jumia.skylens.domain.catalog.exceptions.InvalidAlertLevelException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AlertLevelValidator {

    public static void validate(AlertLevel alertLevel) {

        if (StringUtils.isBlank(alertLevel.country())) {
            throw InvalidAlertLevelException.withMessage("Country must not be null or blank");
        }

        if (alertLevel.reportType() == null) {
            throw InvalidAlertLevelException.withMessage("Report type must not be null");
        }

        validateThreshold(alertLevel.warningValue(), "Warning");
        validateThreshold(alertLevel.criticalValue(), "Critical");

        if (alertLevel.warningValue().compareTo(alertLevel.criticalValue()) <= 0) {
            throw InvalidAlertLevelException.withMessage("Warning must be strictly greater than criticalValue");
        }
    }

    private static void validateThreshold(BigDecimal value, String fieldName) {

        if (value == null) {
            throw InvalidAlertLevelException.withMessage(fieldName + " must not be null");
        }

        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw InvalidAlertLevelException.withMessage(fieldName + " must be between 0 and 1 inclusive");
        }

        if (value.compareTo(BigDecimal.ONE) > 0) {
            throw InvalidAlertLevelException.withMessage(fieldName + " must be between 0 and 1 inclusive");
        }
    }
}

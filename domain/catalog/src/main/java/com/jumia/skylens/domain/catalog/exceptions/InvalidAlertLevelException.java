package com.jumia.skylens.domain.catalog.exceptions;

import com.jumia.skylens.domain.catalog.exceptions.errors.ErrorCode;

public final class InvalidAlertLevelException extends InvalidInputException {

    private InvalidAlertLevelException(String message) {

        super(ErrorCode.INVALID_ALERT_LEVEL, message);
    }

    public static InvalidAlertLevelException withMessage(String message) {

        return new InvalidAlertLevelException(message);
    }
}

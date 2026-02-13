package com.jumia.skylens.domain.catalog.exceptions;

import com.jumia.skylens.domain.catalog.exceptions.errors.ErrorCode;

public final class InvalidBoundaryException extends InvalidInputException {

    private InvalidBoundaryException(String message) {

        super(ErrorCode.INVALID_BOUNDARY, message);
    }

    public static InvalidBoundaryException withMessage(String message) {

        return new InvalidBoundaryException(message);
    }
}

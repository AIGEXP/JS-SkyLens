package com.jumia.skylens.http.in.acl.exceptions;

import com.jumia.skylens.domain.catalog.exceptions.InvalidInputException;
import com.jumia.skylens.http.in.acl.exceptions.errors.ErrorCode;

public final class InvalidBasicAuthTokenException extends InvalidInputException {

    private InvalidBasicAuthTokenException(String message) {

        super(ErrorCode.INVALID_BASIC_TOKEN, message);
    }

    public static InvalidBasicAuthTokenException invalidBasicToken() {

        return new InvalidBasicAuthTokenException("Invalid basic token");
    }
}

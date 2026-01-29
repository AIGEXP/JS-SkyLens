package com.jumia.skylens.http.in.acl.exceptions;

import com.jumia.skylens.http.in.acl.exceptions.errors.ErrorCode;

public final class MissingAuthenticationTokenException extends UnauthorizedException {

    private MissingAuthenticationTokenException(String message) {

        super(ErrorCode.MISSING_AUTHORIZATION_TOKEN, message);
    }

    public static MissingAuthenticationTokenException missingToken() {

        return new MissingAuthenticationTokenException("Missing authorization token");
    }
}

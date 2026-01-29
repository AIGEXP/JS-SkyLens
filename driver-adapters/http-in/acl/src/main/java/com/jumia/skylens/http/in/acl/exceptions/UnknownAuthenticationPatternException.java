package com.jumia.skylens.http.in.acl.exceptions;

import com.jumia.skylens.commons.exceptions.CodedException;
import com.jumia.skylens.http.in.acl.exceptions.errors.ErrorCode;

import java.io.Serial;

public final class UnknownAuthenticationPatternException extends CodedException {

    @Serial
    private static final long serialVersionUID = 4019931650309078617L;

    private UnknownAuthenticationPatternException(String message) {

        super(ErrorCode.UNKNOWN_TOKEN_PATTERN, message);
    }

    public static UnknownAuthenticationPatternException invalidPattern() {

        return new UnknownAuthenticationPatternException("Pattern not recognized for token");
    }
}

package com.jumia.skylens.http.in.acl.exceptions;

import com.jumia.skylens.commons.exceptions.CodedException;
import com.jumia.skylens.commons.exceptions.ErrorCoded;
import com.jumia.skylens.http.in.acl.exceptions.errors.ErrorCode;

import java.io.Serial;

public class UnauthorizedException extends CodedException {

    @Serial
    private static final long serialVersionUID = 7433551117221333654L;

    protected UnauthorizedException(ErrorCoded errorCoded) {

        super(errorCoded);
    }

    protected UnauthorizedException(ErrorCoded errorCode, String message) {

        super(errorCode, message);
    }

    public UnauthorizedException(Throwable throwable) {

        super(ErrorCode.UNAUTHORIZED, throwable);
    }

    public UnauthorizedException() {

        super(ErrorCode.UNAUTHORIZED);
    }
}

package com.jumia.skylens.http.in.acl.exceptions;

import com.jumia.skylens.commons.exceptions.CodedException;
import com.jumia.skylens.http.in.acl.exceptions.errors.ErrorCode;

public final class ForbiddenException extends CodedException {

    public ForbiddenException() {

        super(ErrorCode.FORBIDDEN);
    }
}

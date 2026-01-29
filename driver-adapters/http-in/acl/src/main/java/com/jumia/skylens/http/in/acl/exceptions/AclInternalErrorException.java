package com.jumia.skylens.http.in.acl.exceptions;

import com.jumia.skylens.domain.catalog.exceptions.InternalErrorException;
import com.jumia.skylens.http.in.acl.exceptions.errors.ErrorCode;

public final class AclInternalErrorException extends InternalErrorException {

    private AclInternalErrorException(String message, Throwable cause) {

        super(ErrorCode.ACL_ERROR, message, cause);
    }

    public static AclInternalErrorException with(Throwable cause) {

        return new AclInternalErrorException("ACL internal error", cause);
    }
}

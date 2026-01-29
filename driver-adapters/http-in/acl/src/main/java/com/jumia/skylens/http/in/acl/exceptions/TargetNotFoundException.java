package com.jumia.skylens.http.in.acl.exceptions;

import com.jumia.skylens.domain.catalog.exceptions.NotFoundException;
import com.jumia.skylens.http.in.acl.exceptions.errors.ErrorCode;

public final class TargetNotFoundException extends NotFoundException {

    private TargetNotFoundException(String message, Throwable throwable) {

        super(ErrorCode.TARGET_NOT_FOUND, message, throwable);
    }

    public static TargetNotFoundException forCodeAndException(String targetCode, Exception exception) {

        return new TargetNotFoundException(String.format("Could not find target code [%s] in ACL", targetCode), exception);
    }
}

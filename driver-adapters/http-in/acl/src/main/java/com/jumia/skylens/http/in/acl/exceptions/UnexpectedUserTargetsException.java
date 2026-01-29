package com.jumia.skylens.http.in.acl.exceptions;

import com.jumia.skylens.domain.catalog.exceptions.InternalErrorException;
import com.jumia.skylens.http.in.acl.exceptions.errors.ErrorCode;

import java.util.Map;

public final class UnexpectedUserTargetsException extends InternalErrorException {

    private UnexpectedUserTargetsException(Throwable throwable, String message) {

        super(ErrorCode.UNEXPECTED_USER_TARGETS, message, throwable);
    }

    public static UnexpectedUserTargetsException forTargets(Throwable throwable,
                                                            Map<String, Object> userTargets) {

        return new UnexpectedUserTargetsException(throwable, String.format("Unexpected user targets: [%s]", userTargets));
    }
}

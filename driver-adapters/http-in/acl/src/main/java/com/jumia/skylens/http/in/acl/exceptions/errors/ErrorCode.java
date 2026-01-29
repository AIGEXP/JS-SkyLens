package com.jumia.skylens.http.in.acl.exceptions.errors;

import com.jumia.skylens.commons.exceptions.ErrorCoded;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ErrorCode implements ErrorCoded {
    UNKNOWN_TOKEN_PATTERN(1),
    INVALID_BASIC_TOKEN(2),
    MISSING_AUTHORIZATION_TOKEN(3),
    ACL_ERROR(4),
    TARGET_NOT_FOUND(5),
    UNEXPECTED_USER_TARGETS(6),
    UNAUTHORIZED(7),
    FORBIDDEN(8);

    private final int code;

    @Override
    public Integer getCode() {

        return ErrorCoded.BaseCodes.ACL + code;
    }

    @Override
    public String getName() {

        return name();
    }
}

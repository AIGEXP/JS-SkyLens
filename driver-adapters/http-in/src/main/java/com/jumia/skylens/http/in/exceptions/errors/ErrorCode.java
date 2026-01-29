package com.jumia.skylens.http.in.exceptions.errors;

import com.jumia.skylens.commons.exceptions.ErrorCoded;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ErrorCode implements ErrorCoded {
    INVALID_PARAMS(1),
    UNEXPECTED_ERROR(2),
    INVALID_RESOURCE(3),
    INVALID_SORT_ERROR(4),
    INVALID_ENCODING_ERROR(5),
    CONCURRENT_UPDATE_DETECTED(6),
    INVALID_INPUT_TYPE(7),
    TENANT_HEADER_NOT_FOUND(8),
    ERROR_WHILE_RUNNING_FILTER(9);

    private final Integer code;

    @Override
    public Integer getCode() {

        return ErrorCoded.BaseCodes.REST + this.code;
    }

    @Override
    public String getName() {

        return this.name();
    }
}

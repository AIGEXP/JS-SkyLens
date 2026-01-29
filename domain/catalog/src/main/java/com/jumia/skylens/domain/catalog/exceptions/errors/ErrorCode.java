package com.jumia.skylens.domain.catalog.exceptions.errors;

import com.jumia.skylens.commons.exceptions.ErrorCoded;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ErrorCode implements ErrorCoded {
    NETWORK_NOT_FOUND(0);

    private final int code;

    @Override
    public Integer getCode() {

        return ErrorCoded.BaseCodes.DOMAIN + code;
    }

    @Override
    public String getName() {

        return this.name();
    }
}

package com.jumia.skylens.domain.catalog.exceptions;

import com.jumia.skylens.commons.exceptions.CodedException;
import com.jumia.skylens.commons.exceptions.ErrorCoded;

public abstract class InternalErrorException extends CodedException {

    protected InternalErrorException(ErrorCoded errorCode, String message, Throwable throwable) {

        super(errorCode, message, throwable);
    }
}

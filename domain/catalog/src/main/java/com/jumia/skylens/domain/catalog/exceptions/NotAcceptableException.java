package com.jumia.skylens.domain.catalog.exceptions;

import com.jumia.skylens.commons.exceptions.CodedException;
import com.jumia.skylens.commons.exceptions.ErrorCoded;

public class NotAcceptableException extends CodedException {

    public NotAcceptableException(ErrorCoded errorCode, String message, Throwable throwable) {

        super(errorCode, message, throwable);
    }

    public NotAcceptableException(ErrorCoded errorCode, String message) {

        super(errorCode, message);
    }
}

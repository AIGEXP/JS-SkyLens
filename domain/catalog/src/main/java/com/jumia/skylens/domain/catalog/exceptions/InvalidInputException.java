package com.jumia.skylens.domain.catalog.exceptions;

import com.jumia.skylens.commons.exceptions.CodedException;
import com.jumia.skylens.commons.exceptions.ErrorCoded;

public abstract class InvalidInputException extends CodedException {

    protected InvalidInputException(ErrorCoded errorCode, String message) {

        super(errorCode, message);
    }
}

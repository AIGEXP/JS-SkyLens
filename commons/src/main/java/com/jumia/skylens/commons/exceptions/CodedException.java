package com.jumia.skylens.commons.exceptions;

import lombok.Getter;

import java.io.Serial;

@Getter
public abstract class CodedException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 4225880155414423596L;

    private final ErrorCoded errorCode;

    private final String message;

    public CodedException(ErrorCoded errorCode) {

        this(errorCode, null, null);
    }

    public CodedException(ErrorCoded errorCode, Throwable throwable) {

        this(errorCode, throwable.getMessage(), throwable);
    }

    public CodedException(ErrorCoded errorCode, String message, Throwable throwable) {

        super(throwable);
        this.errorCode = errorCode;
        this.message = message;
    }

    public CodedException(ErrorCoded errorCode, String message) {

        this.errorCode = errorCode;
        this.message = message;
    }

    public Object getExtraInfo() {

        return null;
    }

    @Override
    public String getMessage() {

        if (this.message != null) {
            return String.format("%s - %s", this.errorCode, this.message);
        } else {
            return String.format("%s", this.errorCode);
        }
    }
}

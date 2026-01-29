package com.jumia.skylens.persistence.api.exceptions;

public class StopAlreadyExistsException extends RuntimeException {

    public StopAlreadyExistsException(String stopHash, Throwable cause) {

        super("Stop with hash " + stopHash + " already exists", cause);
    }
}

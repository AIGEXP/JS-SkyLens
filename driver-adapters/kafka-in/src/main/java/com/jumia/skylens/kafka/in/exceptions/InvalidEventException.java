package com.jumia.skylens.kafka.in.exceptions;

import java.io.Serial;

public class InvalidEventException extends DisposableEventException {

    @Serial
    private static final long serialVersionUID = 4301092180522899977L;

    public InvalidEventException(final String message, final Throwable cause) {

        super(message, cause);
    }
}

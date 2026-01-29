package com.jumia.skylens.kafka.in.exceptions;

import lombok.NoArgsConstructor;

import java.io.Serial;

@NoArgsConstructor
public abstract class DisposableEventException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 6493649299687531552L;

    protected DisposableEventException(final String message) {

        super(message);
    }

    protected DisposableEventException(final String message, final Throwable cause) {

        super(message, cause);
    }
}

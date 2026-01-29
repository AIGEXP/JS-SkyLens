package com.jumia.skylens.kafka.in.exceptions;

import java.io.Serial;

public class MissingTenantException extends DisposableEventException {

    @Serial
    private static final long serialVersionUID = 7332240091153760949L;

    public MissingTenantException(String message) {

        super(message);
    }
}

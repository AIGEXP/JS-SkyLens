package com.jumia.skylens.commons.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public final class NotImplementedException extends CodedException {

    public NotImplementedException(String message) {

        super(Error.NOT_IMPLEMENTED, message);
    }

    @RequiredArgsConstructor
    @Getter
    private enum Error implements ErrorCoded {
        NOT_IMPLEMENTED(0);

        private final Integer code;

        @Override
        public String getName() {

            return this.name();
        }
    }
}

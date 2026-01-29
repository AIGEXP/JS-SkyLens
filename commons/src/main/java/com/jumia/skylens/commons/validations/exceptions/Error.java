package com.jumia.skylens.commons.validations.exceptions;

import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@ToString
public class Error {

    private final String field;

    private Serializable rejected;

    private final String message;

    public Error(String field, String message, Object rejected) {

        this.field = field;
        this.message = message;
        setRejected(rejected);
    }

    public void setRejected(Object rejected) {

        if (rejected instanceof Serializable) {
            this.rejected = (Serializable) rejected;
        } else if (rejected != null) {
            this.rejected = rejected.toString();
        }
    }
}

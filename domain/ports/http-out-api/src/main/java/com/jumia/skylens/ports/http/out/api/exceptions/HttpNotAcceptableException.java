package com.jumia.skylens.ports.http.out.api.exceptions;

public class HttpNotAcceptableException extends HttpException {

    public static final int NOT_ACCEPTABLE = 406;

    public HttpNotAcceptableException() {

        super(NOT_ACCEPTABLE, ErrorCode.NOT_ACCEPTABLE);
    }
}

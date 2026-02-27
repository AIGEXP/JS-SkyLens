package com.jumia.skylens.ports.http.out.api.exceptions;

public class HttpNotFoundException extends HttpException {

    public static final int NOT_FOUND = 404;

    public HttpNotFoundException() {

        super(NOT_FOUND, ErrorCode.NOT_FOUND);
    }
}

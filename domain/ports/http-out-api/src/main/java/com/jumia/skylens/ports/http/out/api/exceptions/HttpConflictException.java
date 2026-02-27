package com.jumia.skylens.ports.http.out.api.exceptions;

public class HttpConflictException extends HttpException {

    public static final int CONFLICT = 409;

    public HttpConflictException() {

        super(CONFLICT, ErrorCode.CONFLICT);
    }
}

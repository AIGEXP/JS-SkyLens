package com.jumia.skylens.ports.http.out.api.exceptions;

public class HttpForbiddenException extends HttpException {

    public static final int FORBIDDEN = 403;

    public HttpForbiddenException() {

        super(FORBIDDEN, ErrorCode.FORBIDDEN);
    }
}

package com.jumia.skylens.ports.http.out.api.exceptions;

public class HttpUnauthorizedException extends HttpException {

    public static final int UNAUTHORIZED = 401;

    public HttpUnauthorizedException() {

        super(UNAUTHORIZED, ErrorCode.UNAUTHORIZED);
    }
}

package com.jumia.skylens.ports.http.out.api.exceptions;

public class HttpBadRequestException extends HttpException {

    public static final int BAD_REQUEST = 400;

    public HttpBadRequestException() {

        super(BAD_REQUEST, ErrorCode.BAD_REQUEST);
    }
}

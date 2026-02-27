package com.jumia.skylens.ports.http.out.api.exceptions;

public class HttpInternalServerErrorException extends HttpException {

    public static final int INTERNAL_ERROR = 500;

    public HttpInternalServerErrorException() {

        super(INTERNAL_ERROR, ErrorCode.INTERNAL_SERVER_ERROR);
    }
}

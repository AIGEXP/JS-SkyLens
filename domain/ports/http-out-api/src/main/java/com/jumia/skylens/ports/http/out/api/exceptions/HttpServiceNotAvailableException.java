package com.jumia.skylens.ports.http.out.api.exceptions;

public class HttpServiceNotAvailableException extends HttpException {

    public static final int SERVICE_NOT_AVAILABLE = 503;

    public HttpServiceNotAvailableException() {

        super(SERVICE_NOT_AVAILABLE, ErrorCode.SERVICE_NOT_AVAILABLE);
    }

    public HttpServiceNotAvailableException(String message) {

        super(SERVICE_NOT_AVAILABLE, ErrorCode.SERVICE_NOT_AVAILABLE, message);
    }
}

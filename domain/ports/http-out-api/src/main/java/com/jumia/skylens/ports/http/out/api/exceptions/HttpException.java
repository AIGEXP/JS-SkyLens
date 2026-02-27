package com.jumia.skylens.ports.http.out.api.exceptions;

import com.jumia.skylens.commons.exceptions.CodedException;
import com.jumia.skylens.commons.exceptions.ErrorCoded;
import lombok.Getter;

@Getter
public class HttpException extends CodedException {

    private final Integer httpCode;

    public HttpException(int httpCode, ErrorCoded errorCoded) {

        super(errorCoded);
        this.httpCode = httpCode;
    }

    public HttpException(int httpCode, ErrorCoded errorCoded, String message) {

        super(errorCoded, message);
        this.httpCode = httpCode;
    }
}

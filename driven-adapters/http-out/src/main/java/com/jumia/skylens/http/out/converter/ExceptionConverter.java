package com.jumia.skylens.http.out.converter;

import com.jumia.skylens.commons.converters.Converter;
import com.jumia.skylens.ports.http.out.api.exceptions.ErrorCode;
import com.jumia.skylens.ports.http.out.api.exceptions.HttpBadRequestException;
import com.jumia.skylens.ports.http.out.api.exceptions.HttpConflictException;
import com.jumia.skylens.ports.http.out.api.exceptions.HttpException;
import com.jumia.skylens.ports.http.out.api.exceptions.HttpForbiddenException;
import com.jumia.skylens.ports.http.out.api.exceptions.HttpInternalServerErrorException;
import com.jumia.skylens.ports.http.out.api.exceptions.HttpNotAcceptableException;
import com.jumia.skylens.ports.http.out.api.exceptions.HttpNotFoundException;
import com.jumia.skylens.ports.http.out.api.exceptions.HttpServiceNotAvailableException;
import com.jumia.skylens.ports.http.out.api.exceptions.HttpUnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public class ExceptionConverter implements Converter<HttpStatusCode, HttpException> {

    @Override
    public HttpException convert(HttpStatusCode source) {

        return switch (source) {
            case HttpStatus.BAD_REQUEST -> new HttpBadRequestException();
            case HttpStatus.UNAUTHORIZED -> new HttpUnauthorizedException();
            case HttpStatus.FORBIDDEN -> new HttpForbiddenException();
            case HttpStatus.NOT_FOUND -> new HttpNotFoundException();
            case HttpStatus.INTERNAL_SERVER_ERROR -> new HttpInternalServerErrorException();
            case HttpStatus.SERVICE_UNAVAILABLE -> new HttpServiceNotAvailableException("Service not available, please try again later.");
            case HttpStatus.NOT_ACCEPTABLE -> new HttpNotAcceptableException();
            case HttpStatus.CONFLICT -> new HttpConflictException();
            default -> new HttpException(source.value(), ErrorCode.UNEXPECTED_ERROR);
        };
    }
}

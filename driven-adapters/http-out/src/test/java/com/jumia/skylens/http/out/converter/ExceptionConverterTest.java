package com.jumia.skylens.http.out.converter;

import com.jumia.skylens.ports.http.out.api.exceptions.HttpBadRequestException;
import com.jumia.skylens.ports.http.out.api.exceptions.HttpConflictException;
import com.jumia.skylens.ports.http.out.api.exceptions.HttpException;
import com.jumia.skylens.ports.http.out.api.exceptions.HttpForbiddenException;
import com.jumia.skylens.ports.http.out.api.exceptions.HttpInternalServerErrorException;
import com.jumia.skylens.ports.http.out.api.exceptions.HttpNotAcceptableException;
import com.jumia.skylens.ports.http.out.api.exceptions.HttpNotFoundException;
import com.jumia.skylens.ports.http.out.api.exceptions.HttpServiceNotAvailableException;
import com.jumia.skylens.ports.http.out.api.exceptions.HttpUnauthorizedException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

class ExceptionConverterTest {

    private final ExceptionConverter subject = new ExceptionConverter();

    @Test
    void convert_when400_returnsBadRequestException() {

        //given
        final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        //Then
        final HttpException httpException = subject.convert(httpStatus);

        //Then
        assertThat(httpException).isExactlyInstanceOf(HttpBadRequestException.class);
    }

    @Test
    void convert_when401_returnsUnauthorizedException() {

        //given
        final HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;

        //Then
        final HttpException httpException = subject.convert(httpStatus);

        //Then
        assertThat(httpException).isExactlyInstanceOf(HttpUnauthorizedException.class);
    }

    @Test
    void convert_when403_returnsForbiddenException() {

        //given
        final HttpStatus httpStatus = HttpStatus.FORBIDDEN;

        //Then
        final HttpException httpException = subject.convert(httpStatus);

        //Then
        assertThat(httpException).isExactlyInstanceOf(HttpForbiddenException.class);
    }

    @Test
    void convert_when404_returnsNotFoundException() {

        //given
        final HttpStatus httpStatus = HttpStatus.NOT_FOUND;

        //Then
        final HttpException httpException = subject.convert(httpStatus);

        //Then
        assertThat(httpException).isExactlyInstanceOf(HttpNotFoundException.class);
    }

    @Test
    void convert_when406_returnsServiceNotAcceptableException() {

        // Given
        final HttpStatus httpStatus = HttpStatus.NOT_ACCEPTABLE;

        // When
        final HttpException httpException = subject.convert(httpStatus);

        // Then
        assertThat(httpException).isExactlyInstanceOf(HttpNotAcceptableException.class);
    }

    @Test
    void convert_when409_returnsHttpConflictException() {

        // Given
        final HttpStatus httpStatus = HttpStatus.CONFLICT;

        // When
        final HttpException httpException = subject.convert(httpStatus);

        // Then
        assertThat(httpException).isExactlyInstanceOf(HttpConflictException.class);
    }

    @Test
    void convert_when500_returnsInternalServerErrorException() {

        //given
        final HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        //Then
        final HttpException httpException = subject.convert(httpStatus);

        //Then
        assertThat(httpException).isExactlyInstanceOf(HttpInternalServerErrorException.class);
    }

    @Test
    void convert_when503_returnsServiceNotAvailableException() {

        //given
        final HttpStatus httpStatus = HttpStatus.SERVICE_UNAVAILABLE;

        //Then
        final HttpException httpException = subject.convert(httpStatus);

        //Then
        assertThat(httpException).isExactlyInstanceOf(HttpServiceNotAvailableException.class);
    }

    @Test
    void convert_whenUnhandled_returnsHttpExceptionWithTheReceivedHttpStatus() {

        //given
        final HttpStatus httpStatus = HttpStatus.LOCKED;

        //Then
        final HttpException httpException = subject.convert(httpStatus);

        //Then
        assertThat(httpException)
                .isExactlyInstanceOf(HttpException.class)
                .matches(e -> e.getHttpCode().equals(423));
    }
}

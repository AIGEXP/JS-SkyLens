package com.jumia.skylens.http.in.handlers;

import com.jumia.skylens.commons.exceptions.CodedException;
import com.jumia.skylens.commons.exceptions.ErrorCoded;
import com.jumia.skylens.commons.validations.exceptions.Error;
import com.jumia.skylens.http.in.exceptions.errors.ErrorCode;
import com.jumia.skylens.http.in.responses.errors.ErrorResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Collections;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ErrorResponseFactory {

    public static ErrorResponse create(CodedException exception) {

        return create(exception.getErrorCode(), exception.getMessage(), null, exception.getExtraInfo());
    }

    public static ErrorResponse create(ErrorCoded errorCoded, String message) {

        return create(errorCoded, message, Collections.emptyList(), null);
    }

    public static ErrorResponse create(ErrorCoded errorCoded, String message, Collection<Error> errors, Object extraInfo) {

        return ErrorResponse.builder()
                .code(errorCoded.getCode())
                .codeName(errorCoded.getName())
                .message(message)
                .errors(errors)
                .extraInfo(extraInfo)
                .build();
    }

    public static ErrorResponse createForInvalidParameters() {

        return createForInvalidParameters(Collections.emptyList());
    }

    public static ErrorResponse createForInvalidParameters(Collection<Error> errors) {

        return create(ErrorCode.INVALID_PARAMS, "Invalid params", errors, null);
    }
}

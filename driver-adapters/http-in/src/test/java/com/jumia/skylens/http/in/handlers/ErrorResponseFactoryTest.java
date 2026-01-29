package com.jumia.skylens.http.in.handlers;

import com.jumia.skylens.commons.exceptions.CodedException;
import com.jumia.skylens.commons.exceptions.ErrorCoded;
import com.jumia.skylens.commons.validations.exceptions.Error;
import com.jumia.skylens.http.in.exceptions.errors.ErrorCode;
import com.jumia.skylens.http.in.responses.errors.ErrorResponse;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ErrorResponseFactoryTest {

    @Test
    void create_withErrorCodedAndMessage_returnErrorResponseWithErrors() {

        // Given
        final ErrorCode errorCode = ErrorCode.UNEXPECTED_ERROR;
        final String message = "Message";

        // When
        final ErrorResponse errorResponse = ErrorResponseFactory.create(errorCode, message);

        // Then
        final ErrorResponse expected = ErrorResponse.builder()
                .code(errorCode.getCode())
                .codeName(errorCode.getName())
                .message(message)
                .errors(Set.of())
                .build();
        assertThat(errorResponse)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void create_withErrorsAndExtraInfo_returnErrorResponseWithErrors() {

        // Given
        final ErrorCode errorCode = ErrorCode.UNEXPECTED_ERROR;
        final String message = "Message";
        final Set<Error> errors = Set.of(new Error("field1", "message1", "error1"),
                                         new Error("field2", "message2", "error2"));
        final Object extraInfo = Map.of("extra", "info");

        // When
        final ErrorResponse errorResponse = ErrorResponseFactory.create(errorCode, message, errors, extraInfo);

        // Then
        final ErrorResponse expected = ErrorResponse.builder()
                .code(errorCode.getCode())
                .codeName(errorCode.getName())
                .message(message)
                .errors(errors)
                .extraInfo(extraInfo)
                .build();
        assertThat(errorResponse)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void create_withException_returnErrorResponseWithErrorCodeAndMessage() {

        // Given
        final ErrorCode errorCode = ErrorCode.UNEXPECTED_ERROR;
        final String message = "Message";

        // When
        final ErrorResponse errorResponse = ErrorResponseFactory.create(new TestException(errorCode, message));

        // Then
        final ErrorResponse expected = ErrorResponse.builder()
                .code(errorCode.getCode())
                .codeName(errorCode.getName())
                .message(errorCode.getName() + " - " + message)
                .build();
        assertThat(errorResponse)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void createForInvalidParameters_whenCalledWithErrors_thenReturnErrorResponseWithErrors() {

        // Given
        final Set<Error> errors = Set.of(new Error("field1", "message1", "error1"),
                                         new Error("field2", "message2", "error2"));

        // When
        final ErrorResponse errorResponse = ErrorResponseFactory.createForInvalidParameters(errors);

        // Then
        final ErrorResponse expected = ErrorResponse.builder()
                .code(ErrorCode.INVALID_PARAMS.getCode())
                .codeName(ErrorCode.INVALID_PARAMS.getName())
                .message("Invalid params")
                .errors(errors)
                .build();
        assertThat(errorResponse)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void createForInvalidParameters_whenCalledWithoutArguments_thenReturnErrorResponseWithEmptyErrors() {

        // Given
        // When
        final ErrorResponse errorResponse = ErrorResponseFactory.createForInvalidParameters();

        // Then
        final ErrorResponse expected = ErrorResponse.builder()
                .code(ErrorCode.INVALID_PARAMS.getCode())
                .codeName(ErrorCode.INVALID_PARAMS.getName())
                .message("Invalid params")
                .errors(List.of())
                .build();
        assertThat(errorResponse)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    private static class TestException extends CodedException {

        TestException(ErrorCoded errorCode, String message) {

            super(errorCode, message);
        }
    }
}

package com.jumia.skylens.http.in.acl.utils;

import com.jumia.skylens.http.in.acl.exceptions.UnknownAuthenticationPatternException;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TokenExtractorTest {

    private final TokenExtractor subject = new TokenExtractor();

    @Test
    void extractToken_whenCanExtractToken_returnExtractedToken() {

        // Given
        final String fullToken = "Basic 1234567890";

        // When
        final String result = subject.extractToken(fullToken);

        // Then
        assertThat(result).isEqualTo("1234567890");
    }

    @Test
    void extractToken_whenCannotExtractToken_throwsExceptions() {

        // Given
        final String fullToken = "NotAToken";

        // When
        final ThrowableAssert.ThrowingCallable callable = () -> subject.extractToken(fullToken);

        // Then
        assertThatThrownBy(callable)
                .isInstanceOf(UnknownAuthenticationPatternException.class);
    }
}

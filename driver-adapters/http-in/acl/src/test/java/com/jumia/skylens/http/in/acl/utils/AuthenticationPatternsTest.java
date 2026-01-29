package com.jumia.skylens.http.in.acl.utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AuthenticationPatternsTest {

    private final AuthenticationPatterns subject = new AuthenticationPatterns();

    @Test
    void isBasicAuth_whenIsBasic_returnTrue() {

        // Given
        final String authToken = "Basic 1234";

        // When
        final boolean result = subject.isBasicAuth(authToken);

        // Then
        assertThat(result).isTrue();
    }

    @Test
    void isBearerAuth_whenIsBearer_returnTrue() {

        // Given
        final String authToken = "Bearer 1234";

        // When
        final boolean result = subject.isBearerAuth(authToken);

        // Then
        assertThat(result).isTrue();
    }
}

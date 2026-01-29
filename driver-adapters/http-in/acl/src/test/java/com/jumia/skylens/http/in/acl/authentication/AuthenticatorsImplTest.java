package com.jumia.skylens.http.in.acl.authentication;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AuthenticatorsImplTest {

    @Test
    void get_whenHasOAuthProvider_returnAuthenticator() {

        // Given
        final TestAuthenticator authenticator = new TestAuthenticator(OAuthProvider.GOOGLE);
        final AuthenticatorsImpl subject = new AuthenticatorsImpl(List.of(authenticator));

        // When
        final Authenticator result = subject.get(OAuthProvider.GOOGLE);

        // Then
        assertThat(result)
                .isEqualTo(authenticator);
    }

    private record TestAuthenticator(OAuthProvider provider) implements Authenticator {

        @Override
        public Token authenticate(String authorizationString, UserAuthenticationType userAuthenticationType, String redirectUri) {

            return null;
        }

        @Override
        public OAuthProvider getProvider() {

            return provider;
        }
    }
}

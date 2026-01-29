package com.jumia.skylens.http.in.acl.token.factories;

import com.jumia.skylens.http.in.acl.authentication.AuthToken;
import com.jumia.skylens.http.in.acl.authentication.checkers.BasicAuthorizer;
import com.jumia.skylens.http.in.acl.authentication.checkers.BearerAuthorizer;
import com.jumia.skylens.http.in.acl.exceptions.MissingAuthenticationTokenException;
import com.jumia.skylens.http.in.acl.exceptions.UnknownAuthenticationPatternException;
import com.jumia.skylens.http.in.acl.permissions.checkers.BasicPermissionChecker;
import com.jumia.skylens.http.in.acl.permissions.checkers.BearerPermissionChecker;
import com.jumia.skylens.http.in.acl.tokens.BasicAuthToken;
import com.jumia.skylens.http.in.acl.tokens.BearerAuthToken;
import com.jumia.skylens.http.in.acl.tokens.factories.AuthTokenFactoryImpl;
import com.jumia.skylens.http.in.acl.utils.AuthenticationPatterns;
import net.datafaker.Faker;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class AuthTokenFactoryImplTest {

    private final Faker faker = new Faker();

    @Mock
    private BasicAuthorizer basicAuthChecker;

    @Mock
    private BearerAuthorizer bearerAuthChecker;

    @Mock
    private BasicPermissionChecker basicPermissionChecker;

    @Mock
    private BearerPermissionChecker bearerPermissionChecker;

    @Mock
    private AuthenticationPatterns authenticationPatterns;

    @InjectMocks
    private AuthTokenFactoryImpl subject;

    @Test
    void create_whenTokenIsBasic_returnBasicToken() {

        // Given
        final String token = faker.animal().name();
        doReturn(true)
                .when(authenticationPatterns)
                .isBasicAuth(eq(token));

        doReturn(true)
                .when(basicAuthChecker)
                .isAuthenticated(eq(token));

        // When
        final AuthToken result = subject.create(token);

        // Then
        assertThat(result)
                .isInstanceOf(BasicAuthToken.class);
    }

    @Test
    void create_whenTokenIsBearer_returnBearerToken() {

        // Given
        final String token = faker.animal().name();
        doReturn(true)
                .when(authenticationPatterns)
                .isBearerAuth(eq(token));

        doReturn(true)
                .when(bearerAuthChecker)
                .isAuthenticated(eq(token));

        // When
        final AuthToken result = subject.create(token);

        // Then
        assertThat(result)
                .isInstanceOf(BearerAuthToken.class);
    }

    @Test
    void create_whenTokenIsEmpty_throwsException() {

        // Given
        final String token = "";

        // When
        final ThrowableAssert.ThrowingCallable callable = () -> subject.create(token);

        // Then
        assertThatThrownBy(callable)
                .isInstanceOf(MissingAuthenticationTokenException.class);
    }

    @Test
    void create_whenTokenNotBasicNorBearer_throwsException() {

        // Given
        final String token = faker.animal().name();

        // When
        final ThrowableAssert.ThrowingCallable callable = () -> subject.create(token);

        // Then
        assertThatThrownBy(callable)
                .isInstanceOf(UnknownAuthenticationPatternException.class);
    }
}

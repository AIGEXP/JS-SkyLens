package com.jumia.skylens.http.in.acl.authentication.checkers.impl;

import com.jumia.skylens.http.in.acl.AclFaker;
import com.jumia.skylens.http.in.acl.exceptions.UnauthorizedException;
import com.jumia.skylens.test.fakers.DomainFaker;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pt.jumia.services.acl.lib.AclConnectApiClient;
import pt.jumia.services.acl.lib.AclErrorException;
import pt.jumia.services.acl.lib.client.authentication.AuthenticationClient;
import pt.jumia.services.acl.lib.client.authorization.DefaultAuthorizationClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class BearerAuthorizerImplTest {

    private final AclFaker aclFaker = new AclFaker();

    private final DomainFaker domainFaker = new DomainFaker();

    @Mock
    private AclConnectApiClient<DefaultAuthorizationClient> aclConnectApiClient;

    @InjectMocks
    private BearerAuthorizerImpl subject;

    @Test
    void isAuthenticated_whenAclReturnsResult_returnResult() {

        // Given
        final String token = aclFaker.book().title();
        final boolean isAuthenticated = domainFaker.bool().bool();
        final AuthenticationClient authenticationClient = mock(AuthenticationClient.class);

        doReturn(authenticationClient)
                .when(aclConnectApiClient)
                .authentication();

        doReturn(isAuthenticated)
                .when(authenticationClient)
                .isAuthenticatedFromHeader(token);

        // When
        final boolean result = subject.isAuthenticated(token);

        // Then
        assertThat(result).isEqualTo(isAuthenticated);
    }

    @Test
    void isAuthenticated_whenAclThrowException_throwUnauthorizedException() {

        // Given
        final String token = aclFaker.book().title();
        final AuthenticationClient authenticationClient = mock(AuthenticationClient.class);

        doReturn(authenticationClient)
                .when(aclConnectApiClient)
                .authentication();

        doThrow(aclError())
                .when(authenticationClient)
                .isAuthenticatedFromHeader(token);

        // When
        final ThrowableAssert.ThrowingCallable callable = () -> subject.isAuthenticated(token);

        // Then
        assertThatThrownBy(callable)
                .isInstanceOf(UnauthorizedException.class);
    }

    private AclErrorException aclError() {

        return AclErrorException.build("Error");
    }
}

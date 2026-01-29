package com.jumia.skylens.http.in.acl;

import com.jumia.skylens.http.in.acl.authentication.AuthToken;
import com.jumia.skylens.http.in.acl.exceptions.UnauthorizedException;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pt.jumia.services.acl.lib.AclConnectApiClient;
import pt.jumia.services.acl.lib.AclErrorException;
import pt.jumia.services.acl.lib.RequestUser;
import pt.jumia.services.acl.lib.client.authentication.AuthenticationClient;
import pt.jumia.services.acl.lib.client.authorization.HierarchicalAuthorizationClient;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LogoutExecutorImplTest {

    private final AclFaker aclFaker = new AclFaker();

    @Mock
    private AclConnectApiClient<HierarchicalAuthorizationClient> aclConnectApiClient;

    @InjectMocks
    private LogoutExecutorImpl subject;

    @Test
    void logout_thenDecodesTokenAndLogsOut() {

        // Given
        final AuthToken authToken = mock(AuthToken.class);
        final String tokenValue = aclFaker.color().name();
        final AuthenticationClient authenticationClient = mock(AuthenticationClient.class);
        final RequestUser requestUser = aclFaker.requestUser().build();

        doReturn(authenticationClient)
                .when(aclConnectApiClient)
                .authentication();

        doReturn(tokenValue)
                .when(authToken)
                .getToken();

        doReturn(requestUser)
                .when(authenticationClient)
                .decodeTokenFromHeader(eq(tokenValue));

        // When
        subject.logout(authToken);

        // Then
        verify(authenticationClient)
                .logout(eq(requestUser));
    }

    @Test
    void logout_whenAclThrowsException_throwsUnauthorizedException() {

        // Given
        final AuthToken authToken = mock(AuthToken.class);
        final String tokenValue = aclFaker.color().name();
        final AuthenticationClient authenticationClient = mock(AuthenticationClient.class);

        doReturn(authenticationClient)
                .when(aclConnectApiClient)
                .authentication();

        doReturn(tokenValue)
                .when(authToken)
                .getToken();

        doThrow(AclErrorException.build("Error"))
                .when(authenticationClient)
                .decodeTokenFromHeader(eq(tokenValue));

        // When
        final ThrowableAssert.ThrowingCallable callable = () -> subject.logout(authToken);

        // Then
        assertThatThrownBy(callable)
                .isInstanceOf(UnauthorizedException.class);
    }
}

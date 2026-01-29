package com.jumia.skylens.http.in.acl.authentication.checkers.impl;

import com.jumia.skylens.http.in.acl.credentials.BasicCredential;
import com.jumia.skylens.http.in.acl.credentials.CredentialBuilder;
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
import pt.jumia.services.acl.lib.client.authorization.HierarchicalAuthorizationClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class BasicAuthorizerImplTest {

    private final DomainFaker faker = new DomainFaker();

    @Mock
    private CredentialBuilder credentialBuilder;

    @Mock
    private AclConnectApiClient<HierarchicalAuthorizationClient> aclConnectApiClient;

    @InjectMocks
    private BasicAuthorizerImpl subject;

    @Test
    void isAuthenticated_thenReturnAclApiResponse() {

        // Given
        final String token = faker.lordOfTheRings().character();
        final String username = faker.color().name();
        final String password = faker.ancient().god();
        final AuthenticationClient authenticationClient = mock(AuthenticationClient.class);
        final BasicCredential credential = mock(BasicCredential.class);

        doReturn(credential)
                .when(credentialBuilder)
                .ofBasicRawToken(token);

        doReturn(username)
                .when(credential)
                .getUsername();

        doReturn(password.toCharArray())
                .when(credential)
                .getPassword();

        doReturn(authenticationClient)
                .when(aclConnectApiClient)
                .authentication();

        // When
        final boolean result = subject.isAuthenticated(token);

        // Then
        assertThat(result).isTrue();
    }

    @Test
    void isAuthenticated_whenAclThrowsException_throwsUnauthorizedException() {

        // Given
        final String token = faker.lordOfTheRings().character();
        final String username = faker.color().name();
        final String password = faker.ancient().god();
        final AuthenticationClient authenticationClient = mock(AuthenticationClient.class);
        final BasicCredential credential = mock(BasicCredential.class);

        doReturn(credential)
                .when(credentialBuilder)
                .ofBasicRawToken(token);

        doReturn(username)
                .when(credential)
                .getUsername();

        doReturn(password.toCharArray())
                .when(credential)
                .getPassword();

        doReturn(authenticationClient)
                .when(aclConnectApiClient)
                .authentication();

        doThrow(AclErrorException.build("Error"))
                .when(authenticationClient)
                .authorize(any(), any());

        // When
        final ThrowableAssert.ThrowingCallable callable = () -> subject.isAuthenticated(token);

        // Then
        assertThatThrownBy(callable)
                .isInstanceOf(UnauthorizedException.class);
    }
}

package com.jumia.skylens.http.in.acl.authentication;

import com.jumia.skylens.http.in.acl.AuthInstances;
import com.jumia.skylens.http.in.acl.exceptions.UnauthorizedException;
import net.datafaker.Faker;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pt.jumia.services.acl.lib.AclConnectApiClient;
import pt.jumia.services.acl.lib.AclErrorException;
import pt.jumia.services.acl.lib.AclInstance;
import pt.jumia.services.acl.lib.client.authentication.AuthenticationClient;
import pt.jumia.services.acl.lib.client.authorization.DefaultAuthorizationClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class AclAuthenticatorTest {

    private final Faker faker = new Faker();

    @Mock
    private AclConnectApiClient<DefaultAuthorizationClient> aclConnectApiClient;

    @Mock
    private AuthInstances authInstances;

    @InjectMocks
    private AclAuthenticator subject;

    @Test
    void authenticate_whenAclApiThrowsException_thenThrowUnauthorizedException() {

        // Given
        final String tempToken = faker.harryPotter().spell();
        final UserAuthenticationType userAuthenticationType = UserAuthenticationType.INTERNAL;
        final String redirectUri = faker.internet().url();

        final AclInstance aclInstance = mock(AclInstance.class);
        final AuthInstances.Instance instance = AuthInstances.Instance.of(aclInstance, true);
        final AuthenticationClient authenticationClient = mock(AuthenticationClient.class);

        doReturn(instance)
                .when(authInstances)
                .getAuthInstance(userAuthenticationType);

        doReturn(authenticationClient)
                .when(aclConnectApiClient)
                .authentication();

        doThrow(AclErrorException.build("Error"))
                .when(authenticationClient)
                .tempTokenSwap(aclInstance, tempToken);

        // When
        final ThrowableAssert.ThrowingCallable callable = () -> subject.authenticate(tempToken,
                                                                                     userAuthenticationType,
                                                                                     redirectUri);

        // Then
        assertThatThrownBy(callable)
                .isInstanceOf(UnauthorizedException.class);
    }

    @Test
    void authenticate_whenGetsAuthInstance_thenShouldSwapToken() {

        // Given
        final String tempToken = faker.harryPotter().spell();
        final UserAuthenticationType userAuthenticationType = UserAuthenticationType.INTERNAL;
        final String redirectUri = faker.internet().url();

        final AclInstance aclInstance = mock(AclInstance.class);
        final AuthInstances.Instance instance = AuthInstances.Instance.of(aclInstance, true);
        final AuthenticationClient authenticationClient = mock(AuthenticationClient.class);

        doReturn(instance)
                .when(authInstances)
                .getAuthInstance(userAuthenticationType);

        doReturn(authenticationClient)
                .when(aclConnectApiClient)
                .authentication();

        final String token = faker.animal().name();
        doReturn(token)
                .when(authenticationClient)
                .tempTokenSwap(aclInstance, tempToken);

        // When
        final Token result = subject.authenticate(tempToken, userAuthenticationType, redirectUri);

        // Then
        final Token expected = Token.of(token);
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void getProvider() {

        // When
        final OAuthProvider result = subject.getProvider();

        // Then
        assertThat(result)
                .isEqualTo(OAuthProvider.GOOGLE);
    }
}

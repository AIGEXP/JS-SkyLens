package com.jumia.skylens.http.in.acl.permissions.checkers.impl;

import com.jumia.skylens.http.in.acl.authentication.AuthToken;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import pt.jumia.services.acl.lib.AclConnectApiClient;
import pt.jumia.services.acl.lib.RequestUser;
import pt.jumia.services.acl.lib.client.authentication.AuthenticationClient;
import pt.jumia.services.acl.lib.client.authorization.DefaultAuthorizationClient;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
@SuppressFBWarnings(value = {"URF_UNREAD_FIELD"}, justification = "I prefer to suppress these FindBugs warnings")
class PermissionBearerCheckerImplTest {

    @SuppressWarnings("unchecked")
    final AclConnectApiClient<DefaultAuthorizationClient> aclConnectApiClient = mock(AclConnectApiClient.class);

    @Spy
    @SuppressWarnings("unused")
    private List<AclConnectApiClient<DefaultAuthorizationClient>> aclConnectApiClients = new ArrayList<>(List.of(aclConnectApiClient));

    @Mock
    private AuthToken authToken;

    @InjectMocks
    private PermissionBearerCheckerImpl subject;

    @Test
    void createRequestUser_callsApiClient() {

        // Given
        final String token = "1234";
        final RequestUser requestUser = new RequestUser();
        final AuthenticationClient authenticationClient = mock(AuthenticationClient.class);

        doReturn(token)
                .when(authToken)
                .getToken();
        doReturn(authenticationClient).when(aclConnectApiClient).authentication();

        doReturn(requestUser)
                .when(authenticationClient)
                .decodeTokenFromHeader(token);

        // When
        final RequestUser result = subject.createRequestUser(authToken);

        // Then
        assertThat(result)
                .isEqualTo(requestUser);
    }
}

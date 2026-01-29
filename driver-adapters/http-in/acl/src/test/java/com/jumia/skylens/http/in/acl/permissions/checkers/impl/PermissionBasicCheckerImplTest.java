package com.jumia.skylens.http.in.acl.permissions.checkers.impl;

import com.jumia.skylens.http.in.acl.authentication.AuthToken;
import com.jumia.skylens.http.in.acl.credentials.BasicCredential;
import com.jumia.skylens.http.in.acl.credentials.CredentialBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pt.jumia.services.acl.lib.AclConnectApiClient;
import pt.jumia.services.acl.lib.RequestUser;
import pt.jumia.services.acl.lib.client.authorization.HierarchicalAuthorizationClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class PermissionBasicCheckerImplTest {

    @Mock
    private AclConnectApiClient<HierarchicalAuthorizationClient> aclConnectApiClient;

    @Mock
    private CredentialBuilder credentialBuilder;

    @Mock
    private AuthToken authToken;

    @InjectMocks
    private PermissionBasicCheckerImpl subject;

    @Test
    void createRequestUser_buildsCredential() {

        // Given
        final String token = "1234";
        doReturn(token)
                .when(authToken)
                .getToken();

        final String username = "username";
        final String password = "password";
        final BasicCredential basicCredential = new TestCredential(username, password.toCharArray());
        doReturn(basicCredential)
                .when(credentialBuilder)
                .ofBasicRawToken(token);

        // When
        final RequestUser result = subject.createRequestUser(authToken);

        // Then
        final RequestUser expected = RequestUser.fromBasic(username, password);
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    private record TestCredential(String username, char[] password) implements BasicCredential {

        @Override
        public String getUsername() {

            return username;
        }

        @Override
        public char[] getPassword() {

            return password;
        }
    }
}

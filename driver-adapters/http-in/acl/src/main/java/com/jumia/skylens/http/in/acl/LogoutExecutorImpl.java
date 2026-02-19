package com.jumia.skylens.http.in.acl;

import com.jumia.skylens.http.in.acl.authentication.AuthToken;
import com.jumia.skylens.http.in.acl.exceptions.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import pt.jumia.services.acl.lib.AclConnectApiClient;
import pt.jumia.services.acl.lib.AclErrorException;
import pt.jumia.services.acl.lib.RequestUser;
import pt.jumia.services.acl.lib.client.authorization.DefaultAuthorizationClient;

@RequiredArgsConstructor
public class LogoutExecutorImpl implements LogoutExecutor {

    private final AclConnectApiClient<DefaultAuthorizationClient> aclConnectApiClient;

    @Override
    public void logout(AuthToken authToken) {

        try {
            final RequestUser requestUser = aclConnectApiClient.authentication().decodeTokenFromHeader(authToken.getToken());
            aclConnectApiClient.authentication().logout(requestUser);
        } catch (AclErrorException _) {
            throw new UnauthorizedException();
        }
    }
}

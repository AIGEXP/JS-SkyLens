package com.jumia.skylens.http.in.acl.authentication;

import com.jumia.skylens.http.in.acl.AuthInstances;
import com.jumia.skylens.http.in.acl.exceptions.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import pt.jumia.services.acl.lib.AclConnectApiClient;
import pt.jumia.services.acl.lib.AclErrorException;
import pt.jumia.services.acl.lib.client.authorization.DefaultAuthorizationClient;

@RequiredArgsConstructor
public class AclAuthenticator implements Authenticator {

    private final AclConnectApiClient<DefaultAuthorizationClient> aclConnectApiClient;

    private final AuthInstances authInstances;

    @Override
    public Token authenticate(String tempToken, UserAuthenticationType userAuthenticationType, String redirectUri) {

        final AuthInstances.Instance authInstance = authInstances.getAuthInstance(userAuthenticationType);

        final String tokenResponse = swapTokenInAcl(tempToken, authInstance);
        return Token.of(tokenResponse);
    }

    @Override
    public OAuthProvider getProvider() {

        return OAuthProvider.GOOGLE;
    }

    private String swapTokenInAcl(String tempToken, AuthInstances.Instance authInstance) {

        try {
            return aclConnectApiClient.authentication().tempTokenSwap(authInstance.getAclInstance(), tempToken);
        } catch (AclErrorException exception) {
            throw new UnauthorizedException(exception);
        }
    }
}

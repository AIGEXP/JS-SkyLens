package com.jumia.skylens.http.in.acl.authentication.checkers.impl;

import com.jumia.skylens.http.in.acl.authentication.checkers.BearerAuthorizer;
import com.jumia.skylens.http.in.acl.exceptions.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pt.jumia.services.acl.lib.AclConnectApiClient;
import pt.jumia.services.acl.lib.AclErrorException;
import pt.jumia.services.acl.lib.client.authorization.DefaultAuthorizationClient;

@Slf4j
@RequiredArgsConstructor
public class BearerAuthorizerImpl implements BearerAuthorizer {

    private final AclConnectApiClient<DefaultAuthorizationClient> aclConnectApiClient;

    @Override
    public boolean isAuthenticated(String token) {

        try {
            return aclConnectApiClient.authentication().isAuthenticatedFromHeader(token);
        } catch (AclErrorException exception) {
            throw new UnauthorizedException(exception);
        }
    }
}

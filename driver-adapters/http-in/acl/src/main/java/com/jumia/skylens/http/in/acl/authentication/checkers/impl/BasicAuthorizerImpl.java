package com.jumia.skylens.http.in.acl.authentication.checkers.impl;

import com.jumia.skylens.http.in.acl.authentication.checkers.BasicAuthorizer;
import com.jumia.skylens.http.in.acl.credentials.BasicCredential;
import com.jumia.skylens.http.in.acl.credentials.CredentialBuilder;
import com.jumia.skylens.http.in.acl.exceptions.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pt.jumia.services.acl.lib.AclConnectApiClient;
import pt.jumia.services.acl.lib.AclErrorException;
import pt.jumia.services.acl.lib.client.authorization.HierarchicalAuthorizationClient;

@Slf4j
@RequiredArgsConstructor
public class BasicAuthorizerImpl implements BasicAuthorizer {

    private final CredentialBuilder credentialBuilder;

    private final AclConnectApiClient<HierarchicalAuthorizationClient> aclConnectApiClient;

    @Override
    public boolean isAuthenticated(String token) {

        final BasicCredential credential = credentialBuilder.ofBasicRawToken(token);

        try {
            aclConnectApiClient.authentication().authorize(credential.getUsername(),
                                                           String.valueOf(credential.getPassword()));

            return true;
        } catch (AclErrorException exception) {
            throw new UnauthorizedException(exception);
        }
    }
}

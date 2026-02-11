package com.jumia.skylens.http.in.acl.permissions.checkers.impl;

import com.jumia.skylens.http.in.acl.authentication.AuthToken;
import com.jumia.skylens.http.in.acl.credentials.BasicCredential;
import com.jumia.skylens.http.in.acl.credentials.CredentialBuilder;
import com.jumia.skylens.http.in.acl.permissions.AclTargetPathBuilder;
import com.jumia.skylens.http.in.acl.permissions.checkers.BasicPermissionChecker;
import pt.jumia.services.acl.lib.AclConnectApiClient;
import pt.jumia.services.acl.lib.RequestUser;
import pt.jumia.services.acl.lib.client.authorization.HierarchicalAuthorizationClient;

import java.util.List;

public class PermissionBasicCheckerImpl extends PermissionAbstractCheckerImpl implements BasicPermissionChecker {

    private final CredentialBuilder credentialBuilder;

    public PermissionBasicCheckerImpl(List<AclConnectApiClient<HierarchicalAuthorizationClient>> aclConnectApiClients,
                                      AclTargetPathBuilder aclTargetPathBuilder,
                                      CredentialBuilder credentialBuilder) {

        super(aclConnectApiClients, aclTargetPathBuilder);
        this.credentialBuilder = credentialBuilder;
    }

    @Override
    RequestUser createRequestUser(AuthToken authorization) {

        final BasicCredential credential = credentialBuilder.ofBasicRawToken(authorization.getToken());
        return RequestUser.fromBasic(credential.getUsername(), new String(credential.getPassword()));
    }
}

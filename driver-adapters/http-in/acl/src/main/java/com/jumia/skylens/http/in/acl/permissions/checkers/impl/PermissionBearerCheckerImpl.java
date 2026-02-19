package com.jumia.skylens.http.in.acl.permissions.checkers.impl;

import com.jumia.skylens.http.in.acl.authentication.AuthToken;
import com.jumia.skylens.http.in.acl.permissions.checkers.BearerPermissionChecker;
import pt.jumia.services.acl.lib.AclConnectApiClient;
import pt.jumia.services.acl.lib.RequestUser;
import pt.jumia.services.acl.lib.client.authorization.DefaultAuthorizationClient;

import java.util.List;

public class PermissionBearerCheckerImpl extends PermissionAbstractCheckerImpl implements BearerPermissionChecker {

    public PermissionBearerCheckerImpl(List<AclConnectApiClient<DefaultAuthorizationClient>> aclConnectApiClients) {

        super(aclConnectApiClients);
    }

    @Override
    RequestUser createRequestUser(AuthToken authorization) {

        return getAclConnectApiClients().getFirst().authentication().decodeTokenFromHeader(authorization.getToken());
    }
}

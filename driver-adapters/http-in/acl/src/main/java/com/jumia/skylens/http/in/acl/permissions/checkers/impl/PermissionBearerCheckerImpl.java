package com.jumia.skylens.http.in.acl.permissions.checkers.impl;

import com.jumia.skylens.http.in.acl.authentication.AuthToken;
import com.jumia.skylens.http.in.acl.permissions.AclTargetPathBuilder;
import com.jumia.skylens.http.in.acl.permissions.checkers.BearerPermissionChecker;
import pt.jumia.services.acl.lib.AclConnectApiClient;
import pt.jumia.services.acl.lib.RequestUser;
import pt.jumia.services.acl.lib.client.authorization.HierarchicalAuthorizationClient;

public class PermissionBearerCheckerImpl extends PermissionAbstractCheckerImpl implements BearerPermissionChecker {

    public PermissionBearerCheckerImpl(AclConnectApiClient<HierarchicalAuthorizationClient> aclConnectApiClient,
                                       AclTargetPathBuilder aclTargetPathBuilder) {

        super(aclConnectApiClient, aclTargetPathBuilder);
    }

    @Override
    RequestUser createRequestUser(AuthToken authorization) {

        return getAclConnectApiClient().authentication().decodeTokenFromHeader(authorization.getToken());
    }
}

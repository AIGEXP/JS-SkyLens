package com.jumia.skylens.http.in.acl.permissions.checkers.impl;

import com.jumia.skylens.http.in.acl.authentication.AuthToken;
import com.jumia.skylens.http.in.acl.exceptions.AclInternalErrorException;
import com.jumia.skylens.http.in.acl.exceptions.ForbiddenException;
import com.jumia.skylens.http.in.acl.permissions.AclTargetPathBuilder;
import com.jumia.skylens.http.in.acl.permissions.ApplicationPermission;
import com.jumia.skylens.http.in.acl.permissions.Permission;
import com.jumia.skylens.http.in.acl.permissions.checkers.PermissionChecker;
import com.jumia.skylens.http.in.acl.resources.ApplicationResource;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pt.jumia.services.acl.lib.AclConnectApiClient;
import pt.jumia.services.acl.lib.AclErrorException;
import pt.jumia.services.acl.lib.RequestUser;
import pt.jumia.services.acl.lib.client.authorization.HierarchicalAuthorizationClient;
import pt.jumia.services.acl.lib.client.authorization.Path;

import java.util.List;
import java.util.Set;

@Slf4j
@Getter
@RequiredArgsConstructor
abstract class PermissionAbstractCheckerImpl implements PermissionChecker {

    private final List<AclConnectApiClient<HierarchicalAuthorizationClient>> aclConnectApiClients;

    private final AclTargetPathBuilder aclTargetPathBuilder;

    @Override
    public void checkAnyPermission(AuthToken authorization, Permission... permissions) {

        checkAnyPermission(authorization, Set.of(permissions));
    }

    @Override
    public void checkAnyPermission(AuthToken authorization, Set<Permission> permissions) {

        final RequestUser requestUser = createRequestUser(authorization);
        final boolean hasPermission = permissions
                .stream()
                .anyMatch(permission -> hasPermission(requestUser, permission));

        if (!hasPermission) {
            throw new ForbiddenException();
        }
    }

    @Override
    public void checkPermission(AuthToken authorization, Permission permission) {

        checkAnyPermission(authorization, permission);
    }

    @Override
    public boolean isAdmin(AuthToken abstractAuthToken) {

        return hasPermission(abstractAuthToken, ApplicationPermission.of(ApplicationResource.ADMIN));
    }

    abstract RequestUser createRequestUser(AuthToken authorization);

    @Override
    public boolean hasPermission(AuthToken authToken, Permission permission) {

        return hasPermission(createRequestUser(authToken), permission);
    }

    private boolean hasPermission(RequestUser requestUser, Permission permission) {

        try {
            final Path targetPath = aclTargetPathBuilder.buildPath(permission);

            return aclConnectApiClients.stream()
                    .anyMatch(aclConnectApiClient -> aclConnectApiClient.authorization()
                            .hasPermission(requestUser, requestUser.getUsername(), permission.resource(), targetPath));
        } catch (AclErrorException aclErrorException) {

            log.error("ACL internal error", aclErrorException);
            throw AclInternalErrorException.with(aclErrorException);
        }
    }
}

package com.jumia.skylens.http.in.acl.permissions.checkers.impl.disabled;

import com.jumia.skylens.http.in.acl.authentication.AuthToken;
import com.jumia.skylens.http.in.acl.permissions.Permission;
import com.jumia.skylens.http.in.acl.permissions.checkers.PermissionChecker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

@Slf4j
@RequiredArgsConstructor
public class PermissionDisabledCheckerImpl implements PermissionChecker {

    @Override
    public void checkAnyPermission(AuthToken authorization, Permission... permissions) {

        //do nothing
    }

    @Override
    public void checkAnyPermission(AuthToken authorization, Set<Permission> permissions) {

        //do nothing
    }

    @Override
    public void checkPermission(AuthToken authorization, Permission permission) {

        //do nothing
    }

    @Override
    public boolean hasPermission(AuthToken authToken, Permission permission) {

        return true;
    }

    @Override
    public boolean isAdmin(AuthToken abstractAuthToken) {

        return true;
    }
}

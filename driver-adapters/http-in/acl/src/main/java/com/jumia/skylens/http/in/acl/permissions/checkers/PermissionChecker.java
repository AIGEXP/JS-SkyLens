package com.jumia.skylens.http.in.acl.permissions.checkers;

import com.jumia.skylens.http.in.acl.authentication.AuthToken;
import com.jumia.skylens.http.in.acl.permissions.Permission;

import java.util.Set;

public interface PermissionChecker {

    void checkAnyPermission(AuthToken authorization, Permission... permissions);

    void checkAnyPermission(AuthToken authorization, Set<Permission> permissions);

    void checkPermission(AuthToken authorization, Permission permission);

    boolean hasPermission(AuthToken authToken, Permission permission);

    boolean isAdmin(AuthToken abstractAuthToken);
}

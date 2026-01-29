package com.jumia.skylens.http.in.acl.tokens;

import com.jumia.skylens.http.in.acl.authentication.checkers.BearerAuthorizer;
import com.jumia.skylens.http.in.acl.exceptions.UnauthorizedException;
import com.jumia.skylens.http.in.acl.permissions.checkers.BearerPermissionChecker;

public final class BearerAuthToken extends AbstractAuthToken {

    public BearerAuthToken(String token, BearerAuthorizer authChecker, BearerPermissionChecker permissionChecker) {

        super(token, authChecker, permissionChecker);
    }

    @Override
    protected void validate() {

        if (!getAuthorizer().isAuthenticated(getToken())) {
            throw new UnauthorizedException();
        }
    }
}

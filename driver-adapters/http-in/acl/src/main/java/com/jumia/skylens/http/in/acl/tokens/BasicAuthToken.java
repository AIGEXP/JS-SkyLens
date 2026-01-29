package com.jumia.skylens.http.in.acl.tokens;

import com.jumia.skylens.http.in.acl.authentication.checkers.BasicAuthorizer;
import com.jumia.skylens.http.in.acl.exceptions.UnauthorizedException;
import com.jumia.skylens.http.in.acl.permissions.checkers.BasicPermissionChecker;

public final class BasicAuthToken extends AbstractAuthToken {

    public BasicAuthToken(String token, BasicAuthorizer authChecker, BasicPermissionChecker permissionChecker) {

        super(token, authChecker, permissionChecker);
    }

    @Override
    protected void validate() {

        if (!getAuthorizer().isAuthenticated(getToken())) {
            throw new UnauthorizedException();
        }
    }
}

package com.jumia.skylens.http.in.acl.tokens;

import com.jumia.skylens.http.in.acl.authentication.AuthToken;
import com.jumia.skylens.http.in.acl.permissions.ApplicationPermission;
import com.jumia.skylens.http.in.acl.permissions.PartnerPermission;
import com.jumia.skylens.http.in.acl.permissions.checkers.Authorizer;
import com.jumia.skylens.http.in.acl.permissions.checkers.PermissionChecker;
import com.jumia.skylens.http.in.acl.resources.ApplicationResource;
import com.jumia.skylens.http.in.acl.resources.PartnerResource;
import lombok.Getter;

import java.util.UUID;

@Getter
public abstract class AbstractAuthToken implements AuthToken {

    private final String token;

    private final Authorizer authorizer;

    private final PermissionChecker permissionChecker;

    protected AbstractAuthToken(String token, Authorizer authorizer, PermissionChecker permissionChecker) {

        this.token = token;
        this.authorizer = authorizer;
        this.permissionChecker = permissionChecker;
        validate();
    }

    @Override
    public void checkPermission(ApplicationResource applicationResource, boolean allowAdmin) {

        permissionChecker.checkAnyPermission(this, ApplicationPermission.of(allowAdmin, applicationResource));
    }

    @Override
    public void checkPermission(UUID partnerTarget, PartnerResource partnerResource, boolean allowAdmin) {

        permissionChecker.checkAnyPermission(this, PartnerPermission.of(allowAdmin, partnerTarget, partnerResource));
    }

    @Override
    public boolean isAdmin() {

        return permissionChecker.isAdmin(this);
    }

    protected abstract void validate();
}

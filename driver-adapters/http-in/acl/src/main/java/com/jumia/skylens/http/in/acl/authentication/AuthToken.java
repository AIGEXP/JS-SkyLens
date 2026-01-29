package com.jumia.skylens.http.in.acl.authentication;

import com.jumia.skylens.http.in.acl.resources.ApplicationResource;
import com.jumia.skylens.http.in.acl.resources.PartnerResource;

import java.util.UUID;

public interface AuthToken {

    void checkPermission(ApplicationResource applicationResource, boolean allowAdmin);

    default void checkPermission(ApplicationResource applicationResource) {

        checkPermission(applicationResource, true);
    }

    void checkPermission(UUID partnerTarget, PartnerResource partnerResource, boolean allowAdmin);

    default void checkPermission(UUID partnerTarget, PartnerResource partnerResource) {

        checkPermission(partnerTarget, partnerResource, true);
    }

    String getToken();

    boolean isAdmin();
}

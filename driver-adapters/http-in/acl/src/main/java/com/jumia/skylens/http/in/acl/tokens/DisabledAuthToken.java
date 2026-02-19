package com.jumia.skylens.http.in.acl.tokens;

import com.jumia.skylens.http.in.acl.authentication.AuthToken;
import com.jumia.skylens.http.in.acl.resources.ApplicationResource;
import com.jumia.skylens.http.in.acl.resources.CountryResource;
import com.jumia.skylens.http.in.acl.resources.PartnerResource;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public final class DisabledAuthToken implements AuthToken {

    @Override
    public void checkPermission(ApplicationResource applicationResource, boolean allowAdmin) {

        // When authentication is disabled, no permissions should be checked
    }

    @Override
    public void checkPermission(UUID partnerTarget, PartnerResource partnerResource, boolean allowAdmin) {
        // When authentication is disabled, no permissions should be checked
    }

    @Override
    public void checkPermission(String networkTarget, CountryResource countryResource, boolean allowAdmin) {

        // When authentication is disabled, no permissions should be checked
    }

    @Override
    public String getToken() {

        return null;
    }

    @Override
    public boolean isAdmin() {

        return true;
    }
}

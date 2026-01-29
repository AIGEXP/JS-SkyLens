package com.jumia.skylens.http.in.acl.authentication.disabled;

import com.jumia.skylens.http.in.acl.authentication.Authenticator;
import com.jumia.skylens.http.in.acl.authentication.Authenticators;
import com.jumia.skylens.http.in.acl.authentication.OAuthProvider;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DisabledAuthenticatorsImpl implements Authenticators {

    private final DisabledAuthenticatorImpl disabledAuthenticator;

    @Override
    public Authenticator get(OAuthProvider provider) {

        return disabledAuthenticator;
    }
}

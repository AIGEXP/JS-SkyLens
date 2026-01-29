package com.jumia.skylens.http.in.acl.authentication.disabled;

import com.jumia.skylens.http.in.acl.authentication.Authenticator;
import com.jumia.skylens.http.in.acl.authentication.OAuthProvider;
import com.jumia.skylens.http.in.acl.authentication.Token;
import com.jumia.skylens.http.in.acl.authentication.UserAuthenticationType;

public class DisabledAuthenticatorImpl implements Authenticator {

    @Override
    public Token authenticate(String authorizationString, UserAuthenticationType userAuthenticationType, String redirectUri) {

        return Token.of("");
    }

    @Override
    public OAuthProvider getProvider() {

        return null;
    }
}

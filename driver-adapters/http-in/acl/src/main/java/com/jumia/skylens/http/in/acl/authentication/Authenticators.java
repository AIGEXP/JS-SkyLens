package com.jumia.skylens.http.in.acl.authentication;

public interface Authenticators {

    Authenticator get(OAuthProvider provider);
}

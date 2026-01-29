package com.jumia.skylens.http.in.acl.authentication;

public interface Authenticator {

    Token authenticate(String authorizationString, UserAuthenticationType userAuthenticationType, String redirectUri);

    OAuthProvider getProvider();
}

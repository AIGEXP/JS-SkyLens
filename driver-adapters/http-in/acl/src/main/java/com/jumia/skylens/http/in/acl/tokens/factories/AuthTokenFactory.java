package com.jumia.skylens.http.in.acl.tokens.factories;

import com.jumia.skylens.http.in.acl.authentication.AuthToken;

public interface AuthTokenFactory {

    AuthToken create(String token);
}

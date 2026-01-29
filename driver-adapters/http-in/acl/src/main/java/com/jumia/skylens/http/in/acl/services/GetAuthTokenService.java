package com.jumia.skylens.http.in.acl.services;

import com.jumia.skylens.http.in.acl.authentication.AuthToken;

public interface GetAuthTokenService {

    AuthToken get(String authToken);
}

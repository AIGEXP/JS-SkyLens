package com.jumia.skylens.http.in.acl.services;

import com.jumia.skylens.http.in.acl.authentication.AuthToken;
import com.jumia.skylens.http.in.acl.tokens.factories.AuthTokenFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetAuthTokenServiceImpl implements GetAuthTokenService {

    private final AuthTokenFactory authTokenFactory;

    @Override
    public AuthToken get(String authToken) {

        return authTokenFactory.create(authToken);
    }
}

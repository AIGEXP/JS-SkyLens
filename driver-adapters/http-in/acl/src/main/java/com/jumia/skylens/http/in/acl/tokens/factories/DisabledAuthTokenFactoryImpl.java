package com.jumia.skylens.http.in.acl.tokens.factories;

import com.jumia.skylens.http.in.acl.authentication.AuthToken;
import com.jumia.skylens.http.in.acl.tokens.DisabledAuthToken;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DisabledAuthTokenFactoryImpl implements AuthTokenFactory {

    public AuthToken create(String token) {

        return new DisabledAuthToken();
    }
}

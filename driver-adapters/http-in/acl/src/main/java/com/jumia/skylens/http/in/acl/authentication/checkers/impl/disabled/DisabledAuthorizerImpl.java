package com.jumia.skylens.http.in.acl.authentication.checkers.impl.disabled;

import com.jumia.skylens.http.in.acl.permissions.checkers.Authorizer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class DisabledAuthorizerImpl implements Authorizer {

    @Override
    public boolean isAuthenticated(String token) {

        return true;
    }
}

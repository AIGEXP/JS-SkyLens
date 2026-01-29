package com.jumia.skylens.http.in.acl.tokens.factories;

import com.jumia.skylens.http.in.acl.authentication.AuthToken;
import com.jumia.skylens.http.in.acl.authentication.checkers.BasicAuthorizer;
import com.jumia.skylens.http.in.acl.authentication.checkers.BearerAuthorizer;
import com.jumia.skylens.http.in.acl.exceptions.MissingAuthenticationTokenException;
import com.jumia.skylens.http.in.acl.exceptions.UnknownAuthenticationPatternException;
import com.jumia.skylens.http.in.acl.permissions.checkers.BasicPermissionChecker;
import com.jumia.skylens.http.in.acl.permissions.checkers.BearerPermissionChecker;
import com.jumia.skylens.http.in.acl.tokens.BasicAuthToken;
import com.jumia.skylens.http.in.acl.tokens.BearerAuthToken;
import com.jumia.skylens.http.in.acl.utils.AuthenticationPatterns;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthTokenFactoryImpl implements AuthTokenFactory {

    private final BasicAuthorizer basicAuthChecker;

    private final BearerAuthorizer bearerAuthChecker;

    private final BasicPermissionChecker basicPermissionChecker;

    private final BearerPermissionChecker bearerPermissionChecker;

    private final AuthenticationPatterns authenticationPatterns;

    public AuthToken create(String token) {

        validateTokenNotEmpty(token);

        if (authenticationPatterns.isBearerAuth(token)) {
            return new BearerAuthToken(token, bearerAuthChecker, bearerPermissionChecker);
        } else if (authenticationPatterns.isBasicAuth(token)) {
            return new BasicAuthToken(token, basicAuthChecker, basicPermissionChecker);
        } else {
            throw UnknownAuthenticationPatternException.invalidPattern();
        }
    }

    private void validateTokenNotEmpty(String token) {

        if (token == null || token.isBlank()) {
            throw MissingAuthenticationTokenException.missingToken();
        }
    }
}

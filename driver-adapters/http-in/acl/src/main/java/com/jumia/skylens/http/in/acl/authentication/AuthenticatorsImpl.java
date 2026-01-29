package com.jumia.skylens.http.in.acl.authentication;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AuthenticatorsImpl implements Authenticators {

    private final Map<OAuthProvider, Authenticator> authenticators;

    public AuthenticatorsImpl(List<Authenticator> authenticators) {

        this.authenticators = authenticators.stream()
                .collect(Collectors.toMap(Authenticator::getProvider, Function.identity()));
    }

    @Override
    public Authenticator get(OAuthProvider provider) {

        return authenticators.get(provider);
    }
}

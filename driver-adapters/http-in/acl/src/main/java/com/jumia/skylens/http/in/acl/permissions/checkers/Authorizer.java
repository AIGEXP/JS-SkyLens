package com.jumia.skylens.http.in.acl.permissions.checkers;

public interface Authorizer {

    boolean isAuthenticated(String token);
}

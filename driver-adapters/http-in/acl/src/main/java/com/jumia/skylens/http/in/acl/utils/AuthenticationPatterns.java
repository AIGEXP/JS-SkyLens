package com.jumia.skylens.http.in.acl.utils;

import java.util.regex.Pattern;

public class AuthenticationPatterns {

    //token from: 'Basic [BASE_64_CHARACTERS]'
    private static final Pattern PATTERN_BASIC = Pattern.compile(("^(Basic)[ ]") + "(.*)$");

    //token from: 'Bearer [BASE_64_CHARACTERS]'
    private static final Pattern PATTERN_BEARER = Pattern.compile(("^(Bearer)[ ]") + "(.*)$");

    public boolean isBasicAuth(String authToken) {

        return PATTERN_BASIC.matcher(authToken).find();
    }

    public boolean isBearerAuth(String authToken) {

        return PATTERN_BEARER.matcher(authToken).find();
    }
}

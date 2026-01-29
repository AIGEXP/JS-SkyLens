package com.jumia.skylens.http.in.acl.utils;

import com.jumia.skylens.http.in.acl.exceptions.UnknownAuthenticationPatternException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TokenExtractor {

    public static final String TOKEN_GROUP = "token";

    private final Pattern pattern = Pattern.compile("^.*[ ](?<" + TOKEN_GROUP + ">.*)$");

    public String extractToken(String fullToken) {

        final Matcher matcher = pattern.matcher(fullToken);
        if (matcher.find()) {
            return matcher.group(TOKEN_GROUP);
        }
        throw UnknownAuthenticationPatternException.invalidPattern();
    }
}

package com.jumia.skylens.http.in.acl.credentials;

import com.jumia.skylens.http.in.acl.utils.TokenExtractor;
import lombok.RequiredArgsConstructor;

import java.util.Base64;

@RequiredArgsConstructor
public class CredentialBuilder {

    private final TokenExtractor tokenExtractor;

    /**
     * It parses a {@code "Basic BASE_64_STRING"}.
     *
     * @param rawToken {@code "Basic BASE_64_STRING"}
     * @return {@link BasicCredential}
     */
    public BasicCredential ofBasicRawToken(String rawToken) {

        return new BasicCredentialImpl(Base64.getDecoder().decode(parseAuthorizationTokenValue(rawToken)));
    }

    private String parseAuthorizationTokenValue(String token) {

        return tokenExtractor.extractToken(token);
    }
}

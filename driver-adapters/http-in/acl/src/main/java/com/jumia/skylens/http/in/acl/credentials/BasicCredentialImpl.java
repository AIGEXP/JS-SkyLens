package com.jumia.skylens.http.in.acl.credentials;

import com.jumia.skylens.http.in.acl.exceptions.InvalidBasicAuthTokenException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.nio.charset.Charset;

@Value
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
class BasicCredentialImpl implements BasicCredential {

    String username;

    char[] password;

    BasicCredentialImpl(byte[] decode) {

        this(new String(decode, Charset.defaultCharset()));
    }

    BasicCredentialImpl(String charTokenDecoded) {

        //Pattern to get username and password: 'username:password'
        if (charTokenDecoded.contains(":")) {
            final String[] credentials = charTokenDecoded.split(":");
            if (credentials.length == 0) {
                throw InvalidBasicAuthTokenException.invalidBasicToken();
            }

            this.username = credentials[0];

            if (credentials.length > 1) {
                this.password = credentials[1].toCharArray();
            } else {
                this.password = null;
            }
        } else {
            throw InvalidBasicAuthTokenException.invalidBasicToken();
        }
    }
}

package com.jumia.skylens.http.in.acl.authentication;

import lombok.Value;

@Value(staticConstructor = "of")
public class Token {

    String value;
}

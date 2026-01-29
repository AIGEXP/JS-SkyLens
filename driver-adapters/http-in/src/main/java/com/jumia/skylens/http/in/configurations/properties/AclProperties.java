package com.jumia.skylens.http.in.configurations.properties;

import com.jumia.skylens.http.in.acl.authentication.UserAuthenticationType;

public interface AclProperties {

    String getIssuer();

    Integer getReadTimeout();

    String getUrl();

    UserAuthenticationType getUserAuthenticationType();

    boolean isDefaultInstance();
}

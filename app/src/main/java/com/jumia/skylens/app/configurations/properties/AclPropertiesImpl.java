package com.jumia.skylens.app.configurations.properties;

import com.jumia.skylens.http.in.acl.authentication.UserAuthenticationType;
import com.jumia.skylens.http.in.configurations.properties.AbstractAclProperties;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

@EqualsAndHashCode(callSuper = true)
@ConfigurationProperties("app.acl-service.internal")
@Value
class AclPropertiesImpl extends AbstractAclProperties {

    UserAuthenticationType userAuthenticationType = UserAuthenticationType.INTERNAL;

    @ConstructorBinding
    AclPropertiesImpl(String url,
                      Integer readTimeout,
                      @org.springframework.beans.factory.annotation.Value("true") boolean defaultInstance,
                      String issuer) {

        super(url, readTimeout, defaultInstance, issuer);
    }
}

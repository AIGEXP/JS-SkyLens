package com.jumia.skylens.http.in.acl;

import com.jumia.skylens.http.in.acl.authentication.UserAuthenticationType;
import lombok.Value;
import pt.jumia.services.acl.lib.AclInstance;

import java.util.Map;

public interface AuthInstances {

    Instance getAuthInstance(UserAuthenticationType userAuthenticationType);

    Map<UserAuthenticationType, Instance> getAuthInstances();

    @Value(staticConstructor = "of")
    class Instance {

        AclInstance aclInstance;

        boolean isDefault;
    }
}

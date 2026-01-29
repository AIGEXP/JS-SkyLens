package com.jumia.skylens.http.in.configurations;

import com.jumia.skylens.http.in.acl.AuthInstances;
import com.jumia.skylens.http.in.acl.authentication.UserAuthenticationType;
import com.jumia.skylens.http.in.configurations.properties.AclProperties;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Getter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import pt.jumia.services.acl.lib.AclInstance;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Getter
@Configuration
@ConditionalOnProperty("app.acl-service.enabled")
@SuppressFBWarnings(value = {"EI_EXPOSE_REP"}, justification = "I prefer to suppress these FindBugs warnings")
public class AuthInstancesImpl implements AuthInstances {

    private static final TimeUnit MILLISECONDS = TimeUnit.MILLISECONDS;

    private final Map<UserAuthenticationType, Instance> authInstances;

    public AuthInstancesImpl(List<AclProperties> aclProperties) {

        this.authInstances = aclProperties.stream()
                .collect(Collectors.toUnmodifiableMap(AclProperties::getUserAuthenticationType, this::authInstanceOf));
    }

    @Override
    public Instance getAuthInstance(UserAuthenticationType userAuthenticationType) {

        return authInstances.get(userAuthenticationType);
    }

    private Instance authInstanceOf(AclProperties properties) {

        return Instance.of(AclInstance.of(properties.getUrl(),
                                          properties.getIssuer(),
                                          properties.getReadTimeout(),
                                          MILLISECONDS),
                           properties.isDefaultInstance());
    }
}

package com.jumia.skylens.http.in.configurations;

import com.jumia.skylens.http.in.acl.AuthInstances;
import com.jumia.skylens.http.in.acl.authentication.UserAuthenticationType;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import pt.jumia.services.acl.lib.AclConnectApiClient;
import pt.jumia.services.acl.lib.AclConnectApiClientBuilder;
import pt.jumia.services.acl.lib.CacheStrategy;
import pt.jumia.services.acl.lib.client.authorization.HierarchicalAuthorizationClient;

import java.util.Arrays;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty("app.acl-service.enabled")
@SuppressFBWarnings(value = {"EI_EXPOSE_REP"}, justification = "I prefer to suppress these FindBugs warnings")
public class AclApiBuilder {

    private final AuthInstances authInstances;

    private final CacheStrategy cacheStrategy;

    public AclConnectApiClient<HierarchicalAuthorizationClient> buildAclConnectApiClient() {

        final AclConnectApiClientBuilder aclConnectApiClientBuilder = getAclConnectApiClientBuilder();

        addAclInstances(aclConnectApiClientBuilder);

        return aclConnectApiClientBuilder.buildAsHierarchicalAuthClient();
    }

    private void addAclInstances(AclConnectApiClientBuilder aclConnectApiClientBuilder) {

        Arrays.stream(UserAuthenticationType.values())
                .map(authInstances.getAuthInstances()::get)
                .forEach(authInstance -> aclConnectApiClientBuilder.addAclInstance(authInstance.getAclInstance(),
                        authInstance.isDefault()));
    }

    private AclConnectApiClientBuilder getAclConnectApiClientBuilder() {

        return new AclConnectApiClientBuilder()
                .setApplicationCode("LogisticPartners")
                .setLogger(new AclLogger())
                .setCacheStrategy(cacheStrategy);
    }
}

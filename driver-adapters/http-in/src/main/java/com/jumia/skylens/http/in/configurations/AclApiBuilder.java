package com.jumia.skylens.http.in.configurations;

import com.jumia.skylens.http.in.acl.AuthInstances;
import com.jumia.skylens.http.in.acl.authentication.UserAuthenticationType;
import com.jumia.skylens.http.in.configurations.properties.AclCacheProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import pt.jumia.services.acl.lib.AclConnectApiClient;
import pt.jumia.services.acl.lib.AclConnectApiClientBuilder;
import pt.jumia.services.acl.lib.CacheStrategy;
import pt.jumia.services.acl.lib.cache.InMemoryCacheStrategy;
import pt.jumia.services.acl.lib.client.authorization.HierarchicalAuthorizationClient;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty("app.acl-service.enabled")
public class AclApiBuilder {

    private final AuthInstances authInstances;

    private final AclCacheProperties aclCacheProperties;

    public AclConnectApiClient<HierarchicalAuthorizationClient> buildAclConnectApiClient(final String applicationCode) {

        final AclConnectApiClientBuilder aclConnectApiClientBuilder = getAclConnectApiClientBuilder(applicationCode);

        addAclInstances(aclConnectApiClientBuilder);

        return aclConnectApiClientBuilder.buildAsHierarchicalAuthClient();
    }

    private void addAclInstances(AclConnectApiClientBuilder aclConnectApiClientBuilder) {

        Arrays.stream(UserAuthenticationType.values())
                .map(authInstances.getAuthInstances()::get)
                .forEach(authInstance -> aclConnectApiClientBuilder.addAclInstance(authInstance.getAclInstance(),
                                                                                   authInstance.isDefault()));
    }

    private AclConnectApiClientBuilder getAclConnectApiClientBuilder(final String applicationCode) {

        return new AclConnectApiClientBuilder()
                .setApplicationCode(applicationCode)
                .setLogger(new AclLogger())
                .setCacheStrategy(cacheStrategy());
    }

    private CacheStrategy cacheStrategy() {

        if (aclCacheProperties == null) {

            return InMemoryCacheStrategy.newInstance(1, TimeUnit.HOURS);
        }

        return InMemoryCacheStrategy.newInstance(aclCacheProperties.expireDuration().toMillis(),
                                                 TimeUnit.MILLISECONDS);
    }
}

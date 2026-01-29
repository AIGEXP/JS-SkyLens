package com.jumia.skylens.http.in.configurations;

import com.jumia.skylens.http.in.acl.authentication.checkers.impl.BasicAuthorizerImpl;
import com.jumia.skylens.http.in.acl.authentication.checkers.impl.BearerAuthorizerImpl;
import com.jumia.skylens.http.in.acl.credentials.CredentialBuilder;
import com.jumia.skylens.http.in.acl.permissions.AclTargetPathBuilder;
import com.jumia.skylens.http.in.acl.permissions.checkers.impl.PermissionBasicCheckerImpl;
import com.jumia.skylens.http.in.acl.permissions.checkers.impl.PermissionBearerCheckerImpl;
import com.jumia.skylens.http.in.acl.tokens.factories.AuthTokenFactory;
import com.jumia.skylens.http.in.acl.tokens.factories.AuthTokenFactoryImpl;
import com.jumia.skylens.http.in.acl.utils.AuthenticationPatterns;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pt.jumia.services.acl.lib.AclConnectApiClient;
import pt.jumia.services.acl.lib.client.authorization.HierarchicalAuthorizationClient;

@Configuration
@ConditionalOnProperty("app.acl-service.enabled")
public class AclAuthorizerConfiguration {

    @Bean
    AclConnectApiClient<HierarchicalAuthorizationClient> aclConnectApiClient(AclApiBuilder aclApiBuilder) {

        return aclApiBuilder.buildAclConnectApiClient();
    }

    @Bean
    BasicAuthorizerImpl authBasicChecker(CredentialBuilder credentialBuilder,
                                         AclConnectApiClient<HierarchicalAuthorizationClient> aclConnectApiClient) {

        return new BasicAuthorizerImpl(credentialBuilder, aclConnectApiClient);
    }

    @Bean
    BearerAuthorizerImpl authBearerChecker(AclConnectApiClient<HierarchicalAuthorizationClient> aclConnectApiClient) {

        return new BearerAuthorizerImpl(aclConnectApiClient);
    }

    @Bean
    AuthTokenFactory authTokenFactory(BasicAuthorizerImpl authBasicChecker,
                                      BearerAuthorizerImpl authBearerChecker,
                                      PermissionBasicCheckerImpl permissionBasicChecker,
                                      PermissionBearerCheckerImpl permissionBearerChecker,
                                      AuthenticationPatterns authenticationPatterns) {

        return new AuthTokenFactoryImpl(authBasicChecker,
                                        authBearerChecker,
                                        permissionBasicChecker,
                                        permissionBearerChecker,
                                        authenticationPatterns);
    }

    @Bean
    AclTargetPathBuilder aclTargetPathBuilder() {

        return new AclTargetPathBuilder();
    }

    @Bean
    PermissionBasicCheckerImpl permissionBasicChecker(
            AclConnectApiClient<HierarchicalAuthorizationClient> aclConnectApiClient,
            AclTargetPathBuilder aclTargetPathBuilder,
            CredentialBuilder credentialBuilder) {

        return new PermissionBasicCheckerImpl(aclConnectApiClient, aclTargetPathBuilder, credentialBuilder);
    }

    @Bean
    PermissionBearerCheckerImpl permissionBearerChecker(
            AclConnectApiClient<HierarchicalAuthorizationClient> aclConnectApiClient,
            AclTargetPathBuilder aclTargetPathBuilder) {

        return new PermissionBearerCheckerImpl(aclConnectApiClient, aclTargetPathBuilder);
    }
}

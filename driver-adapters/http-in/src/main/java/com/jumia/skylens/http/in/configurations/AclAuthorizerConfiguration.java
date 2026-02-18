package com.jumia.skylens.http.in.configurations;

import com.jumia.skylens.http.in.acl.authentication.checkers.impl.BasicAuthorizerImpl;
import com.jumia.skylens.http.in.acl.authentication.checkers.impl.BearerAuthorizerImpl;
import com.jumia.skylens.http.in.acl.credentials.CredentialBuilder;
import com.jumia.skylens.http.in.acl.permissions.checkers.impl.PermissionBasicCheckerImpl;
import com.jumia.skylens.http.in.acl.permissions.checkers.impl.PermissionBearerCheckerImpl;
import com.jumia.skylens.http.in.acl.tokens.factories.AuthTokenFactory;
import com.jumia.skylens.http.in.acl.tokens.factories.AuthTokenFactoryImpl;
import com.jumia.skylens.http.in.acl.utils.AuthenticationPatterns;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import pt.jumia.services.acl.lib.AclConnectApiClient;
import pt.jumia.services.acl.lib.client.authorization.DefaultAuthorizationClient;

import java.util.List;

@Configuration
@ConditionalOnProperty("app.acl-service.enabled")
public class AclAuthorizerConfiguration {

    private static final String LOGISTIC_PARTNERS_APPLICATION_CODE = "LogisticPartners";

    private static final String HMT_CODE = "HMT";

    @Bean
    @Primary
    AclConnectApiClient<DefaultAuthorizationClient> logisticPartnersConnectApiClient(AclApiBuilder aclApiBuilder) {

        return aclApiBuilder.buildAclConnectApiClient(LOGISTIC_PARTNERS_APPLICATION_CODE);
    }

    @Bean
    AclConnectApiClient<DefaultAuthorizationClient> hmtConnectApiClient(AclApiBuilder aclApiBuilder) {

        return aclApiBuilder.buildAclConnectApiClient(HMT_CODE);
    }

    @Bean
    BasicAuthorizerImpl authBasicChecker(CredentialBuilder credentialBuilder,
                                         AclConnectApiClient<DefaultAuthorizationClient> aclConnectApiClient) {

        return new BasicAuthorizerImpl(credentialBuilder, aclConnectApiClient);
    }

    @Bean
    BearerAuthorizerImpl authBearerChecker(AclConnectApiClient<DefaultAuthorizationClient> aclConnectApiClient) {

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
    PermissionBasicCheckerImpl permissionBasicChecker(List<AclConnectApiClient<DefaultAuthorizationClient>> aclConnectApiClients,
                                                      CredentialBuilder credentialBuilder) {

        return new PermissionBasicCheckerImpl(aclConnectApiClients, credentialBuilder);
    }

    @Bean
    PermissionBearerCheckerImpl permissionBearerChecker(List<AclConnectApiClient<DefaultAuthorizationClient>> aclConnectApiClients) {

        return new PermissionBearerCheckerImpl(aclConnectApiClients);
    }
}

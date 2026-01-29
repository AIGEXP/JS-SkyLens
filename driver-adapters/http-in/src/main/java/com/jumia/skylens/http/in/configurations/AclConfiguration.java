package com.jumia.skylens.http.in.configurations;

import com.jumia.skylens.http.in.acl.AuthInstances;
import com.jumia.skylens.http.in.acl.LogoutExecutor;
import com.jumia.skylens.http.in.acl.LogoutExecutorImpl;
import com.jumia.skylens.http.in.acl.authentication.AclAuthenticator;
import com.jumia.skylens.http.in.acl.authentication.Authenticator;
import com.jumia.skylens.http.in.acl.authentication.Authenticators;
import com.jumia.skylens.http.in.acl.authentication.AuthenticatorsImpl;
import com.jumia.skylens.http.in.acl.credentials.CredentialBuilder;
import com.jumia.skylens.http.in.acl.utils.AuthenticationPatterns;
import com.jumia.skylens.http.in.acl.utils.TokenExtractor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pt.jumia.services.acl.lib.AclConnectApiClient;
import pt.jumia.services.acl.lib.client.authorization.HierarchicalAuthorizationClient;

import java.util.List;

@Configuration
@ConditionalOnProperty("app.acl-service.enabled")
public class AclConfiguration {

    @Bean
    Authenticator aclAuthenticator(AclConnectApiClient<HierarchicalAuthorizationClient> aclConnectApiClient, AuthInstances authInstances) {

        return new AclAuthenticator(aclConnectApiClient, authInstances);
    }

    @Bean
    AuthenticationPatterns authenticationPatterns() {

        return new AuthenticationPatterns();
    }

    @Bean
    Authenticators authenticators(List<Authenticator> authenticators) {

        return new AuthenticatorsImpl(authenticators);
    }

    @Bean
    CredentialBuilder credentialBuilder(TokenExtractor tokenExtractor) {

        return new CredentialBuilder(tokenExtractor);
    }

    @Bean
    LogoutExecutor logoutExecutor(AclConnectApiClient<HierarchicalAuthorizationClient> aclConnectApiClient) {

        return new LogoutExecutorImpl(aclConnectApiClient);
    }

    @Bean
    TokenExtractor tokenExtractor() {

        return new TokenExtractor();
    }
}

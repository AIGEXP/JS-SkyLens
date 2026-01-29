package com.jumia.skylens.http.in.configurations;

import com.jumia.skylens.http.in.acl.LogoutExecutor;
import com.jumia.skylens.http.in.acl.authentication.AuthToken;
import com.jumia.skylens.http.in.acl.authentication.Authenticators;
import com.jumia.skylens.http.in.acl.authentication.checkers.impl.disabled.DisabledAuthorizerImpl;
import com.jumia.skylens.http.in.acl.authentication.disabled.DisabledAuthenticatorImpl;
import com.jumia.skylens.http.in.acl.authentication.disabled.DisabledAuthenticatorsImpl;
import com.jumia.skylens.http.in.acl.tokens.factories.AuthTokenFactory;
import com.jumia.skylens.http.in.acl.tokens.factories.DisabledAuthTokenFactoryImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(value = "app.acl-service.enabled", havingValue = "false")
public class AclDisabledConfiguration {

    @Bean
    AuthTokenFactory authTokenFactory() {

        return new DisabledAuthTokenFactoryImpl();
    }

    @Bean
    Authenticators authenticators(DisabledAuthenticatorImpl disabledAuthenticator) {

        return new DisabledAuthenticatorsImpl(disabledAuthenticator);
    }

    @Bean
    DisabledAuthenticatorImpl disabledAuthenticator() {

        return new DisabledAuthenticatorImpl();
    }

    @Bean
    DisabledAuthorizerImpl disabledAuthorizer() {

        return new DisabledAuthorizerImpl();
    }

    @Bean
    LogoutExecutor logoutExecutor() {

        return AuthToken::getToken;
    }
}

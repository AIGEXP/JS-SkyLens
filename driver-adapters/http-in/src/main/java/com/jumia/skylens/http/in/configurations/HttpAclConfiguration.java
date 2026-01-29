package com.jumia.skylens.http.in.configurations;

import com.jumia.skylens.http.in.acl.services.GetAuthTokenService;
import com.jumia.skylens.http.in.acl.services.GetAuthTokenServiceImpl;
import com.jumia.skylens.http.in.acl.tokens.factories.AuthTokenFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpAclConfiguration {

    @Bean
    GetAuthTokenService getAuthTokenUseCase(AuthTokenFactory authTokenFactory) {

        return new GetAuthTokenServiceImpl(authTokenFactory);
    }
}

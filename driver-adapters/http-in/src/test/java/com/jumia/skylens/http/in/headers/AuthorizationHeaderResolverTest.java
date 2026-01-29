package com.jumia.skylens.http.in.headers;

import com.jumia.skylens.http.in.acl.authentication.AuthToken;
import com.jumia.skylens.http.in.acl.services.GetAuthTokenService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(AuthorizationHeaderResolverTest.TestController.class)
@ContextConfiguration(classes = {
        AuthorizationHeaderResolverTest.TestApplication.class,
        AuthorizationHeaderResolverTest.TestController.class
})
class AuthorizationHeaderResolverTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private GetAuthTokenService getAuthTokenService;

    @Test
    void resolveArgument_whenHeaderIsPresent_callsUseCaseWithToken() throws Exception {

        // Given
        final String token = "Basic 123456";
        final MockHttpServletRequestBuilder requestBuilder = get("/test/endpoint")
                .header("Authorization", token);

        // When
        mvc.perform(requestBuilder);

        // Then
        verify(getAuthTokenService)
                .get(token);
    }

    @RestController
    @RequiredArgsConstructor
    static class TestController {

        private final GetAuthTokenService getAuthTokenService;

        @GetMapping("/test/endpoint")
        public void test(@AuthorizationHeader AuthToken authToken) {

        }
    }

    @SpringBootApplication
    @RequiredArgsConstructor
    static class TestApplication {

        @Configuration
        @RequiredArgsConstructor
        static class WebConfig implements WebMvcConfigurer {

            private final GetAuthTokenService getAuthTokenService;

            @Override
            public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {

                resolvers.add(new AuthorizationHeaderResolver(getAuthTokenService));
            }
        }
    }
}

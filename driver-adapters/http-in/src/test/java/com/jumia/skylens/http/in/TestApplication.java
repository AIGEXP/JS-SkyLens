package com.jumia.skylens.http.in;

import com.jumia.skylens.http.in.acl.authentication.AuthToken;
import com.jumia.skylens.http.in.headers.AuthorizationHeaderResolver;
import com.jumia.skylens.http.in.interceptors.WebHeadersLogContext;
import com.jumia.skylens.http.in.interceptors.WebHeadersLogContextImpl;
import lombok.RequiredArgsConstructor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@SpringBootApplication
public class TestApplication {

    public static final AuthToken TOKEN = Mockito.mock(AuthToken.class);

    @Configuration
    @RequiredArgsConstructor
    public static class WebConfig implements WebMvcConfigurer {

        @Lazy
        @Autowired
        private List<HandlerInterceptor> handlerInterceptors;

        @Override
        public void addInterceptors(final InterceptorRegistry registry) {

            handlerInterceptors.forEach(registry::addInterceptor);
            WebMvcConfigurer.super.addInterceptors(registry);
        }

        @Override
        public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {

            resolvers.add(new AuthorizationHeaderResolver(_ -> TOKEN));
        }

        @Bean
        WebHeadersLogContext webHeadersLogContext() {

            return new WebHeadersLogContextImpl();
        }
    }
}

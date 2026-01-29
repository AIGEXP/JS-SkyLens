package com.jumia.skylens.app.configurations.http.in;

import com.jumia.skylens.http.in.acl.services.GetAuthTokenService;
import com.jumia.skylens.http.in.headers.AuthorizationHeaderResolver;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@ConfigurationPropertiesScan(basePackages = "com.jumia.skylens")
@RequiredArgsConstructor
@SuppressFBWarnings(value = {"EI_EXPOSE_REP"}, justification = "I prefer to suppress these FindBugs warnings")
public class WebConfig implements WebMvcConfigurer {

    private final GetAuthTokenService getAuthTokenService;

    private final List<HandlerInterceptor> interceptors;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        interceptors.forEach(registry::addInterceptor);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {

        resolvers.add(new AuthorizationHeaderResolver(getAuthTokenService));
    }

    @Bean
    @ConditionalOnProperty(value = "app.cors.enabled", havingValue = "true")
    public FilterRegistrationBean<CorsFilter> corsFilter(CorsFilterRegistrationBeanCreator corsFilterRegistrationBeanCreator) {

        return corsFilterRegistrationBeanCreator.create();
    }
}

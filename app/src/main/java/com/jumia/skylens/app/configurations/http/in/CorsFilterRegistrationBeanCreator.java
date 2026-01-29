package com.jumia.skylens.app.configurations.http.in;

import com.jumia.skylens.app.configurations.properties.AppProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Map;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(value = "app.cors.enabled", havingValue = "true")
public class CorsFilterRegistrationBeanCreator {

    private final AppProperties appProperties;

    public FilterRegistrationBean<CorsFilter> create() {

        final FilterRegistrationBean<CorsFilter> corsFilterFilterRegistrationBean = new FilterRegistrationBean<>(getCorsFilter());
        corsFilterFilterRegistrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return corsFilterFilterRegistrationBean;
    }

    private CorsConfiguration getCorsConfiguration() {

        final CorsConfiguration config = new CorsConfiguration();
        final AppProperties.CorsProperties cors = appProperties.getCors();
        config.setAllowedHeaders(cors.getAllowedHeaders());
        config.setAllowedOrigins(cors.getAllowedOrigins());
        config.setAllowedMethods(cors.getAllowedMethods());
        config.setMaxAge(cors.getMaxAge().getSeconds());
        return config;
    }

    private CorsFilter getCorsFilter() {

        return new CorsFilter(getUrlBasedCorsConfigurationSource());
    }

    private UrlBasedCorsConfigurationSource getUrlBasedCorsConfigurationSource() {

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final AppProperties.CorsProperties cors = appProperties.getCors();

        source.setCorsConfigurations(Map.of(cors.getWebMapping(), getCorsConfiguration()));
        return source;
    }
}

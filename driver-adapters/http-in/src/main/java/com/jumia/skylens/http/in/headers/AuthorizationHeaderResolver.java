package com.jumia.skylens.http.in.headers;

import com.jumia.skylens.http.in.acl.authentication.AuthToken;
import com.jumia.skylens.http.in.acl.services.GetAuthTokenService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@RequiredArgsConstructor
public class AuthorizationHeaderResolver implements HandlerMethodArgumentResolver {

    private final GetAuthTokenService getAuthTokenService;

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {

        return methodParameter.getParameterAnnotation(AuthorizationHeader.class) != null;
    }

    @Override
    public AuthToken resolveArgument(@NonNull MethodParameter methodParameter,
                                     ModelAndViewContainer modelAndViewContainer,
                                     NativeWebRequest nativeWebRequest,
                                     WebDataBinderFactory webDataBinderFactory) {

        final HttpServletRequest request = (HttpServletRequest) nativeWebRequest.getNativeRequest();
        final String authorizationToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        return getAuthTokenService.get(authorizationToken);
    }
}

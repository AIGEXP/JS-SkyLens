package com.jumia.skylens.http.in.interceptors;

import com.jumia.skylens.commons.logging.LogContext;
import com.jumia.skylens.commons.logging.LoggingUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import pt.aig.aigx.loggingcontext.enums.CommunicationTypeEnum;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
@Order(Ordered.LOWEST_PRECEDENCE - 1)
public class LoggingInterceptor implements HandlerInterceptor {

    private static final String CORRELATION_ID_HEADER = "X-Correlation-ID";

    private static final String REQUEST_ID_HEADER = "X-Request-ID";

    private final WebHeadersLogContext webHeadersLogContext;

    @Override
    public boolean preHandle(HttpServletRequest req, @NonNull HttpServletResponse res, @NonNull Object handler) {

        final Predicate<String> headerValueNotNull = header -> req.getHeader(header) != null;
        final Predicate<String> headerNotNull = Objects::nonNull;

        final Map<String, String> extraHeaders = webHeadersLogContext.getHeaders().stream()
                .filter(headerNotNull.and(headerValueNotNull))
                .collect(Collectors.toMap(webHeadersLogContext::buildContextName, req::getHeader));

        final String correlationId = extractOrGenerateCorrelationId(req);

        LoggingUtils.setLogContext(LogContext.of("api",
                                                 null,
                                                 CommunicationTypeEnum.HTTP_IN.getCommunicationTypeValue(),
                                                 correlationId,
                                                 extraHeaders));

        return true;
    }

    private String extractOrGenerateCorrelationId(HttpServletRequest request) {

        return Optional.ofNullable(request.getHeader(CORRELATION_ID_HEADER))
                .or(() -> Optional.ofNullable(request.getHeader(REQUEST_ID_HEADER)))
                .orElseGet(LoggingUtils::generateCorrelationId);
    }

    @Override
    public void afterCompletion(@NonNull HttpServletRequest request,
                                @NonNull HttpServletResponse response,
                                @NonNull Object handler,
                                Exception ex) {

        LoggingUtils.clearLogContext();
    }
}

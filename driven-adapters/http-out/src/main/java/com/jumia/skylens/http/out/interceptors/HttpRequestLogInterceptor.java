package com.jumia.skylens.http.out.interceptors;

import com.jumia.skylens.commons.logging.LogContext;
import com.jumia.skylens.commons.logging.LoggingUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NullMarked;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;
import pt.aig.aigx.loggingcontext.dtos.HttpMessageLogDTO;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static pt.aig.aigx.loggingcontext.enums.CommunicationTypeEnum.HTTP_OUT;

@Slf4j
@RequiredArgsConstructor
public class HttpRequestLogInterceptor implements ClientHttpRequestInterceptor {

    private static final String REQUEST_MESSAGE = "Request message";

    private static final String RESPONSE_MESSAGE = "Response message";

    private static final String CORRELATION_ID_HEADER = "X-Correlation-ID";

    private static final Set<String> REDACTED_HEADERS = Set.of(HttpHeaders.AUTHORIZATION);

    private final String systemName;

    @Override
    public @NullMarked ClientHttpResponse intercept(HttpRequest request,
                                                    byte[] body,
                                                    ClientHttpRequestExecution execution) throws IOException {

        final String correlationId = LoggingUtils.getCorrelationId();

        Optional.ofNullable(correlationId)
                .ifPresent(id -> request.getHeaders().add(CORRELATION_ID_HEADER, id));

        final Map<String, String> previousContext = LoggingUtils.getCurrentContext();

        try {
            LoggingUtils.setLogContext(LogContext.of(systemName,
                                                     null,
                                                     HTTP_OUT.getCommunicationTypeValue(),
                                                     correlationId));

            logRequest(request, body);

            final ClientHttpResponse response = execution.execute(request, body);

            logResponse(request, response);

            return response;
        } finally {
            Optional.ofNullable(previousContext)
                    .ifPresentOrElse(LoggingUtils::setLogContext, LoggingUtils::clearLogContext);
        }
    }

    private void logRequest(HttpRequest request, byte[] body) {

        final Map<String, String> requestHeaders = getRequestHeaders(request);

        final HttpMessageLogDTO logDTO = new HttpMessageLogDTO(systemName,
                                                               request.getURI().toString(),
                                                               request.getMethod().toString(),
                                                               requestHeaders,
                                                               requestToString(body),
                                                               null);

        final String msg = logDTO.toString(log, REQUEST_MESSAGE, false);
        if (log.isDebugEnabled()) {
            log.debug(msg);
        } else {
            log.info(msg);
        }
    }

    private void logResponse(HttpRequest request, ClientHttpResponse response) throws IOException {

        final Map<String, String> requestHeaders = getRequestHeaders(request);

        final Map<String, String> responseHeaders = response.getHeaders().headerSet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                                          entry -> entry.getValue().toString()));

        final HttpMessageLogDTO logDTO = new HttpMessageLogDTO(systemName,
                                                               request.getURI().toString(),
                                                               request.getMethod().toString(),
                                                               requestHeaders,
                                                               null,
                                                               new HttpMessageLogDTO.ResponseDTO(null,
                                                                                                 responseHeaders,
                                                                                                 response.getStatusCode().value()));

        if (log.isDebugEnabled()) {
            final String body = requestToString(StreamUtils.copyToByteArray(response.getBody()));
            logDTO.setBody(body);
            logDTO.getResponse().setBody(body);

            log.debug(logDTO.toString(log, RESPONSE_MESSAGE, false));
        } else {
            log.info(logDTO.toString(log, RESPONSE_MESSAGE, false));
        }
    }

    private String requestToString(byte[] body) {

        return new String(body, StandardCharsets.UTF_8);
    }

    private Map<String, String> getRequestHeaders(final HttpRequest request) {

        return request.getHeaders().headerSet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                                          entry -> !REDACTED_HEADERS.contains(entry.getKey())
                                                  ? entry.getValue().toString()
                                                  : "REDACTED"));
    }
}

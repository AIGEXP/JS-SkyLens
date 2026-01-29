package com.jumia.skylens.http.in.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.MDC;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class LoggingInterceptorTest {

    @Mock
    private WebHeadersLogContext webHeadersLogContext;

    @InjectMocks
    private LoggingInterceptor subject;

    @AfterEach
    void after() {

        MDC.clear();
    }

    @Test
    void preHandle_whenNullHeader_thenDoesNotThrowNullPointerException() {

        // Given
        final String extraHeader1 = "test1";
        final String extraHeader2 = "test2";
        final String headerValue = "header-value";
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);

        doReturn(List.of(extraHeader1, extraHeader2))
                .when(webHeadersLogContext)
                .getHeaders();

        doAnswer(AdditionalAnswers.returnsFirstArg())
                .when(webHeadersLogContext)
                .buildContextName(any());

        doReturn(null)
                .when(request)
                .getHeader(extraHeader1);

        doReturn(headerValue)
                .when(request)
                .getHeader(extraHeader2);

        // When
        subject.preHandle(request, response, new Object());

        final Map<String, String> expectedContext = Map.of(
                "communicationType", "http.in",
                "logContext", "api.all.http.in",
                "system", "api",
                "network", "all",
                extraHeader2, headerValue);

        // Then
        final Map<String, String> contextMap = MDC.getCopyOfContextMap();
        assertThat(contextMap)
                .containsAllEntriesOf(expectedContext);
    }

    @Test
    void afterCompletion_thenClearMdcContext() {

        // Given
        final String extraHeader1 = "test1";
        final String extraHeader2 = "test2";
        final String headerValue = "header-value";
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);

        doReturn(List.of(extraHeader1, extraHeader2))
                .when(webHeadersLogContext)
                .getHeaders();

        doAnswer(AdditionalAnswers.returnsFirstArg())
                .when(webHeadersLogContext)
                .buildContextName(any());

        doReturn(null)
                .when(request)
                .getHeader(extraHeader1);

        doReturn(headerValue)
                .when(request)
                .getHeader(extraHeader2);

        subject.preHandle(request, response, new Object());

        // When
        subject.afterCompletion(request, response, new Object(), new RuntimeException());

        // Then
        final Map<String, String> contextMap = MDC.getCopyOfContextMap();
        assertThat(contextMap)
                .isNullOrEmpty();
    }
}

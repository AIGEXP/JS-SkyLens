package com.jumia.skylens.http.out.skynet.impl;

import com.jumia.skylens.domain.catalog.ServiceProvider;
import com.jumia.skylens.http.out.skynet.configurations.SkyNetProperties;
import com.jumia.skylens.http.out.skynet.configurations.SkyNetTestConfiguration;
import com.jumia.skylens.http.out.skynet.responses.ServiceProviderResponse;
import com.jumia.skylens.ports.http.out.api.countries.ServiceProviderFetcher;
import com.jumia.skylens.ports.http.out.api.exceptions.HttpBadRequestException;
import com.jumia.skylens.ports.http.out.api.exceptions.HttpInternalServerErrorException;
import com.jumia.skylens.test.testcontainers.MockServerContainerSingleton;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import jakarta.annotation.Resource;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockserver.client.MockServerClient;
import org.mockserver.model.MediaType;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import tools.jackson.databind.json.JsonMapper;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.verify.VerificationTimes.exactly;

@Testcontainers
@ExtendWith(SpringExtension.class)
@Import(SkyNetTestConfiguration.class)
class ServiceProviderFetcherImplTest {

    @Container
    static final MockServerContainerSingleton MOCK_SERVER = MockServerContainerSingleton.getInstance();

    private static MockServerClient mockServerClient;

    @Resource
    private SkyNetProperties skyNetProperties;

    @Resource
    private CircuitBreaker skyNetCircuitBreaker;

    @Resource
    private ServiceProviderFetcher subject;

    private final JsonMapper jsonMapper = new JsonMapper();

    @BeforeAll
    static void setup() {

        mockServerClient = new MockServerClient(MOCK_SERVER.getHost(), MOCK_SERVER.getServerPort());
    }

    @BeforeEach
    void setupBeforeEach() {

        mockServerClient.reset();
        skyNetCircuitBreaker.transitionToClosedState();
    }

    @Test
    void get_whenCalledWithCorrectParams_thenCallSkyNetSuccessfully() {

        // Given
        final UUID serviceProviderSid = UUID.randomUUID();
        final String countryCode = "NG";
        final String path = "/api/v1/service-providers/sid/" + serviceProviderSid;

        final ServiceProviderResponse.Network network = new ServiceProviderResponse.Network(countryCode);
        final ServiceProviderResponse serviceProviderResponse = new ServiceProviderResponse(network);

        mockServerClient
                .when(request().withPath(path))
                .respond(response()
                                 .withContentType(MediaType.APPLICATION_JSON)
                                 .withBody(jsonMapper.writeValueAsString(serviceProviderResponse)));

        // When
        final ServiceProvider serviceProvider = subject.get(serviceProviderSid);

        // Then
        mockServerClient.verify(request()
                                        .withMethod("GET")
                                        .withPath(path)
                                        .withHeader(HttpHeaders.AUTHORIZATION, skyNetProperties.authorizationToken()));

        assertThat(serviceProvider)
                .usingRecursiveComparison()
                .isEqualTo(new ServiceProvider(countryCode));
    }

    @Test
    void get_when5xxResponse_thenRetryAndThrowException() {

        // Given
        final UUID serviceProviderSid = UUID.randomUUID();
        final String path = "/api/v1/service-providers/sid/" + serviceProviderSid;

        mockServerClient
                .when(request().withPath(path))
                .respond(response().withStatusCode(500));

        // When
        final ThrowableAssert.ThrowingCallable throwable = () -> subject.get(serviceProviderSid);

        // Then
        assertThatThrownBy(throwable)
                .isInstanceOf(HttpInternalServerErrorException.class);

        mockServerClient.verify(request().withPath(path), exactly(3));
    }

    @Test
    void get_when4xxResponse_thenDoNotRetry() {

        // Given
        final UUID serviceProviderSid = UUID.randomUUID();
        final String path = "/api/v1/service-providers/sid/" + serviceProviderSid;

        mockServerClient
                .when(request().withPath(path))
                .respond(response().withStatusCode(400));

        // When
        final ThrowableAssert.ThrowingCallable throwable = () -> subject.get(serviceProviderSid);

        // Then
        assertThatThrownBy(throwable)
                .isInstanceOf(HttpBadRequestException.class);

        mockServerClient.verify(request().withPath(path), exactly(1));
    }

    @Test
    void get_whenCircuitBreakerOpen_thenFailsFast() {

        // Given
        final UUID serviceProviderSid = UUID.randomUUID();

        skyNetCircuitBreaker.transitionToOpenState();

        // When
        final ThrowableAssert.ThrowingCallable throwable = () -> subject.get(serviceProviderSid);

        // Then
        assertThatThrownBy(throwable)
                .isInstanceOf(CallNotPermittedException.class);

        mockServerClient.verifyZeroInteractions();
    }
}

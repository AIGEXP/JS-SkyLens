package com.jumia.skylens.http.in.acl.services;

import com.jumia.skylens.http.in.acl.authentication.AuthToken;
import com.jumia.skylens.http.in.acl.tokens.factories.AuthTokenFactory;
import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class GetAuthTokenServiceImplTest {

    private final Faker faker = new Faker();

    @Mock
    private AuthTokenFactory authTokenFactory;

    @InjectMocks
    private GetAuthTokenServiceImpl subject;

    @Test
    void get_callsAuthTokenFactory() {

        // Given
        final String token = faker.artist().name();

        final AuthToken authToken = Mockito.mock(AuthToken.class);
        doReturn(authToken)
                .when(authTokenFactory)
                .create(eq(token));

        // When
        final AuthToken result = subject.get(token);

        // Then
        assertThat(result)
                .isEqualTo(authToken);
    }
}

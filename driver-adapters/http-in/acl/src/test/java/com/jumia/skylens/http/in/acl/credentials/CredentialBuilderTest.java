package com.jumia.skylens.http.in.acl.credentials;

import com.jumia.skylens.http.in.acl.utils.TokenExtractor;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.charset.Charset;
import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This is an integration test that tests the {@link CredentialBuilder}, {@link TokenExtractor} and {@link BasicCredentialImpl},
 * since they only makes sense to be tested together.
 */
@ExtendWith(MockitoExtension.class)
class CredentialBuilderTest {

    private final Faker faker = new Faker();

    private final TokenExtractor tokenExtractor = new TokenExtractor();

    private CredentialBuilder subject;

    @Test
    void ofRawToken_returnsToken() {

        // Given
        final String username = faker.pokemon().name();
        final String password = faker.harryPotter().character();

        final String decodedToken = username + ":" + password;
        final String token = new String(Base64.getEncoder().encode(decodedToken.getBytes(Charset.defaultCharset())),
                                        Charset.defaultCharset());

        // When
        final BasicCredential result = subject.ofBasicRawToken("Basic " + token);

        // Then
        assertThat(result.getUsername()).isEqualTo(username);
        assertThat(result.getPassword()).isEqualTo(password.toCharArray());
    }

    @BeforeEach
    void setUp() {

        subject = new CredentialBuilder(tokenExtractor);
    }
}

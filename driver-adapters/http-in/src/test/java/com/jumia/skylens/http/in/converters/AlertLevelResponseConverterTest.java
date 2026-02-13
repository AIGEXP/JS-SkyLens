package com.jumia.skylens.http.in.converters;

import com.jumia.skylens.domain.catalog.AlertLevel;
import com.jumia.skylens.http.in.model.AlertLevelResponse;
import com.jumia.skylens.test.fakers.DomainFaker;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AlertLevelResponseConverterTest {

    private final DomainFaker faker = new DomainFaker();

    private final AlertLevelResponseConverter subject = new AlertLevelResponseConverterImpl();

    @Test
    void convert_whenCalled_convertSuccessfully() {

        // Given
        final AlertLevel alertLevel = faker.alertLevel().build();

        // When
        final AlertLevelResponse alertLevelResponse = subject.convert(alertLevel);

        // Then
        assertThat(alertLevelResponse)
                .usingRecursiveComparison()
                .isEqualTo(AlertLevelResponse.builder()
                                   .criticalValue(alertLevel.criticalValue().doubleValue())
                                   .warningValue(alertLevel.warningValue().doubleValue())
                                   .build());
    }
}

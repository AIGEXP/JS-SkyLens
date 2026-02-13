package com.jumia.skylens.domain.impl;

import com.jumia.skylens.domain.catalog.AlertLevel;
import com.jumia.skylens.domain.catalog.exceptions.InvalidAlertLevelException;
import com.jumia.skylens.persistence.api.AlertLevelDAO;
import com.jumia.skylens.test.fakers.DomainFaker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SaveAlertLevelUseCaseImplTest {

    private final DomainFaker faker = new DomainFaker();

    @Mock
    private AlertLevelDAO alertLevelDAO;

    @InjectMocks
    private SaveAlertLevelUseCaseImpl subject;

    @Test
    void run_whenValidAlertLevel_thenCallDAOAndReturnResult() {

        // Given
        final AlertLevel alertLevel = faker.alertLevel().build();
        final AlertLevel saved = faker.alertLevel().build();

        when(alertLevelDAO.save(alertLevel)).thenReturn(saved);

        // When
        final AlertLevel result = subject.run(alertLevel);

        // Then
        assertThat(result).isEqualTo(saved);
    }

    @Test
    void run_whenCountryIsNull_thenThrowException() {

        // Given
        final AlertLevel alertLevel = faker.alertLevel().country(null).build();

        // When / Then
        assertThatThrownBy(() -> subject.run(alertLevel))
                .isInstanceOf(InvalidAlertLevelException.class)
                .hasMessageContaining("Country");

        verifyNoInteractions(alertLevelDAO);
    }

    @Test
    void run_whenCountryIsBlank_thenThrowException() {

        // Given
        final AlertLevel alertLevel = faker.alertLevel().country("  ").build();

        // When / Then
        assertThatThrownBy(() -> subject.run(alertLevel))
                .isInstanceOf(InvalidAlertLevelException.class)
                .hasMessageContaining("Country");

        verifyNoInteractions(alertLevelDAO);
    }

    @Test
    void run_whenReportTypeIsNull_thenThrowException() {

        // Given
        final AlertLevel alertLevel = faker.alertLevel().reportType(null).build();

        // When / Then
        assertThatThrownBy(() -> subject.run(alertLevel))
                .isInstanceOf(InvalidAlertLevelException.class)
                .hasMessageContaining("Report type");

        verifyNoInteractions(alertLevelDAO);
    }

    @Test
    void run_whenWarningIsNull_thenThrowException() {

        // Given
        final AlertLevel alertLevel = faker.alertLevel().warningValue(null).build();

        // When / Then
        assertThatThrownBy(() -> subject.run(alertLevel))
                .isInstanceOf(InvalidAlertLevelException.class)
                .hasMessageContaining("Warning");

        verifyNoInteractions(alertLevelDAO);
    }

    @Test
    void run_whenWarningIsNegative_thenThrowException() {

        // Given
        final AlertLevel alertLevel = faker.alertLevel().warningValue(BigDecimal.valueOf(-0.01)).build();

        // When / Then
        assertThatThrownBy(() -> subject.run(alertLevel))
                .isInstanceOf(InvalidAlertLevelException.class)
                .hasMessageContaining("Warning");

        verifyNoInteractions(alertLevelDAO);
    }

    @Test
    void run_whenWarningIsAboveOne_thenThrowException() {

        // Given
        final AlertLevel alertLevel = faker.alertLevel().warningValue(BigDecimal.valueOf(1.01)).build();

        // When / Then
        assertThatThrownBy(() -> subject.run(alertLevel))
                .isInstanceOf(InvalidAlertLevelException.class)
                .hasMessageContaining("Warning");

        verifyNoInteractions(alertLevelDAO);
    }

    @Test
    void run_whenCriticalIsNull_thenThrowException() {

        // Given
        final AlertLevel alertLevel = faker.alertLevel().criticalValue(null).build();

        // When / Then
        assertThatThrownBy(() -> subject.run(alertLevel))
                .isInstanceOf(InvalidAlertLevelException.class)
                .hasMessageContaining("Critical");

        verifyNoInteractions(alertLevelDAO);
    }

    @Test
    void run_whenCriticalIsNegative_thenThrowException() {

        // Given
        final AlertLevel alertLevel = faker.alertLevel().criticalValue(BigDecimal.valueOf(-0.01)).build();

        // When / Then
        assertThatThrownBy(() -> subject.run(alertLevel))
                .isInstanceOf(InvalidAlertLevelException.class)
                .hasMessageContaining("Critical");

        verifyNoInteractions(alertLevelDAO);
    }

    @Test
    void run_whenCriticalIsAboveOne_thenThrowException() {

        // Given
        final AlertLevel alertLevel = faker.alertLevel().criticalValue(BigDecimal.valueOf(1.01)).build();

        // When / Then
        assertThatThrownBy(() -> subject.run(alertLevel))
                .isInstanceOf(InvalidAlertLevelException.class)
                .hasMessageContaining("Critical");

        verifyNoInteractions(alertLevelDAO);
    }

    @Test
    void run_whenWarningEqualsCritical_thenThrowException() {

        // Given
        final AlertLevel alertLevel = faker.alertLevel()
                .warningValue(BigDecimal.valueOf(0.50))
                .criticalValue(BigDecimal.valueOf(0.50))
                .build();

        // When / Then
        assertThatThrownBy(() -> subject.run(alertLevel))
                .isInstanceOf(InvalidAlertLevelException.class)
                .hasMessageContaining("Warning must be strictly greater than criticalValue");

        verifyNoInteractions(alertLevelDAO);
    }

    @Test
    void run_whenWarningLessThanCritical_thenThrowException() {

        // Given
        final AlertLevel alertLevel = faker.alertLevel()
                .warningValue(BigDecimal.valueOf(0.30))
                .criticalValue(BigDecimal.valueOf(0.70))
                .build();

        // When / Then
        assertThatThrownBy(() -> subject.run(alertLevel))
                .isInstanceOf(InvalidAlertLevelException.class)
                .hasMessageContaining("Warning must be strictly greater than criticalValue");

        verifyNoInteractions(alertLevelDAO);
    }

    @Test
    void run_whenWarningIsOneAndCriticalIsZero_thenSucceed() {

        // Given
        final AlertLevel alertLevel = faker.alertLevel()
                .warningValue(BigDecimal.ONE)
                .criticalValue(BigDecimal.ZERO)
                .build();
        final AlertLevel saved = faker.alertLevel().build();

        when(alertLevelDAO.save(alertLevel)).thenReturn(saved);

        // When
        final AlertLevel result = subject.run(alertLevel);

        // Then
        assertThat(result).isEqualTo(saved);
    }
}

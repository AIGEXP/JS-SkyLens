package com.jumia.skylens.domain.impl;

import com.jumia.skylens.domain.catalog.Boundary;
import com.jumia.skylens.domain.catalog.exceptions.InvalidBoundaryException;
import com.jumia.skylens.persistence.api.BoundaryDAO;
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
class SaveBoundaryUseCaseImplTest {

    private final DomainFaker faker = new DomainFaker();

    @Mock
    private BoundaryDAO boundaryDAO;

    @InjectMocks
    private SaveBoundaryUseCaseImpl subject;

    @Test
    void run_whenValidBoundary_thenCallDAOAndReturnResult() {

        // Given
        final Boundary boundary = faker.boundary().build();
        final Boundary saved = faker.boundary().build();

        when(boundaryDAO.save(boundary)).thenReturn(saved);

        // When
        final Boundary result = subject.run(boundary);

        // Then
        assertThat(result).isEqualTo(saved);
    }

    @Test
    void run_whenCountryIsNull_thenThrowException() {

        // Given
        final Boundary boundary = faker.boundary().country(null).build();

        // When / Then
        assertThatThrownBy(() -> subject.run(boundary))
                .isInstanceOf(InvalidBoundaryException.class)
                .hasMessageContaining("Country");

        verifyNoInteractions(boundaryDAO);
    }

    @Test
    void run_whenCountryIsBlank_thenThrowException() {

        // Given
        final Boundary boundary = faker.boundary().country("  ").build();

        // When / Then
        assertThatThrownBy(() -> subject.run(boundary))
                .isInstanceOf(InvalidBoundaryException.class)
                .hasMessageContaining("Country");

        verifyNoInteractions(boundaryDAO);
    }

    @Test
    void run_whenReportTypeIsNull_thenThrowException() {

        // Given
        final Boundary boundary = faker.boundary().reportType(null).build();

        // When / Then
        assertThatThrownBy(() -> subject.run(boundary))
                .isInstanceOf(InvalidBoundaryException.class)
                .hasMessageContaining("Report type");

        verifyNoInteractions(boundaryDAO);
    }

    @Test
    void run_whenWarningIsNull_thenThrowException() {

        // Given
        final Boundary boundary = faker.boundary().warning(null).build();

        // When / Then
        assertThatThrownBy(() -> subject.run(boundary))
                .isInstanceOf(InvalidBoundaryException.class)
                .hasMessageContaining("Warning");

        verifyNoInteractions(boundaryDAO);
    }

    @Test
    void run_whenWarningIsNegative_thenThrowException() {

        // Given
        final Boundary boundary = faker.boundary().warning(BigDecimal.valueOf(-0.01)).build();

        // When / Then
        assertThatThrownBy(() -> subject.run(boundary))
                .isInstanceOf(InvalidBoundaryException.class)
                .hasMessageContaining("Warning");

        verifyNoInteractions(boundaryDAO);
    }

    @Test
    void run_whenWarningIsAboveOne_thenThrowException() {

        // Given
        final Boundary boundary = faker.boundary().warning(BigDecimal.valueOf(1.01)).build();

        // When / Then
        assertThatThrownBy(() -> subject.run(boundary))
                .isInstanceOf(InvalidBoundaryException.class)
                .hasMessageContaining("Warning");

        verifyNoInteractions(boundaryDAO);
    }

    @Test
    void run_whenCriticalIsNull_thenThrowException() {

        // Given
        final Boundary boundary = faker.boundary().critical(null).build();

        // When / Then
        assertThatThrownBy(() -> subject.run(boundary))
                .isInstanceOf(InvalidBoundaryException.class)
                .hasMessageContaining("Critical");

        verifyNoInteractions(boundaryDAO);
    }

    @Test
    void run_whenCriticalIsNegative_thenThrowException() {

        // Given
        final Boundary boundary = faker.boundary().critical(BigDecimal.valueOf(-0.01)).build();

        // When / Then
        assertThatThrownBy(() -> subject.run(boundary))
                .isInstanceOf(InvalidBoundaryException.class)
                .hasMessageContaining("Critical");

        verifyNoInteractions(boundaryDAO);
    }

    @Test
    void run_whenCriticalIsAboveOne_thenThrowException() {

        // Given
        final Boundary boundary = faker.boundary().critical(BigDecimal.valueOf(1.01)).build();

        // When / Then
        assertThatThrownBy(() -> subject.run(boundary))
                .isInstanceOf(InvalidBoundaryException.class)
                .hasMessageContaining("Critical");

        verifyNoInteractions(boundaryDAO);
    }

    @Test
    void run_whenWarningEqualsCritical_thenThrowException() {

        // Given
        final Boundary boundary = faker.boundary()
                .warning(BigDecimal.valueOf(0.50))
                .critical(BigDecimal.valueOf(0.50))
                .build();

        // When / Then
        assertThatThrownBy(() -> subject.run(boundary))
                .isInstanceOf(InvalidBoundaryException.class)
                .hasMessageContaining("Warning must be strictly greater than critical");

        verifyNoInteractions(boundaryDAO);
    }

    @Test
    void run_whenWarningLessThanCritical_thenThrowException() {

        // Given
        final Boundary boundary = faker.boundary()
                .warning(BigDecimal.valueOf(0.30))
                .critical(BigDecimal.valueOf(0.70))
                .build();

        // When / Then
        assertThatThrownBy(() -> subject.run(boundary))
                .isInstanceOf(InvalidBoundaryException.class)
                .hasMessageContaining("Warning must be strictly greater than critical");

        verifyNoInteractions(boundaryDAO);
    }

    @Test
    void run_whenWarningIsOneAndCriticalIsZero_thenSucceed() {

        // Given
        final Boundary boundary = faker.boundary()
                .warning(BigDecimal.ONE)
                .critical(BigDecimal.ZERO)
                .build();
        final Boundary saved = faker.boundary().build();

        when(boundaryDAO.save(boundary)).thenReturn(saved);

        // When
        final Boundary result = subject.run(boundary);

        // Then
        assertThat(result).isEqualTo(saved);
    }
}

package com.jumia.skylens.http.in.services;

import com.jumia.skylens.domain.ListPackagesUseCase;
import com.jumia.skylens.domain.catalog.PackageFilter;
import com.jumia.skylens.domain.catalog.PackageSummary;
import com.jumia.skylens.domain.catalog.pages.PageRequest;
import com.jumia.skylens.http.in.converters.PackageFilterConverter;
import com.jumia.skylens.http.in.converters.PackageResponseInnerConverter;
import com.jumia.skylens.http.in.converters.paginations.PageRequestConverter;
import com.jumia.skylens.http.in.model.PackageFilterRequest;
import com.jumia.skylens.http.in.model.PackageResponseInner;
import com.jumia.skylens.test.fakers.DomainFaker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PackageServiceTest {

    private final DomainFaker faker = new DomainFaker();

    @Mock
    private ListPackagesUseCase listPackagesUseCase;

    @Mock
    private PackageResponseInnerConverter packageResponseInnerConverter;

    @Mock
    private PackageFilterConverter packageFilterConverter;

    @Mock
    private PageRequestConverter pageRequestConverter;

    @InjectMocks
    private PackageService subject;

    @Test
    void listPackages_whenPartnerExists_thenReturnPackagesSummary() {

        // Given
        final PackageFilter packageFilter = faker.packageFilter().build();
        final PageRequest pageRequest = PageRequest.of(0, 10);
        final PackageFilterRequest packageFilterRequest = mock(PackageFilterRequest.class);

        final PackageSummary packageSummary1 = faker.packageSummary().build();
        final PackageSummary packageSummary2 = faker.packageSummary().build();
        final List<PackageSummary> packageSummaries = List.of(packageSummary1, packageSummary2);

        final PackageResponseInner packageResponse1 = PackageResponseInner.builder()
                .stopId(packageSummary1.stopId())
                .build();

        final PackageResponseInner packageResponse2 = PackageResponseInner.builder()
                .stopId(packageSummary2.stopId())
                .build();

        when(listPackagesUseCase.run(packageFilter, pageRequest)).thenReturn(packageSummaries);
        when(packageFilterConverter.convert(any(), any())).thenReturn(packageFilter);
        when(pageRequestConverter.convert(any(), any())).thenReturn(pageRequest);
        when(packageResponseInnerConverter.convert(packageSummary1)).thenReturn(packageResponse1);
        when(packageResponseInnerConverter.convert(packageSummary2)).thenReturn(packageResponse2);

        // When
        final List<PackageResponseInner> result = subject.listPackages(packageFilter.partnerSid(), packageFilterRequest);

        // Then
        assertThat(result)
                .isNotNull()
                .hasSize(2)
                .containsExactly(packageResponse1, packageResponse2);

        verify(listPackagesUseCase).run(packageFilter, pageRequest);
        verify(packageResponseInnerConverter, times(2)).convert(any(PackageSummary.class));
        verify(packageFilterConverter).convert(any(PackageFilterRequest.class), any(UUID.class));
        verify(pageRequestConverter).convert(any(Integer.class), any(Integer.class));
    }
}

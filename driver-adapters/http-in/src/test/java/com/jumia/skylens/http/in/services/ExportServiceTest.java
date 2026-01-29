package com.jumia.skylens.http.in.services;

import com.jumia.skylens.domain.ExportAndPublishPackageUseCase;
import com.jumia.skylens.domain.catalog.PackageFilter;
import com.jumia.skylens.domain.catalog.pages.PageRequest;
import com.jumia.skylens.http.in.converters.PackageFilterConverter;
import com.jumia.skylens.http.in.converters.paginations.PageRequestConverter;
import com.jumia.skylens.http.in.model.PackageExportRequest;
import com.jumia.skylens.http.in.model.PackageFilterRequest;
import com.jumia.skylens.test.fakers.DomainFaker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExportServiceTest {

    private final DomainFaker faker = new DomainFaker();

    @Mock
    private PackageFilterConverter packageFilterConverter;

    @Mock
    private PageRequestConverter pageRequestConverter;

    @Mock
    private ExportAndPublishPackageUseCase exportAndPublishPackageUseCase;

    @InjectMocks
    private ExportService subject;

    @Test
    void exportPackages_whenParametersIsApplied_thenExportAndPublishIsCalled() {

        // Given
        final UUID partnerSid = UUID.randomUUID();
        final String uploadTo = "https://s3.amazonaws.com/bucket/export.csv";
        final PackageFilter packageFilter = faker.packageFilter().build();
        final PageRequest pageRequest = PageRequest.of(0, 10);
        final PackageFilterRequest filterRequest = mock(PackageFilterRequest.class);
        final PackageExportRequest packageExportRequest = mock(PackageExportRequest.class);

        when(packageExportRequest.getFilters()).thenReturn(filterRequest);
        when(packageExportRequest.getId()).thenReturn(456L);
        when(packageExportRequest.getUploadTo()).thenReturn(uploadTo);
        when(filterRequest.getLimit()).thenReturn(200);
        when(filterRequest.getOffset()).thenReturn(10);
        when(packageFilterConverter.convert(any(), any())).thenReturn(packageFilter);
        when(pageRequestConverter.convert(any(), any())).thenReturn(pageRequest);

        // When
        subject.exportPackages(partnerSid, packageExportRequest);

        // Then
        verify(packageFilterConverter).convert(filterRequest, partnerSid);
        verify(pageRequestConverter).convert(200, 10);
        verify(exportAndPublishPackageUseCase).run(partnerSid, 456L, uploadTo, packageFilter, pageRequest);
    }
}

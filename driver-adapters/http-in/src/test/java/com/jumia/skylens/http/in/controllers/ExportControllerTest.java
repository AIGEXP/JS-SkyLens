package com.jumia.skylens.http.in.controllers;

import com.jumia.skylens.http.in.model.PackageExportRequest;
import com.jumia.skylens.http.in.providers.InvalidExportPackageRequestProvider;
import com.jumia.skylens.http.in.services.ExportService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.ResultActions;

import java.util.UUID;

import static com.jumia.skylens.http.in.controllers.ExportOperationsApi.PATH_EXPORT_PACKAGES;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ExportController.class)
class ExportControllerTest extends BaseControllerTest {

    private final UUID partnerSid = UUID.randomUUID();

    @MockitoBean
    private ExportService exportService;

    @Test
    @SneakyThrows
    void exportPackages_whenValidRequest_thenReturn200() {

        // Given
        final PackageExportRequest request = restFaker.packageExportRequest().build();

        // When
        final ResultActions resultActions = mvc.perform(post(PATH_EXPORT_PACKAGES, partnerSid)
                                                                .contentType("application/json")
                                                                .content(asJsonString(request)));

        // Then
        resultActions.andExpect(status().isOk());
        verify(exportService).exportPackages(any(UUID.class), any(PackageExportRequest.class));
    }

    @Test
    @SneakyThrows
    void exportPackages_whenValidRequestWithoutFilters_thenReturn200() {

        // Given
        final PackageExportRequest request = restFaker.packageExportRequest()
                .filters(null)
                .build();

        // When
        final ResultActions resultActions = mvc.perform(post(PATH_EXPORT_PACKAGES, partnerSid)
                                                                .contentType("application/json")
                                                                .content(asJsonString(request)));

        // Then
        resultActions.andExpect(status().isOk());
        verify(exportService).exportPackages(partnerSid, request);
    }

    @ParameterizedTest
    @ArgumentsSource(InvalidExportPackageRequestProvider.class)
    @SneakyThrows
    void exportPackages_whenInvalidRequest_thenReturn400(final PackageExportRequest request) {

        // When
        final ResultActions resultActions = mvc.perform(post(PATH_EXPORT_PACKAGES, partnerSid)
                                                                .contentType("application/json")
                                                                .content(asJsonString(request)));

        // When
        // Then
        resultActions.andExpect(status().isBadRequest());
        verifyNoInteractions(exportService);
    }

    @Test
    @SneakyThrows
    void exportPackages_whenEmptyBody_thenReturn400() {

        // When
        final ResultActions resultActions = mvc.perform(post(PATH_EXPORT_PACKAGES, partnerSid)
                                                                .contentType("application/json")
                                                                .content("{}"));

        // When
        // Then
        resultActions.andExpect(status().isBadRequest());
        verifyNoInteractions(exportService);
    }

    @Test
    @SneakyThrows
    void exportPackages_whenInvalidJson_thenReturn400() {

        // When
        final ResultActions resultActions = mvc.perform(post(PATH_EXPORT_PACKAGES, partnerSid)
                                                                .contentType("application/json")
                                                                .content("invalid json"));

        // When
        // Then
        resultActions.andExpect(status().isBadRequest());
        verifyNoInteractions(exportService);
    }
}

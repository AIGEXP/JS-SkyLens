package com.jumia.skylens.http.in.controllers;

import com.jumia.skylens.http.in.model.BoundaryResponse;
import com.jumia.skylens.http.in.model.ReportType;
import com.jumia.skylens.http.in.services.BoundaryService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.ResultActions;

import static com.jumia.skylens.http.in.controllers.ConfigurationApi.PATH_SET_METRIC_BOUNDARIES;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ConfigurationController.class)
class ConfigurationControllerTest extends BaseControllerTest {

    @MockitoBean
    private BoundaryService boundaryService;

    @Test
    void setMetricBoundaries_whenValidRequest_thenReturn200() throws Exception {

        // Given
        final String country = "CI";
        final ReportType reportType = ReportType.SUCCESS_RATE;
        final BoundaryResponse boundaryResponse = restFaker.boundaryResponse().build();

        when(boundaryService.save(any(), any(), any())).thenReturn(boundaryResponse);

        // When
        final ResultActions resultActions = mvc.perform(put(PATH_SET_METRIC_BOUNDARIES, country, reportType)
                                                                .contentType(MediaType.APPLICATION_JSON)
                                                                .content("""
                                                                        {
                                                                            "warning": 0.90,
                                                                            "critical": 0.80
                                                                        }
                                                                        """));

        // Then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.warning").value(boundaryResponse.getWarning()))
                .andExpect(jsonPath("$.critical").value(boundaryResponse.getCritical()));

        verify(boundaryService).save(any(), any(), any());
    }

    @Test
    void setMetricBoundaries_whenMissingWarning_thenReturn400() throws Exception {

        // Given
        final String country = "CI";
        final ReportType reportType = ReportType.SUCCESS_RATE;

        // When
        final ResultActions resultActions = mvc.perform(put(PATH_SET_METRIC_BOUNDARIES, country, reportType)
                                                                .contentType(MediaType.APPLICATION_JSON)
                                                                .content("""
                                                                        {
                                                                            "critical": 0.80
                                                                        }
                                                                        """));

        // Then
        resultActions
                .andExpect(status().isBadRequest());

        verifyNoInteractions(boundaryService);
    }

    @Test
    void setMetricBoundaries_whenMissingCritical_thenReturn400() throws Exception {

        // Given
        final String country = "CI";
        final ReportType reportType = ReportType.SUCCESS_RATE;

        // When
        final ResultActions resultActions = mvc.perform(put(PATH_SET_METRIC_BOUNDARIES, country, reportType)
                                                                .contentType(MediaType.APPLICATION_JSON)
                                                                .content("""
                                                                        {
                                                                            "warning": 0.90
                                                                        }
                                                                        """));

        // Then
        resultActions
                .andExpect(status().isBadRequest());

        verifyNoInteractions(boundaryService);
    }

    @Test
    void setMetricBoundaries_whenInvalidReportType_thenReturn400() throws Exception {

        // When
        final ResultActions resultActions = mvc.perform(put("/api/v1/countries/CI/boundaries/INVALID_TYPE")
                                                                .contentType(MediaType.APPLICATION_JSON)
                                                                .content("""
                                                                        {
                                                                            "warning": 0.90,
                                                                            "critical": 0.80
                                                                        }
                                                                        """));

        // Then
        resultActions
                .andExpect(status().isBadRequest());

        verifyNoInteractions(boundaryService);
    }
}

package com.jumia.skylens.http.in.controllers;

import com.jumia.skylens.http.in.model.ReportType;
import com.jumia.skylens.http.in.model.ThresholdResponse;
import com.jumia.skylens.http.in.services.ConfigurationService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.ResultActions;

import static com.jumia.skylens.http.in.controllers.ConfigurationApi.PATH_SET_THRESHOLD_TARGET;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ConfigurationController.class)
class ConfigurationControllerTest extends BaseControllerTest {

    @MockitoBean
    private ConfigurationService configurationService;

    @Test
    void setThresholdTarget_whenCalled_thenReturn200() throws Exception {

        // Given
        final String country = "CI";
        final ReportType reportType = ReportType.SUCCESS_RATE;
        final ThresholdResponse thresholdResponse = restFaker.thresholdResponse().build();

        when(configurationService.setThresholdTarget(any(), any(), any())).thenReturn(thresholdResponse);

        // When
        final ResultActions resultActions = mvc.perform(put(PATH_SET_THRESHOLD_TARGET, country, reportType)
                                                                .contentType(MediaType.APPLICATION_JSON)
                                                                .content("{\"targetRate\": 0.85}"));

        // Then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*").isNotEmpty())
                .andExpect(jsonPath("$.targetRate").value(thresholdResponse.getTargetRate().doubleValue()))
                .andExpect(jsonPath("$.updatedAt").isNotEmpty());

        verify(configurationService).setThresholdTarget(any(), any(), any());
    }

    @Test
    void setThresholdTarget_whenMissingBody_thenReturn400() throws Exception {

        // Given
        final String country = "CI";
        final ReportType reportType = ReportType.SUCCESS_RATE;

        // When
        final ResultActions resultActions = mvc.perform(put(PATH_SET_THRESHOLD_TARGET, country, reportType)
                                                                .contentType(MediaType.APPLICATION_JSON));

        // Then
        resultActions
                .andExpect(status().isBadRequest());
    }
}

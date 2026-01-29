package com.jumia.skylens.http.in.controllers;

import com.jumia.skylens.http.in.model.StopPublishingRequest;
import com.jumia.skylens.http.in.services.StopService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.ResultActions;

import static com.jumia.skylens.http.in.controllers.StopManagementApi.PATH_PUBLISH_LAST_MILE_STOPS;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StopController.class)
class StopControllerTest extends BaseControllerTest {

    @MockitoBean
    private StopService stopService;

    @Test
    @SneakyThrows
    void publishLastMileStops_whenCalled_thenCallServiceSuccessfully() {

        // Given
        final StopPublishingRequest stopPublishingRequest = restFaker.stopPublishingRequest().build();

        // When
        final ResultActions resultActions = mvc.perform(post(PATH_PUBLISH_LAST_MILE_STOPS)
                                                                .contentType(MediaType.APPLICATION_JSON)
                                                                .content(asJsonString(stopPublishingRequest)));

        // Then
        resultActions
                .andExpect(status().isOk());

        verify(stopService).publishLastMileStops(stopPublishingRequest);
    }
}

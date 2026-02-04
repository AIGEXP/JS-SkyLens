package com.jumia.skylens.http.in.controllers;

import com.jumia.skylens.http.in.services.ReferenceDataService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.ResultActions;

import static com.jumia.skylens.http.in.controllers.ReferenceDataApi.PATH_GET_DATE_RANGES;
import static com.jumia.skylens.http.in.controllers.ReferenceDataApi.PATH_GET_PAYMENT_TYPES;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReferenceDataController.class)
class ReferenceDataControllerTest extends BaseControllerTest {

    @MockitoBean
    private ReferenceDataService referenceDataService;

    @Test
    void getDateRanges_whenCalled_thenReturns200AndDateRanges() throws Exception {

        // Given
        // When
        final ResultActions resultActions = mvc.perform(get(PATH_GET_DATE_RANGES));

        // Then
        resultActions.andExpect(status().isOk());
        verify(referenceDataService).listDateRanges();
    }

    @Test
    void getPaymentTypes_whenCalled_thenReturns200AndPaymentTypes() throws Exception {

        // Given
        // When
        final ResultActions resultActions = mvc.perform(get(PATH_GET_PAYMENT_TYPES));

        // Then
        resultActions.andExpect(status().isOk());
        verify(referenceDataService).listPaymentTypes();
    }
}

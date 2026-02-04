package com.jumia.skylens.http.in.controllers;

import com.jumia.skylens.http.in.services.ReferenceDataService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReferenceDataController.class)
class ReferenceDataControllerTest extends BaseControllerTest {

    private static final String DATE_RANGES_API = ReferenceDataApi.PATH_GET_DATE_RANGES;
    private static final String PAYMENT_TYPES_API = ReferenceDataApi.PATH_GET_PAYMENT_TYPES;

    @MockitoBean
    private ReferenceDataService referenceDataService;

    @Test
    void getDateRanges_whenCalled_thenReturns200AndDateRanges() throws Exception {

        // Given
        // When
        final ResultActions resultActions = mvc.perform(get(DATE_RANGES_API));

        // Then
        resultActions.andExpect(status().isOk());
        verify(referenceDataService).listDateRangeTypes();
    }

    @Test
    void getPaymentTypes_whenCalled_thenReturns200AndPaymentTypes() throws Exception {

        // Given
        // When
        final ResultActions resultActions = mvc.perform(get(PAYMENT_TYPES_API));

        // Then
        resultActions.andExpect(status().isOk());
        verify(referenceDataService).listPaymentTypes();
    }
}

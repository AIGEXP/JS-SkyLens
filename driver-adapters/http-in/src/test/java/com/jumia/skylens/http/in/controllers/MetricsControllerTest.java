package com.jumia.skylens.http.in.controllers;

import com.jumia.skylens.http.in.model.DateRange;
import com.jumia.skylens.http.in.model.DeliveryMetricsResponseInner;
import com.jumia.skylens.http.in.model.LossRateMetricsResponseInner;
import com.jumia.skylens.http.in.model.MovementType;
import com.jumia.skylens.http.in.model.NoAttemptsMetricsResponse;
import com.jumia.skylens.http.in.model.PaymentType;
import com.jumia.skylens.http.in.model.SuccessRateMetricsResponseDeprecatedInner;
import com.jumia.skylens.http.in.model.SuccessRateMetricsResponseInner;
import com.jumia.skylens.http.in.services.MetricsService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.UUID;

import static com.jumia.skylens.http.in.controllers.MetricsApi.PATH_GET_LOSS_RATE;
import static com.jumia.skylens.http.in.controllers.MetricsApi.PATH_GET_NO_ATTEMPTS;
import static com.jumia.skylens.http.in.controllers.MetricsApi.PATH_GET_PACKAGE_DELIVERIES;
import static com.jumia.skylens.http.in.controllers.MetricsApi.PATH_GET_SUCCESS_RATE;
import static com.jumia.skylens.http.in.controllers.MetricsApi.PATH_GET_SUCCESS_RATE_DEPRECATED;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MetricsController.class)
class MetricsControllerTest extends BaseControllerTest {

    @MockitoBean
    private MetricsService metricsService;

    @Test
    void getPackageDeliveries_whenDateRangeIsNull_thenReturn400() throws Exception {

        // Given
        final UUID serviceProviderSid = UUID.randomUUID();
        final UUID hubSid = UUID.randomUUID();
        final PaymentType paymentType = PaymentType.PRE;
        final MovementType movementType = MovementType.DOOR;

        // When
        final ResultActions resultActions = mvc.perform(get(PATH_GET_PACKAGE_DELIVERIES, serviceProviderSid)
                                                                .param("hubSid", hubSid.toString())
                                                                .param("paymentType", paymentType.toString())
                                                                .param("movementType", movementType.toString()));

        // Then
        resultActions
                .andExpect(status().isBadRequest());

        verifyNoInteractions(metricsService);
    }

    @Test
    void getPackageDeliveries_whenFiltersApplied_thenReturn200() throws Exception {

        // Given
        final UUID serviceProviderSid = UUID.randomUUID();
        final UUID hubSid = UUID.randomUUID();
        final DateRange dateRange = DateRange.CURRENT_WEEK;
        final PaymentType paymentType = PaymentType.PRE;
        final MovementType movementType = MovementType.DOOR;
        final DeliveryMetricsResponseInner deliveryMetricsResponseInner = restFaker.deliveryMetricsResponse().build();

        when(metricsService.getPackageDeliveries(any(), any(), any(), any(), any())).thenReturn(List.of(deliveryMetricsResponseInner));

        // When
        final ResultActions resultActions = mvc.perform(get(PATH_GET_PACKAGE_DELIVERIES, serviceProviderSid)
                                                                .param("hubSid", hubSid.toString())
                                                                .param("dateRange", dateRange.toString())
                                                                .param("paymentType", paymentType.toString())
                                                                .param("movementType", movementType.toString()));

        // Then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].*", hasSize(2)))
                .andExpect(jsonPath("$[0].date").value(deliveryMetricsResponseInner.getDate().toString()))
                .andExpect(jsonPath("$[0].packagesDelivered").value(deliveryMetricsResponseInner.getPackagesDelivered()));

        verify(metricsService).getPackageDeliveries(serviceProviderSid, dateRange, hubSid, paymentType, movementType);
    }

    @Test
    void getSuccessRateDeprecated_whenDateRangeIsNull_thenReturn400() throws Exception {

        // Given
        final UUID serviceProviderSid = UUID.randomUUID();
        final UUID hubSid = UUID.randomUUID();
        final PaymentType paymentType = PaymentType.PRE;
        final MovementType movementType = MovementType.DOOR;

        // When
        final ResultActions resultActions = mvc.perform(get(PATH_GET_SUCCESS_RATE_DEPRECATED, serviceProviderSid)
                                                                .param("hubSid", hubSid.toString())
                                                                .param("paymentType", paymentType.toString())
                                                                .param("movementType", movementType.toString()));

        // Then
        resultActions
                .andExpect(status().isBadRequest());

        verifyNoInteractions(metricsService);
    }

    @Test
    void getSuccessRateDeprecated_whenFiltersApplied_thenReturn200() throws Exception {

        // Given
        final UUID serviceProviderSid = UUID.randomUUID();
        final UUID hubSid = UUID.randomUUID();
        final DateRange dateRange = DateRange.CURRENT_WEEK;
        final PaymentType paymentType = PaymentType.PRE;
        final MovementType movementType = MovementType.DOOR;
        final SuccessRateMetricsResponseDeprecatedInner successRateMetricsResponseDeprecated = restFaker
                .successRateMetricsResponseDeprecated()
                .build();

        when(metricsService.getSuccessRateDeprecated(any(), any(), any(), any(), any())).thenReturn(List.of(
                successRateMetricsResponseDeprecated));

        // When
        final ResultActions resultActions = mvc.perform(get(PATH_GET_SUCCESS_RATE_DEPRECATED, serviceProviderSid)
                                                                .param("hubSid", hubSid.toString())
                                                                .param("dateRange", dateRange.toString())
                                                                .param("paymentType", paymentType.toString())
                                                                .param("movementType", movementType.toString()));

        // Then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].*", hasSize(4)))
                .andExpect(jsonPath("$[0].date").value(successRateMetricsResponseDeprecated.getDate().toString()))
                .andExpect(jsonPath("$[0].packagesDelivered").value(successRateMetricsResponseDeprecated.getPackagesDelivered()))
                .andExpect(jsonPath("$[0].packagesClosed").value(successRateMetricsResponseDeprecated.getPackagesClosed()))
                .andExpect(jsonPath("$[0].successRate").value(successRateMetricsResponseDeprecated.getSuccessRate()));

        verify(metricsService).getSuccessRateDeprecated(serviceProviderSid, dateRange, hubSid, paymentType, movementType);
    }

    @Test
    void getSuccessRate_whenDateRangeIsNull_thenReturn400() throws Exception {

        // Given
        final String country = "NG";
        final UUID serviceProviderSid = UUID.randomUUID();
        final UUID hubSid = UUID.randomUUID();
        final PaymentType paymentType = PaymentType.PRE;
        final MovementType movementType = MovementType.DOOR;

        // When
        final ResultActions resultActions = mvc.perform(get(PATH_GET_SUCCESS_RATE, country, serviceProviderSid)
                                                                .param("hubSid", hubSid.toString())
                                                                .param("paymentType", paymentType.toString())
                                                                .param("movementType", movementType.toString()));

        // Then
        resultActions
                .andExpect(status().isBadRequest());

        verifyNoInteractions(metricsService);
    }

    @Test
    void getSuccessRate_whenFiltersApplied_thenReturn200() throws Exception {

        // Given
        final String country = "NG";
        final UUID serviceProviderSid = UUID.randomUUID();
        final UUID hubSid = UUID.randomUUID();
        final DateRange dateRange = DateRange.CURRENT_WEEK;
        final PaymentType paymentType = PaymentType.PRE;
        final MovementType movementType = MovementType.DOOR;
        final SuccessRateMetricsResponseInner successRateMetricsResponse = restFaker.successRateMetricsResponse().build();

        when(metricsService.getSuccessRate(any(), any(), any(), any(), any(), any())).thenReturn(List.of(successRateMetricsResponse));

        // When
        final ResultActions resultActions = mvc.perform(get(PATH_GET_SUCCESS_RATE, country, serviceProviderSid)
                                                                .param("hubSid", hubSid.toString())
                                                                .param("dateRange", dateRange.toString())
                                                                .param("paymentType", paymentType.toString())
                                                                .param("movementType", movementType.toString()));

        // Then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].*", hasSize(6)))
                .andExpect(jsonPath("$[0].date").value(successRateMetricsResponse.getDate().toString()))
                .andExpect(jsonPath("$[0].packagesDelivered").value(successRateMetricsResponse.getPackagesDelivered()))
                .andExpect(jsonPath("$[0].packagesClosed").value(successRateMetricsResponse.getPackagesClosed()))
                .andExpect(jsonPath("$[0].successRate").value(successRateMetricsResponse.getSuccessRate()))
                .andExpect(jsonPath("$[0].isWarning").value(successRateMetricsResponse.getIsWarning()))
                .andExpect(jsonPath("$[0].isCritical").value(successRateMetricsResponse.getIsCritical()));

        verify(metricsService).getSuccessRate(country, serviceProviderSid, dateRange, hubSid, paymentType, movementType);
    }

    @Test
    void getLossRate_whenDateRangeIsNull_thenReturn400() throws Exception {

        // Given
        final UUID serviceProviderSid = UUID.randomUUID();
        final UUID hubSid = UUID.randomUUID();
        final PaymentType paymentType = PaymentType.PRE;
        final MovementType movementType = MovementType.DOOR;

        // When
        final ResultActions resultActions = mvc.perform(get(PATH_GET_LOSS_RATE, serviceProviderSid)
                                                                .param("hubSid", hubSid.toString())
                                                                .param("paymentType", paymentType.toString())
                                                                .param("movementType", movementType.toString()));

        // Then
        resultActions
                .andExpect(status().isBadRequest());

        verifyNoInteractions(metricsService);
    }

    @Test
    void getLossRate_whenFiltersApplied_thenReturn200() throws Exception {

        // Given
        final UUID serviceProviderSid = UUID.randomUUID();
        final UUID hubSid = UUID.randomUUID();
        final DateRange dateRange = DateRange.CURRENT_WEEK;
        final PaymentType paymentType = PaymentType.PRE;
        final MovementType movementType = MovementType.DOOR;
        final LossRateMetricsResponseInner lossRateMetricsResponseInner = restFaker.lossRateMetricsResponse().build();

        when(metricsService.getLossRate(any(), any(), any(), any(), any())).thenReturn(List.of(lossRateMetricsResponseInner));

        // When
        final ResultActions resultActions = mvc.perform(get(PATH_GET_LOSS_RATE, serviceProviderSid)
                                                                .param("hubSid", hubSid.toString())
                                                                .param("dateRange", dateRange.toString())
                                                                .param("paymentType", paymentType.toString())
                                                                .param("movementType", movementType.toString()));

        // Then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].*", hasSize(4)))
                .andExpect(jsonPath("$[0].date").value(lossRateMetricsResponseInner.getDate().toString()))
                .andExpect(jsonPath("$[0].packagesReceived").value(lossRateMetricsResponseInner.getPackagesReceived()))
                .andExpect(jsonPath("$[0].packagesLost").value(lossRateMetricsResponseInner.getPackagesLost()))
                .andExpect(jsonPath("$[0].lossRate").value(lossRateMetricsResponseInner.getLossRate()));

        verify(metricsService).getLossRate(serviceProviderSid, dateRange, hubSid, paymentType, movementType);
    }

    @Test
    void getNoAttempts_whenFiltersApplied_thenReturn200() throws Exception {

        // Given
        final UUID serviceProviderSid = UUID.randomUUID();
        final UUID hubSid = UUID.randomUUID();
        final PaymentType paymentType = PaymentType.PRE;
        final MovementType movementType = MovementType.DOOR;
        final NoAttemptsMetricsResponse noAttemptsMetricsResponse = restFaker.noAttemptsMetricsResponse().build();

        when(metricsService.getNoAttempts(any(), any(), any(), any())).thenReturn(noAttemptsMetricsResponse);

        // When
        final ResultActions resultActions = mvc.perform(get(PATH_GET_NO_ATTEMPTS, serviceProviderSid)
                                                                .param("hubSid", hubSid.toString())
                                                                .param("paymentType", paymentType.toString())
                                                                .param("movementType", movementType.toString()));

        // Then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(4)))
                .andExpect(jsonPath("$.oneDay").value(noAttemptsMetricsResponse.getOneDay()))
                .andExpect(jsonPath("$.twoDays").value(noAttemptsMetricsResponse.getTwoDays()))
                .andExpect(jsonPath("$.threeDays").value(noAttemptsMetricsResponse.getThreeDays()))
                .andExpect(jsonPath("$.overThreeDays").value(noAttemptsMetricsResponse.getOverThreeDays()));

        verify(metricsService).getNoAttempts(serviceProviderSid, hubSid, paymentType, movementType);
    }
}

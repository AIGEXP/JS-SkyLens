package com.jumia.skylens.http.in.controllers;

import com.jumia.skylens.http.in.model.PackageFilterRequest;
import com.jumia.skylens.http.in.model.PackageResponseInner;
import com.jumia.skylens.http.in.model.PackageResponseInnerDriver;
import com.jumia.skylens.http.in.model.PackageResponseInnerPaymentMethod;
import com.jumia.skylens.http.in.providers.InvalidListPackageStopProvider;
import com.jumia.skylens.http.in.services.PackageService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.jumia.skylens.http.in.controllers.PackageOperationsApi.PATH_LIST_PACKAGES;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PackageController.class)
class PackageControllerTest extends BaseControllerTest {

    private final UUID partnerSid = UUID.randomUUID();

    @MockitoBean
    private PackageService packageService;

    @ParameterizedTest
    @ArgumentsSource(InvalidListPackageStopProvider.class)
    @SneakyThrows
    void listStops_whenInvalidParams_thenReturn400(PackageFilterRequest filter) {

        // Given
        final LinkedMultiValueMap<String, String> requestParams = getRequestAsParams(filter);

        // When
        final ResultActions resultActions = mvc.perform(get(PATH_LIST_PACKAGES, partnerSid)
                                                                .params(requestParams));

        // Then
        resultActions.andExpect(status().isBadRequest());
        verifyNoInteractions(packageService);
    }

    @Test
    @SneakyThrows
    void listStops_whenFiltersApplied_thenReturn200() {

        // Given
        final PackageFilterRequest filter = restFaker.packageFilterRequest().build();
        final LinkedMultiValueMap<String, String> requestParams = getRequestAsParams(filter);
        final PackageResponseInner pkg = restFaker.packageResponse().build();

        when(packageService.listPackages(any(), any())).thenReturn(List.of(pkg));

        // When
        final ResultActions resultActions = mvc.perform(get(PATH_LIST_PACKAGES, partnerSid).params(requestParams));

        // Then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].*", hasSize(9)))
                .andExpect(jsonPath("$[0].trackingNumber").value(pkg.getTrackingNumber()))
                .andExpect(jsonPath("$[0].stopId").value(pkg.getStopId()))
                .andExpect(jsonPath("$[0].eventDate").value(pkg.getEventDate().toString()))
                .andExpect(jsonPath("$[0].size").value(pkg.getSize().name()))
                .andExpect(jsonPath("$[0].serviceCode").value(pkg.getServiceCode()))
                .andExpect(jsonPath("$[0].zone").value(pkg.getZone()))
                .andExpect(jsonPath("$[0].node.*", hasSize(1)))
                .andExpect(jsonPath("$[0].node.name").value(pkg.getNode().getName()))
                .andExpect(jsonPath("$[0].paymentMethod.*", hasSize(1)))
                .andExpect(jsonPath("$[0].paymentMethod.name").value(Optional.ofNullable(pkg.getPaymentMethod())
                                                                             .map(PackageResponseInnerPaymentMethod::getName)
                                                                             .orElse(null)))
                .andExpect(jsonPath("$[0].driver.*", hasSize(1)))
                .andExpect(jsonPath("$[0].driver.name").value(Optional.ofNullable(pkg.getDriver())
                                                                      .map(PackageResponseInnerDriver::getName)
                                                                      .orElse(null)));

        verify(packageService).listPackages(partnerSid, filter);
    }
}

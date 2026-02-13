package com.jumia.skylens.http.in.controllers;

import com.jumia.skylens.http.in.acl.authentication.AuthToken;
import com.jumia.skylens.http.in.acl.resources.ApplicationResource;
import com.jumia.skylens.http.in.model.ReportType;
import com.jumia.skylens.http.in.model.ThresholdRequest;
import com.jumia.skylens.http.in.model.ThresholdResponse;
import com.jumia.skylens.http.in.services.ConfigurationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ConfigurationController implements ConfigurationApi {

    private final ConfigurationService configurationService;

    @Override
    public ThresholdResponse setThresholdTarget(final String country,
                                                final ReportType reportType,
                                                final ThresholdRequest request,
                                                final AuthToken authToken) {

        authToken.checkPermission(ApplicationResource.ADMIN);

        return configurationService.setThresholdTarget(country, reportType, request);
    }
}

package com.jumia.skylens.http.in.controllers;

import com.jumia.skylens.http.in.acl.authentication.AuthToken;
import com.jumia.skylens.http.in.acl.resources.ApplicationResource;
import com.jumia.skylens.http.in.model.BoundaryRequest;
import com.jumia.skylens.http.in.model.BoundaryResponse;
import com.jumia.skylens.http.in.model.ReportType;
import com.jumia.skylens.http.in.services.BoundaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ConfigurationController implements ConfigurationApi {

    private final BoundaryService boundaryService;

    @Override
    public BoundaryResponse setMetricBoundaries(final String country,
                                                final ReportType reportType,
                                                final BoundaryRequest boundaryRequest,
                                                final AuthToken authToken) {

        authToken.checkPermission(ApplicationResource.ADMIN);

        return boundaryService.save(country, reportType, boundaryRequest);
    }
}

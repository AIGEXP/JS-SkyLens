package com.jumia.skylens.http.in.controllers;

import com.jumia.skylens.http.in.acl.authentication.AuthToken;
import com.jumia.skylens.http.in.acl.resources.ApplicationResource;
import com.jumia.skylens.http.in.model.CacheResponseInner;
import com.jumia.skylens.http.in.services.CacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CacheManagementController implements CacheManagementApi {

    private final CacheService cacheService;

    @Override
    public void clearCache(AuthToken authToken) {

        authToken.checkPermission(ApplicationResource.ADMIN);

        cacheService.clearCache();
    }

    @Override
    public List<CacheResponseInner> listCache(AuthToken authToken) {

        authToken.checkPermission(ApplicationResource.ADMIN);

        return cacheService.listCache();
    }
}

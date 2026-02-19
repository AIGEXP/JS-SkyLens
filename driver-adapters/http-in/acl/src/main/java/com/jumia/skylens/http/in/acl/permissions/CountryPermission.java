package com.jumia.skylens.http.in.acl.permissions;

import com.jumia.skylens.http.in.acl.resources.ApplicationResource;
import com.jumia.skylens.http.in.acl.resources.CountryResource;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record CountryPermission(String resource, String target) implements Permission {

    public static Permission of(String targetCode, CountryResource resource) {

        return new CountryPermission(resource.getValue(), targetCode);
    }

    public static Set<Permission> of(boolean allowAdmin, String targetCode, CountryResource... resources) {

        final Set<Permission> permissionSet = Stream.of(resources)
                .map(countryResource -> of(targetCode, countryResource))
                .collect(Collectors.toSet());

        if (allowAdmin) {
            permissionSet.add(ApplicationPermission.of(ApplicationResource.ADMIN));
        }

        return permissionSet;
    }
}

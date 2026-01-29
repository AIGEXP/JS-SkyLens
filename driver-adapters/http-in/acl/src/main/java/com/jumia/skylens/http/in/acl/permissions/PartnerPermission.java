package com.jumia.skylens.http.in.acl.permissions;

import com.jumia.skylens.http.in.acl.resources.ApplicationResource;
import com.jumia.skylens.http.in.acl.resources.PartnerResource;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record PartnerPermission(String resource, String target) implements Permission {

    public static Permission of(UUID targetCode, PartnerResource resource) {

        return new PartnerPermission(resource.getValue(), targetCode.toString());
    }

    public static Set<Permission> of(boolean allowAdmin, UUID targetCode, PartnerResource... resources) {

        final Set<Permission> permissionSet = Stream.of(resources)
                .map(countryResource -> of(targetCode, countryResource))
                .collect(Collectors.toSet());

        if (allowAdmin) {
            permissionSet.add(ApplicationPermission.of(ApplicationResource.ADMIN));
        }

        return permissionSet;
    }
}

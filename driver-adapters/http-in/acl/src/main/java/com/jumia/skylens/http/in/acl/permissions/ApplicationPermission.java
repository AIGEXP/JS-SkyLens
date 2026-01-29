package com.jumia.skylens.http.in.acl.permissions;

import com.jumia.skylens.http.in.acl.resources.ApplicationResource;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record ApplicationPermission(String resource, String target) implements Permission {

    public static final String APPLICATION_NAME = "LogisticPartners";

    public static Set<Permission> of(ApplicationResource... resources) {

        return of(false, resources);
    }

    public static Set<Permission> of(boolean withAdminPermission, ApplicationResource... resources) {

        final Set<Permission> permissionSet = Stream.of(resources)
                .map(ApplicationPermission::of)
                .collect(Collectors.toSet());

        if (withAdminPermission) {
            permissionSet.add(of(ApplicationResource.ADMIN));
        }

        return permissionSet;
    }

    public static Permission of(ApplicationResource resource) {

        return new ApplicationPermission(resource.getValue(), APPLICATION_NAME);
    }
}

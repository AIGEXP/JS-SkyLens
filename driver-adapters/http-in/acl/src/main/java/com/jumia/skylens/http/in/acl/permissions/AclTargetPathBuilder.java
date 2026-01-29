package com.jumia.skylens.http.in.acl.permissions;

import lombok.RequiredArgsConstructor;
import pt.jumia.services.acl.lib.client.authorization.Path;

@RequiredArgsConstructor
public class AclTargetPathBuilder {

    public Path buildPath(Permission permission) {

        if (permission instanceof PartnerPermission) {

            return Path.fromTargetsHierarchyAsString("*:" + permission.target());
        } else if (permission instanceof ApplicationPermission) {

            return Path.fromTargetsHierarchyAsString(permission.target());
        } else {

            throw new UnsupportedOperationException("Unsupported permission type!");
        }
    }
}

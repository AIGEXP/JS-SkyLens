package com.jumia.skylens.http.in.acl;

import net.datafaker.Faker;
import pt.jumia.services.acl.lib.RequestUser;

import java.util.UUID;

public class AclFaker extends Faker {

    public RequestUser.RequestUserBuilder requestUser() {

        return RequestUser.builder()
                .name(witcher().witcher())
                .username(harryPotter().spell())
                .email(internet().emailAddress())
                .googleId(numerify("#########################"))
                .sid(UUID.randomUUID().toString());
    }
}

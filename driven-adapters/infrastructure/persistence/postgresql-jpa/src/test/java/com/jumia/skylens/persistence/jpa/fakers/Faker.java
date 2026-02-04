package com.jumia.skylens.persistence.jpa.fakers;

import com.jumia.skylens.test.fakers.DomainFaker;

public class Faker extends net.datafaker.Faker {

    public final DomainFaker domain = new DomainFaker();
}

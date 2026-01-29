package com.jumia.skylens.persistence.jpa.fakers;

import com.jumia.skylens.persistence.jpa.entities.PackageEntity;
import com.jumia.skylens.persistence.jpa.entities.StopEntity;
import com.jumia.skylens.persistence.jpa.entities.enums.PaymentMethodTypeEnum;
import com.jumia.skylens.persistence.jpa.entities.enums.ServiceCodeEnum;
import com.jumia.skylens.persistence.jpa.entities.enums.SizeEnum;
import com.jumia.skylens.persistence.jpa.projections.PackageProjection;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EntityFaker {

    private final net.datafaker.Faker faker = new net.datafaker.Faker();

    public StopEntity.Builder stop() {

        return StopEntity.builder()
                .stopId(faker.lorem().word())
                .stopHash(faker.lorem().characters(32))
                .network(faker.country().countryCode2())
                .partnerSid(UUID.randomUUID())
                .nodeSid(UUID.randomUUID())
                .nodeName(faker.lorem().word())
                .zoneName(faker.address().cityName())
                .serviceCode(faker.options().option(ServiceCodeEnum.class))
                .size(faker.options().option(SizeEnum.class))
                .type(faker.options().option(StopEntity.StopType.class))
                .lastMileDeliveredBy(faker.options().option(StopEntity.LastMileDeliveredBy.class))
                .paymentMethodType(faker.options().option(PaymentMethodTypeEnum.class))
                .eventDate(faker.timeAndDate().birthday())
                .published(true)
                .createdAt(Instant.now())
                .updatedAt(Instant.now());
    }

    public PackageEntity.Builder pkg(final StopEntity stop) {

        final PackageEntity.Address address = PackageEntity.Address.builder()
                .id(faker.number().positive())
                .email(faker.internet().emailAddress())
                .type(faker.lorem().word())
                .region(faker.address().state())
                .city(faker.address().city())
                .build();

        return PackageEntity.builder()
                .stopId(stop.getId())
                .trackingNumber(faker.lorem().characters())
                .network("ng")
                .partnerSid(UUID.randomUUID())
                .nodeSid(UUID.randomUUID())
                .nodeName(faker.lorem().word())
                .driverSid(faker.lorem().characters())
                .driverName(faker.lorem().word())
                .paymentMethodSid(UUID.randomUUID())
                .paymentMethodName(faker.lorem().word())
                .serviceCode(faker.options().option(ServiceCodeEnum.class))
                .size(faker.options().option(SizeEnum.class))
                .address(address)
                .nodeAddress(new PackageEntity.NodeAddress(faker.address().state(), faker.address().city()))
                .eventDate(Instant.now())
                .createdAt(Instant.now());
    }

    public PackageProjection.Builder stopProjection() {

        return PackageProjection.builder()
                .stopId(faker.lorem().word())
                .eventDate(faker.timeAndDate().birthday())
                .size(faker.options().option(SizeEnum.class))
                .serviceCode(faker.options().option(ServiceCodeEnum.class))
                .nodeName(faker.lorem().word())
                .paymentMethodName(faker.lorem().word())
                .driverName(faker.name().fullName());
    }
}

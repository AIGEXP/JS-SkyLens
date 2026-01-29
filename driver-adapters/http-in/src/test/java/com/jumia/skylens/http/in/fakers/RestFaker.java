package com.jumia.skylens.http.in.fakers;

import com.jumia.skylens.http.in.model.PackageExportRequest;
import com.jumia.skylens.http.in.model.PackageFilterRequest;
import com.jumia.skylens.http.in.model.PackageResponseInner;
import com.jumia.skylens.http.in.model.PackageResponseInnerDriver;
import com.jumia.skylens.http.in.model.PackageResponseInnerNode;
import com.jumia.skylens.http.in.model.PackageResponseInnerPaymentMethod;
import com.jumia.skylens.http.in.model.Size;
import com.jumia.skylens.http.in.model.StopPublishingRequest;
import net.datafaker.Faker;

import java.time.LocalDate;
import java.util.UUID;

public class RestFaker extends Faker {

    public PackageFilterRequest.Builder packageFilterRequest() {

        return PackageFilterRequest.builder()
                .trackingNumber(lorem().characters(10))
                .stopId(lorem().characters(10))
                .nodeName(lorem().characters())
                .dateFrom(LocalDate.now())
                .dateTo(LocalDate.now().plusDays(1))
                .size(options().option(Size.class))
                .limit(1)
                .offset(10)
                .serviceCode(options().option("FCD4", "FCD2", "FCD1"))
                .driverName(name().firstName())
                .paymentMethodSid(UUID.randomUUID())
                .zoneSid(UUID.randomUUID());
    }

    public PackageExportRequest.Builder packageExportRequest() {

        return PackageExportRequest.builder()
                .id(number().randomNumber())
                .uploadTo(internet().url())
                .filters(packageFilterRequest().build());
    }

    public PackageResponseInner.Builder packageResponse() {

        return PackageResponseInner.builder()
                .trackingNumber(lorem().characters())
                .stopId(lorem().characters(10))
                .eventDate(LocalDate.now())
                .size(options().option(Size.class))
                .zone(lorem().word())
                .serviceCode(options().option("FCD4", "FCD2", "FCD1"))
                .node(PackageResponseInnerNode.builder().name(lordOfTheRings().character()).build())
                .paymentMethod(PackageResponseInnerPaymentMethod.builder().name(lordOfTheRings().character()).build())
                .driver(PackageResponseInnerDriver.builder().name(lordOfTheRings().character()).build());
    }

    public StopPublishingRequest.Builder stopPublishingRequest() {

        return StopPublishingRequest.builder()
                .partnerSid(UUID.randomUUID())
                .network(country().countryCode2())
                .dateFrom(LocalDate.now())
                .dateTo(LocalDate.now().plusDays(1))
                .force(bool().bool());
    }
}

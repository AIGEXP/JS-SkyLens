package com.jumia.skylens.test.fakers;

import com.jumia.skylens.domain.catalog.LogisticEvent;
import com.jumia.skylens.domain.catalog.Package;
import com.jumia.skylens.domain.catalog.PackageFilter;
import com.jumia.skylens.domain.catalog.PackageSummary;
import com.jumia.skylens.domain.catalog.Stop;
import com.jumia.skylens.domain.catalog.StopWithPackageQuantity;
import com.jumia.skylens.domain.catalog.Zone;
import com.jumia.skylens.domain.catalog.enums.LastMileDeliveredBy;
import com.jumia.skylens.domain.catalog.enums.PaymentMethodType;
import com.jumia.skylens.domain.catalog.enums.ServiceCode;
import com.jumia.skylens.domain.catalog.enums.Size;
import net.datafaker.Faker;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

public class DomainFaker extends Faker {

    public Stop.Builder stop() {

        return Stop.builder()
                .stopId(lorem().word())
                .stopHash(lorem().characters(32))
                .network("ng")
                .partnerSid(UUID.randomUUID())
                .nodeSid(UUID.randomUUID())
                .nodeName(lorem().word())
                .serviceCode(options().option(ServiceCode.class))
                .size(options().option(Size.class))
                .type(options().option(Stop.Type.class))
                .lastMileDeliveredBy(options().option(LastMileDeliveredBy.class))
                .eventDate(timeAndDate().birthday())
                .paymentMethodType(options().option(PaymentMethodType.class));
    }

    public Package.Builder pkg() {

        return Package.builder()
                .packageId(number().positive())
                .trackingNumber(lorem().word())
                .network("ng")
                .partnerSid(UUID.randomUUID())
                .nodeSid(UUID.randomUUID())
                .nodeName(lorem().word())
                .driverSid(lorem().characters())
                .driverName(lorem().word())
                .paymentMethodSid(UUID.randomUUID())
                .paymentMethodName(lorem().word())
                .paymentMethodType(options().option(PaymentMethodType.class))
                .serviceCode(options().option(ServiceCode.class))
                .size(options().option(Size.class))
                .address(packageAddress().build())
                .nodeAddress(new Package.NodeAddress(address().state(), address().city()))
                .eventDate(timeAndDate().future());
    }

    public Package.Address.Builder packageAddress() {

        return Package.Address.builder()
                .id(number().positive())
                .email(internet().emailAddress())
                .type(lorem().word())
                .region(address().state())
                .city(address().city());
    }

    public LogisticEvent.Builder incomingLogisticEvent() {

        final LogisticEvent.SubRequest subRequest = subRequest().build();

        return incomingLogisticEvent(subRequest);
    }

    public LogisticEvent.Builder incomingLogisticEvent(LogisticEvent.SubRequest subRequest) {

        final long currentSubRequestId = number().positive();
        final LogisticEvent.BusinessPartner businessPartner = businessPartner().build();

        return LogisticEvent.builder()
                .packageId((long) number().positive())
                .trackingNumber(lorem().word())
                .currentSubRequestId(currentSubRequestId)
                .currentBusinessPartner(businessPartner)
                .subRequests(Map.of(currentSubRequestId, subRequest))
                .network("ng")
                .size(options().option(Size.class));
    }

    public LogisticEvent.SubRequest.Builder subRequest() {

        final LogisticEvent.Invoice invoice = invoice();
        final LogisticEvent.Address address = incomingLogisticEventAddress().build();
        final LogisticEvent.Attempt attempt = attempt().build();
        final Map<Long, LogisticEvent.Attempt> attempts = Map.of((long) number().positive(), attempt);

        return LogisticEvent.SubRequest.builder()
                .status(LogisticEvent.SubRequestStatus.COMPLETE)
                .service(serviceCode())
                .address(address)
                .invoice(invoice)
                .attempts(attempts);
    }

    public LogisticEvent.Attempt.Builder attempt() {

        final LogisticEvent.Driver driver = incomingLogisticEventDriver().build();

        return LogisticEvent.Attempt.builder()
                .status(LogisticEvent.AttemptStatus.CONFIRMED)
                .successful(true)
                .driver(driver);
    }

    public LogisticEvent.Address.Builder incomingLogisticEventAddress() {

        return LogisticEvent.Address.builder()
                .id(number().positive())
                .email(internet().emailAddress())
                .type(lorem().word())
                .region(address().state())
                .city(address().city());
    }

    public LogisticEvent.Driver.Builder incomingLogisticEventDriver() {

        final LogisticEvent.ServiceProvider serviceProvider = serviceProvider().build();

        return LogisticEvent.Driver.builder()
                .sid(lorem().characters())
                .name(lordOfTheRings().character())
                .serviceProvider(serviceProvider);
    }

    public LogisticEvent.Service serviceCode() {

        return new LogisticEvent.Service(options().option(Arrays.stream(ServiceCode.values())
                                                                  .map(Object::toString)
                                                                  .toArray(String[]::new)));
    }

    public LogisticEvent.Invoice invoice() {

        final LogisticEvent.PaymentMethod paymentMethod = paymentMethod();

        return new LogisticEvent.Invoice(paymentMethod);
    }

    public LogisticEvent.PaymentMethod paymentMethod() {

        return new LogisticEvent.PaymentMethod(UUID.randomUUID(),
                                               lorem().characters(),
                                               options().option(PaymentMethodType.class));
    }

    public LogisticEvent.BusinessPartner.Builder businessPartner() {

        final LogisticEvent.ServiceProvider serviceProvider = serviceProvider().build();

        return LogisticEvent.BusinessPartner.builder()
                .sid(UUID.randomUUID())
                .serviceProvider(serviceProvider);
    }

    public LogisticEvent.ServiceProvider.Builder serviceProvider() {

        return LogisticEvent.ServiceProvider.builder()
                .sid(UUID.randomUUID())
                .type(LogisticEvent.ServiceProviderType.THIRD_PARTY);
    }

    public Zone zone() {

        return new Zone(UUID.randomUUID(), lorem().word());
    }

    public PackageFilter.Builder packageFilter() {

        return PackageFilter.builder()
                .partnerSid(UUID.randomUUID())
                .trackingNumber(lorem().characters(10))
                .stopId(lorem().characters(10))
                .serviceCode(options().option(ServiceCode.class).name());
    }

    public PackageSummary.Builder packageSummary() {

        return PackageSummary.builder()
                .stopId(lorem().characters(10))
                .eventDate(timeAndDate().birthday())
                .size(options().option(Size.class))
                .serviceCode(options().option(ServiceCode.class))
                .nodeName(lorem().word())
                .paymentMethodName(lorem().word())
                .driverName(lorem().word());
    }

    public StopWithPackageQuantity.Builder stopWithPackageQuantity() {

        return StopWithPackageQuantity.builder()
                .stopId(lorem().word())
                .network("ng")
                .partnerSid(UUID.randomUUID())
                .nodeSid(UUID.randomUUID())
                .serviceCode(options().option(ServiceCode.class))
                .size(options().option(Size.class))
                .eventDate(timeAndDate().birthday())
                .paymentMethodType(options().option(PaymentMethodType.class))
                .quantity(number().positive());
    }
}

package com.jumia.skylens.persistence.jpa.providers;

import com.jumia.skylens.domain.catalog.PackageFilter;
import com.jumia.skylens.domain.catalog.enums.Size;
import com.jumia.skylens.persistence.jpa.entities.PackageEntity;
import com.jumia.skylens.persistence.jpa.entities.StopEntity;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.ParameterDeclarations;

import java.util.function.BiFunction;
import java.util.stream.Stream;

public class PackageFilterProvider implements ArgumentsProvider {

    @Override
    public @NonNull Stream<? extends Arguments> provideArguments(@NonNull ParameterDeclarations parameterDeclarations,
                                                                 @NonNull ExtensionContext context) {

        return Stream.<BiFunction<StopEntity, PackageEntity, PackageFilter>>of(
                (stop, _) -> PackageFilter.builder().partnerSid(stop.getPartnerSid()).build(),
                (stop, _) -> PackageFilter.builder().stopId(stop.getStopId()).build(),
                (stop, _) -> PackageFilter.builder().serviceCode(stop.getServiceCode().name()).build(),
                (stop, _) -> PackageFilter.builder().size(Size.valueOf(stop.getSize().name())).build(),
                (stop, _) -> PackageFilter.builder().zoneSid(stop.getZoneSid()).build(),
                (stop, _) -> PackageFilter.builder().nodeName(stop.getNodeName()).build(),
                (_, pkg) -> PackageFilter.builder().paymentMethodSid(pkg.getPaymentMethodSid()).build(),
                (_, pkg) -> PackageFilter.builder().driverName(pkg.getDriverName()).build()
        ).map(Arguments::of);
    }
}

package com.jumia.skylens.persistence.jpa.providers;

import com.jumia.skylens.domain.catalog.StopPublishingFilter;
import com.jumia.skylens.persistence.jpa.entities.StopEntity;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.ParameterDeclarations;

import java.util.function.Function;
import java.util.stream.Stream;

public class StopPublishingFilterProvider implements ArgumentsProvider {

    @Override
    public @NonNull Stream<? extends Arguments> provideArguments(@NonNull ParameterDeclarations parameterDeclarations,
                                                                 @NonNull ExtensionContext context) {

        return Stream.<Function<StopEntity, StopPublishingFilter>>of(
                stop -> StopPublishingFilter.builder().partnerSid(stop.getPartnerSid()).build(),
                stop -> StopPublishingFilter.builder().network(stop.getNetwork()).build(),
                stop -> StopPublishingFilter.builder().date(stop.getEventDate()).build()
        ).map(Arguments::of);
    }
}

package com.jumia.skylens.http.in.providers;

import com.jumia.skylens.http.in.fakers.RestFaker;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.ParameterDeclarations;

import java.time.LocalDate;
import java.util.stream.Stream;

public class InvalidListPackageStopProvider implements ArgumentsProvider {

    private final RestFaker faker = new RestFaker();

    @Override
    public @NonNull Stream<? extends Arguments> provideArguments(@NonNull ParameterDeclarations parameterDeclarations,
                                                                 @NonNull ExtensionContext extensionContext) {

        return Stream.of(
                Arguments.of(faker.packageFilterRequest().limit(0).build()),
                Arguments.of(faker.packageFilterRequest().limit(-1).build()),
                Arguments.of(faker.packageFilterRequest().limit(1001).build()),
                Arguments.of(faker.packageFilterRequest().offset(-1).build()),
                Arguments.of(faker.packageFilterRequest()
                                     .dateFrom(LocalDate.of(2025, 12, 31))
                                     .dateTo(LocalDate.of(2025, 12, 1))
                                     .build())
        );
    }
}

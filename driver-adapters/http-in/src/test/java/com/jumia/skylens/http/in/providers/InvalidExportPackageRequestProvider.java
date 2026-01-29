package com.jumia.skylens.http.in.providers;

import com.jumia.skylens.http.in.fakers.RestFaker;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.ParameterDeclarations;

import java.util.stream.Stream;

public class InvalidExportPackageRequestProvider implements ArgumentsProvider {

    private final RestFaker restFaker = new RestFaker();

    @Override
    public @NonNull Stream<? extends Arguments> provideArguments(@NonNull ParameterDeclarations parameterDeclarations,
                                                                 @NonNull ExtensionContext extensionContext) {

        return Stream.of(
                Arguments.of(restFaker.packageExportRequest()
                        .id(null)
                        .build()),
                Arguments.of(restFaker.packageExportRequest()
                        .uploadTo(null)
                        .build()),
                Arguments.of(restFaker.packageExportRequest()
                        .id(null)
                        .uploadTo(null)
                        .build())
        );
    }
}

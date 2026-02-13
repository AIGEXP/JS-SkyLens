package com.jumia.skylens.cli.in.runner;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

@Value
@RequiredArgsConstructor(onConstructor = @__({@ConstructorBinding}))
@ConfigurationProperties("app.job")
public class JobProperties {

    int pageSize;

    SourceDbProperties sourceDb;

    @Value
    @RequiredArgsConstructor(onConstructor = @__({@ConstructorBinding}))
    public static class SourceDbProperties {

        String url;

        String username;

        String password;
    }
}

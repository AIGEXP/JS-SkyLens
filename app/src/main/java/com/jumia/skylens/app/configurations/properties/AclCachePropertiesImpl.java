package com.jumia.skylens.app.configurations.properties;

import com.jumia.skylens.http.in.configurations.properties.AclCacheProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties("app.acl-service.cache")
public record AclCachePropertiesImpl(Duration expireDuration) implements AclCacheProperties {

}

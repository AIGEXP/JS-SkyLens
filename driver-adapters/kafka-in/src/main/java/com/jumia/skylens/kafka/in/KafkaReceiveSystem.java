package com.jumia.skylens.kafka.in;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum KafkaReceiveSystem {
    SKY_DRIVERS("skyDrivers");

    private final String internalName;
}

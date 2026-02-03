package com.jumia.skylens.kafka.in;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum KafkaReceiveSystem {
    BI("BI");

    private final String internalName;
}

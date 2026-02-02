package com.jumia.skylens.kafka.in.configuration;

import com.jumia.skylens.kafka.in.KafkaMessageConsumer;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Import;

@EnableAutoConfiguration
@Import(KafkaMessageConsumer.class)
public class KafkaConfiguration {

}

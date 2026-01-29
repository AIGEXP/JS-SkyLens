package com.jumia.skylens.kafka.in.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jumia.skylens.kafka.in.KafkaMessageConsumer;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@EnableAutoConfiguration
@Import(KafkaMessageConsumer.class)
public class KafkaConfiguration {

    @Bean
    public ObjectMapper kafkaObjectMapperProvider() {

        return new ObjectMapper()
                .registerModule(new JavaTimeModule());
    }
}

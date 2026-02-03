package com.jumia.skylens.kafka.in.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jumia.skylens.kafka.in.fakers.KafkaInFaker;
import com.jumia.skylens.test.testcontainers.KafkaContainerSingleton;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@ExtendWith(SpringExtension.class)
@Testcontainers
@ContextConfiguration(classes = KafkaConfiguration.class, loader = AnnotationConfigContextLoader.class)
public abstract class BaseTestKafkaIn {

    @Container
    public static final KafkaContainerSingleton KAFKA = KafkaContainerSingleton.getInstance();

    protected final KafkaInFaker faker = new KafkaInFaker();

    @Resource
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Resource
    private ObjectMapper kafkaObjectMapperProvider;

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {

        registry.add("spring.kafka.bootstrap-servers", KAFKA::getBootstrapServers);
    }

    @SneakyThrows
    protected void publishMessage(String topic, Object payload, Map<String, String> headers) {

        publishMessage(topic, kafkaObjectMapperProvider.writeValueAsString(payload), headers);
    }

    protected void publishMessage(String topic, String payload, Map<String, String> headers) {

        final ProducerRecord<String, Object> producerRecord = new ProducerRecord<>(topic, payload);

        headers.forEach((key, value) -> producerRecord.headers().add(key, value.getBytes(StandardCharsets.UTF_8)));

        kafkaTemplate.send(producerRecord);
    }
}

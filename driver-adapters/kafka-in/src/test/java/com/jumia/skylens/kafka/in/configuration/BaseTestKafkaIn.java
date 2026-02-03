package com.jumia.skylens.kafka.in.configuration;

import com.jumia.skylens.kafka.in.fakers.KafkaInFaker;
import com.jumia.skylens.test.testcontainers.KafkaContainerSingleton;
import jakarta.annotation.Resource;
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
import tools.jackson.databind.json.JsonMapper;

@ExtendWith(SpringExtension.class)
@Testcontainers
@ContextConfiguration(classes = KafkaConfiguration.class, loader = AnnotationConfigContextLoader.class)
public abstract class BaseTestKafkaIn {

    @Container
    static final KafkaContainerSingleton KAFKA_CONTAINER = KafkaContainerSingleton.getInstance();

    protected final KafkaInFaker faker = new KafkaInFaker();

    @Resource
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Resource
    private JsonMapper jsonMapper;

    @DynamicPropertySource
    static void baseKafkaProperties(DynamicPropertyRegistry registry) {

        registry.add("spring.kafka.bootstrap-servers", KAFKA_CONTAINER::getBootstrapServers);
        registry.add("spring.kafka.consumer.auto-offset-reset", () -> "earliest");
        registry.add("spring.kafka.listener.ack-mode", () -> "manual_immediate");
    }

    protected void publishMessage(String topic, Object payload) {

        sedMessage(topic, jsonMapper.writeValueAsString(payload));
    }

    protected void sedMessage(String topic, String payload) {

        final ProducerRecord<String, Object> producerRecord = new ProducerRecord<>(topic, payload);

        kafkaTemplate.send(producerRecord);
    }
}

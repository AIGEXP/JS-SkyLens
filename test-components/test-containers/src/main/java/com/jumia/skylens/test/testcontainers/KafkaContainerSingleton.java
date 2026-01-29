package com.jumia.skylens.test.testcontainers;

import lombok.extern.slf4j.Slf4j;
import org.testcontainers.kafka.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

@Slf4j
public final class KafkaContainerSingleton extends KafkaContainer {

    private static final String IMAGE_VERSION = "apache/kafka";

    private KafkaContainerSingleton() {

        super(DockerImageName.parse(IMAGE_VERSION));
    }

    public static KafkaContainerSingleton getInstance() {

        return ContainerHolder.CONTAINER;
    }

    @Override
    public void stop() {

        log.info("Kafka container shutting down");
        super.stop();
    }

    private static final class ContainerHolder {

        private static final KafkaContainerSingleton CONTAINER = new KafkaContainerSingleton();
    }
}

package com.jumia.skylens.acceptance.utils;

import lombok.Getter;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;

@Getter
@Component
public class LastMileStopConsumer {

    private static final String TOPIC_PATTERN = "services_.*_hmt_topic_3pl-lastmile";

    private final LinkedList<ConsumerRecord<?, ?>> consumerRecords = new LinkedList<>();

    private final CountDownLatch latch = new CountDownLatch(1);

    @KafkaListener(topicPattern = TOPIC_PATTERN)
    public void receive(ConsumerRecord<?, ?> consumerRecord) {

        this.consumerRecords.add(consumerRecord);
        latch.countDown();
    }
}

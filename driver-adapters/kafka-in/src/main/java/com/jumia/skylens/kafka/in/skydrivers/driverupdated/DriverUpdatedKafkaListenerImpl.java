package com.jumia.skylens.kafka.in.skydrivers.driverupdated;

import com.jumia.skylens.kafka.in.AbstractMessageListener;
import com.jumia.skylens.kafka.in.KafkaMessageConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
public class DriverUpdatedKafkaListenerImpl extends AbstractMessageListener implements DriverUpdatedKafkaListener {

    public DriverUpdatedKafkaListenerImpl(final DriverUpdatedKafkaProcessor driverUpdatedKafkaProcessor,
                                          final KafkaMessageConsumer kafkaMessageConsumer) {

        super(driverUpdatedKafkaProcessor, kafkaMessageConsumer);
    }

    @KafkaListener(
            topicPattern = "${app.kafka.in.topic}",
            concurrency = "${spring.kafka.listener.concurrency:1}"
    )
    @Override
    public void onMessage(final ConsumerRecord<String, String> consumerRecord,
                          final Acknowledgment ack) {

        super.onMessage(consumerRecord, ack);
    }
}

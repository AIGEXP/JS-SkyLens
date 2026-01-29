package com.jumia.skylens.kafka.in;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jumia.skylens.kafka.in.exceptions.EmptyBodyException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;
import org.springframework.stereotype.Service;
import tools.jackson.databind.json.JsonMapper;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class KafkaMessageConsumer {

    private final JsonMapper jsonMapper;

    public void processMessage(final KafkaProcessor<?, ? extends KafkaProcessorContext<?>> processor,
                               final ConsumerRecord<String, String> consumerRecord) {

        process(processor, consumerRecord);
    }

    private <T, C extends KafkaProcessorContext<T>> void process(final KafkaProcessor<T, C> processor,
                                                                 final ConsumerRecord<String, String> consumerRecord) {

        final Map<String, Object> headers = StreamSupport.stream(consumerRecord.headers().spliterator(), false)
                .collect(Collectors.toMap(Header::key, header -> new String(header.value(), StandardCharsets.UTF_8)));

        process(processor,
                consumerRecord,
                payload -> {
                    @SuppressWarnings("unchecked") final C context = (C) new KafkaProcessorContext<>(payload, headers, consumerRecord);
                    processor.execute(context);
                });
    }

    private <T, C extends KafkaProcessorContext<T>> void process(final KafkaProcessor<T, C> processor,
                                                                 final ConsumerRecord<String, String> consumerRecord,
                                                                 final Consumer<T> consumer) {

        final String message = consumerRecord.value();

        if (StringUtils.isBlank(message)) {
            throw new EmptyBodyException();
        }

        setTenant(consumerRecord);

        consumer.accept(processor.convertPayload(message, jsonMapper));
    }

    private void setTenant(final ConsumerRecord<String, String> consumerRecord) {

        final String tenantId = getTenantForMessage(consumerRecord);
    }

    private String getTenantForMessage(final ConsumerRecord<String, String> consumerRecord) {

        return Optional.ofNullable(consumerRecord.headers().lastHeader(MessageHeaders.NETWORK_HEADER_NAME))
                .map(header -> new String(header.value(), StandardCharsets.UTF_8))
                .orElseThrow(() -> new RuntimeException());
    }
}

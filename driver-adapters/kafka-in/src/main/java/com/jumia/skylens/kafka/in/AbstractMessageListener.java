package com.jumia.skylens.kafka.in;

import com.jumia.skylens.commons.logging.LogContext;
import com.jumia.skylens.commons.logging.LoggingUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;
import org.springframework.kafka.support.Acknowledgment;
import pt.aig.aigx.loggingcontext.dtos.KafkaMessageLogDTO;
import pt.aig.aigx.loggingcontext.enums.CommunicationTypeEnum;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractMessageListener {

    private final KafkaProcessor<?, ? extends KafkaProcessorContext<?>> kafkaProcessor;

    private final KafkaMessageConsumer kafkaMessageConsumer;

    public void onMessage(final ConsumerRecord<String, String> consumerRecord, final Acknowledgment acknowledgment) {

        final KafkaMessageLogDTO logDTO = buildLoggingDTO(consumerRecord);

        LoggingUtils.setLogContext(LogContext.of(kafkaProcessor.getSystem().getInternalName(),
                                                 null,
                                                 CommunicationTypeEnum.KAFKA_IN.getCommunicationTypeValue()));
        try {

            handleMessage(consumerRecord, logDTO);
        } catch (final Exception exception) {

            handleProcessingError(logDTO, exception);
        }

        if (Objects.nonNull(acknowledgment)) {
            acknowledgment.acknowledge();
        }
    }

    private KafkaMessageLogDTO buildLoggingDTO(final ConsumerRecord<String, String> consumerRecord) {

        final Map<String, Object> headers = StreamSupport.stream(consumerRecord.headers().spliterator(), false)
                .collect(Collectors.toMap(Header::key, header -> new String(header.value(), StandardCharsets.UTF_8)));

        return KafkaMessageLogDTO.builder()
                .topic(consumerRecord.topic())
                .headers(headers)
                .payload(consumerRecord.value())
                .build();
    }

    private void handleMessage(final ConsumerRecord<String, String> consumerRecord, final KafkaMessageLogDTO logDTO) {

        logMessage(KafkaMessageLogDTO.KafkaLogMessage.PROCESSING.getMessage(), logDTO);

        kafkaMessageConsumer.processMessage(kafkaProcessor, consumerRecord);

        logMessage(KafkaMessageLogDTO.KafkaLogMessage.PROCESSED.getMessage(), logDTO);
    }

    private void logMessage(final String message, final KafkaMessageLogDTO logDTO) {

        final String msg = logDTO.toString(log, message, false);
        log.info(msg);
    }

    private void handleProcessingError(final KafkaMessageLogDTO logDTO, final Exception exception) {

        final String msg = logDTO.toString(log, KafkaMessageLogDTO.KafkaLogMessage.ERROR_PROCESSING.getMessage(), true);
        log.error(msg, exception);
    }
}

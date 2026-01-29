package com.jumia.skylens.kafka.in;

import com.jumia.skylens.kafka.in.exceptions.InvalidEventException;
import lombok.extern.slf4j.Slf4j;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.json.JsonMapper;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

@Slf4j
public abstract class AbstractKafkaProcessor<T> implements KafkaProcessor<T, KafkaProcessorContext<T>> {

    private static final String JSON_ERROR_MESSAGE = "Unable to parse payload for class {}";

    private final Class<T> payloadClass = getPayloadClass();

    @Override
    public void execute(KafkaProcessorContext<T> context) {

        process(context.payload());
    }

    @Override
    public T convertPayload(final String payload, final JsonMapper jsonMapper) {

        T convertedPayload;
        try {
            convertedPayload = jsonMapper.readValue(payload, payloadClass);
        } catch (final JacksonException ioException) {

            throw new InvalidEventException(JSON_ERROR_MESSAGE + " " + payloadClass.getName(), ioException);
        }
        return convertedPayload;
    }

    private Class<T> getPayloadClass() {

        final Type genericSuperclass = getClass().getGenericSuperclass();
        final ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
        final Type actualTypeArgument = parameterizedType.getActualTypeArguments()[0];
        if (actualTypeArgument instanceof ParameterizedType) {
            //For cases when the payload is also a generic, get its raw type
            return (Class<T>) ((ParameterizedType) actualTypeArgument).getRawType();
        } else {
            return (Class<T>) actualTypeArgument;
        }
    }

    public abstract void process(T payload);
}

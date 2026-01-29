package org.openapitools.configuration;

import com.jumia.skylens.http.in.model.DateRange;
import com.jumia.skylens.http.in.model.MovementType;
import com.jumia.skylens.http.in.model.PaymentType;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

/**
 * This class provides Spring Converter beans for the enum models in the OpenAPI specification.
 *
 * By default, Spring only converts primitive types to enums using Enum::valueOf, which can prevent
 * correct conversion if the OpenAPI specification is using an `enumPropertyNaming` other than
 * `original` or the specification has an integer enum.
 */
@Configuration(value = "org.openapitools.configuration.enumConverterConfiguration")
public class EnumConverterConfiguration {

    @Bean(name = "org.openapitools.configuration.EnumConverterConfiguration.dateRangeConverter")
    Converter<String, DateRange> dateRangeConverter() {
        return new Converter<String, DateRange>() {
            @Override
            public DateRange convert(String source) {
                return DateRange.fromValue(source);
            }
        };
    }
    @Bean(name = "org.openapitools.configuration.EnumConverterConfiguration.movementTypeConverter")
    Converter<String, MovementType> movementTypeConverter() {
        return new Converter<String, MovementType>() {
            @Override
            public MovementType convert(String source) {
                return MovementType.fromValue(source);
            }
        };
    }
    @Bean(name = "org.openapitools.configuration.EnumConverterConfiguration.paymentTypeConverter")
    Converter<String, PaymentType> paymentTypeConverter() {
        return new Converter<String, PaymentType>() {
            @Override
            public PaymentType convert(String source) {
                return PaymentType.fromValue(source);
            }
        };
    }

}

package com.jumia.skylens.kafka.in.bi.hubdailymetrics.converters;

import com.jumia.skylens.domain.catalog.HubDailyMetric;
import com.jumia.skylens.kafka.in.bi.hubdailymetrics.dtos.HubDailyMetricDTO;
import com.jumia.skylens.kafka.in.fakers.KafkaInFaker;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

class HubDailyMetricConverterTest {

    private final KafkaInFaker faker = new KafkaInFaker();

    private final HubDailyMetricConverter subject = new HubDailyMetricConverterImpl();

    @Test
    void convert_whenCalled_thenConvertSuccessfully() {

        // Given
        final HubDailyMetricDTO hubDailyMetricDTO = faker.hubDailyMetricDTO().build();

        // When
        final HubDailyMetric hubDailyMetric = subject.convert(hubDailyMetricDTO);

        // Then
        assertThat(hubDailyMetric)
                .usingRecursiveComparison()
                .ignoringFields("paymentType")
                .isEqualTo(HubDailyMetric.builder()
                                   .hubSid(hubDailyMetricDTO.hubSid())
                                   .serviceProviderSid(hubDailyMetricDTO.serviceProviderSid())
                                   .day(LocalDate.parse(hubDailyMetricDTO.day(), DateTimeFormatter.ofPattern("yyyyMMdd")))
                                   .movementType(HubDailyMetric.MovementType.valueOf(hubDailyMetricDTO.movementType().name()))
                                   .packagesDelivered(hubDailyMetricDTO.packagesDelivered())
                                   .packagesClosed(hubDailyMetricDTO.packagesClosed())
                                   .packagesReceived(hubDailyMetricDTO.packagesReceived())
                                   .packagesLostAtHub(hubDailyMetricDTO.packagesLostAtHub())
                                   .packagesNoAttemptsOneDay(hubDailyMetricDTO.packagesNoAttemptsOneDay())
                                   .packagesNoAttemptsTwoDays(hubDailyMetricDTO.packagesNoAttemptsTwoDays())
                                   .packagesNoAttemptsThreeDays(hubDailyMetricDTO.packagesNoAttemptsThreeDays())
                                   .packagesNoAttemptsOverThreeDays(hubDailyMetricDTO.packagesNoAttemptsFourDays()
                                                                            + hubDailyMetricDTO.packagesNoAttemptsOverFourDays()));
    }

    @Test
    void convert_whenIsPrePaidIsTrue_thenConvertToPaymentTypePRE() {

        // Given
        final HubDailyMetricDTO hubDailyMetricDTO = faker.hubDailyMetricDTO().prePaid(true).build();

        // When
        final HubDailyMetric hubDailyMetric = subject.convert(hubDailyMetricDTO);

        // Then
        assertThat(hubDailyMetric.paymentType()).isEqualTo(HubDailyMetric.PaymentType.PRE);
    }

    @Test
    void convert_whenIsPrePaidIsFalse_thenConvertToPaymentTypePOST() {

        // Given
        final HubDailyMetricDTO hubDailyMetricDTO = faker.hubDailyMetricDTO().prePaid(false).build();

        // When
        final HubDailyMetric hubDailyMetric = subject.convert(hubDailyMetricDTO);

        // Then
        assertThat(hubDailyMetric.paymentType()).isEqualTo(HubDailyMetric.PaymentType.POST);
    }
}

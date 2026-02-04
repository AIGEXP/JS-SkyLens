package com.jumia.skylens.http.in.fakers;

import com.jumia.skylens.http.in.model.DeliveryMetricsResponseInner;
import com.jumia.skylens.http.in.model.LossRateMetricsResponseInner;
import com.jumia.skylens.http.in.model.NoAttemptsMetricsResponse;
import com.jumia.skylens.http.in.model.SuccessRateMetricsResponseInner;
import net.datafaker.Faker;

public class RestFaker extends Faker {

    public DeliveryMetricsResponseInner.Builder deliveryMetricsResponse() {

        return DeliveryMetricsResponseInner.builder()
                .date(timeAndDate().birthday())
                .packagesDelivered(number().randomDigit());
    }

    public SuccessRateMetricsResponseInner.Builder successRateMetricsResponse() {

        return SuccessRateMetricsResponseInner.builder()
                .date(timeAndDate().birthday())
                .packagesDelivered(number().randomDigit())
                .packagesClosed(number().randomDigit())
                .successRate(number().randomDouble(2, 0, 100));
    }

    public LossRateMetricsResponseInner.Builder lossRateMetricsResponse() {

        return LossRateMetricsResponseInner.builder()
                .date(timeAndDate().birthday())
                .packagesLost(number().randomDigit())
                .packagesReceived(number().randomDigit())
                .lossRate(number().randomDouble(2, 0, 100));
    }

    public NoAttemptsMetricsResponse.Builder noAttemptsMetricsResponse() {

        return NoAttemptsMetricsResponse.builder()
                .oneDay(number().randomDigit())
                .twoDays(number().randomDigit())
                .threeDays(number().randomDigit())
                .overThreeDays(number().randomDigit());
    }
}

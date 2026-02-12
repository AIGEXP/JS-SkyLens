package com.jumia.skylens.app.configurations.domain;

import com.jumia.skylens.domain.GetCurrentPackageAttemptsMetricsUseCase;
import com.jumia.skylens.domain.GetPackageMetricsUseCase;
import com.jumia.skylens.domain.ListDateRangeUseCase;
import com.jumia.skylens.domain.ListMovementTypeUseCase;
import com.jumia.skylens.domain.ListPaymentTypeUseCase;
import com.jumia.skylens.domain.SaveHubDailyMetricUseCase;
import com.jumia.skylens.domain.UpsertNetworkThresholdUseCase;
import com.jumia.skylens.domain.impl.GetCurrentPackageAttemptsMetricsUseCaseImpl;
import com.jumia.skylens.domain.impl.GetPackageMetricsUseCaseImpl;
import com.jumia.skylens.domain.impl.ListDateRangeUseCaseImpl;
import com.jumia.skylens.domain.impl.ListMovementTypeUseCaseImpl;
import com.jumia.skylens.domain.impl.ListPaymentTypeUseCaseImpl;
import com.jumia.skylens.domain.impl.SaveHubDailyMetricUseCaseImpl;
import com.jumia.skylens.domain.impl.UpsertNetworkThresholdUseCaseImpl;
import com.jumia.skylens.persistence.api.HubDailyMetricDAO;
import com.jumia.skylens.persistence.api.NetworkThresholdDAO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfiguration {

    @Bean
    public SaveHubDailyMetricUseCase saveHubDailyMetricUseCase(final HubDailyMetricDAO hubDailyMetricDAO) {

        return new SaveHubDailyMetricUseCaseImpl(hubDailyMetricDAO);
    }

    @Bean
    public GetPackageMetricsUseCase getPackageStatisticsUseCase(final HubDailyMetricDAO hubDailyMetricDAO) {

        return new GetPackageMetricsUseCaseImpl(hubDailyMetricDAO);
    }

    @Bean
    public GetCurrentPackageAttemptsMetricsUseCase getCurrentPackageAttemptsMetricsUseCase(final HubDailyMetricDAO hubDailyMetricDAO) {

        return new GetCurrentPackageAttemptsMetricsUseCaseImpl(hubDailyMetricDAO);
    }

    @Bean
    ListDateRangeUseCase listDateRangeUseCase() {

        return new ListDateRangeUseCaseImpl();
    }

    @Bean
    public ListPaymentTypeUseCase listPaymentTypeUseCase() {

        return new ListPaymentTypeUseCaseImpl();
    }

    @Bean
    public ListMovementTypeUseCase listMovementTypeUseCase() {

        return new ListMovementTypeUseCaseImpl();
    }

    @Bean
    public UpsertNetworkThresholdUseCase saveNetworkThresholdUseCase(final NetworkThresholdDAO networkThresholdDAO) {

        return new UpsertNetworkThresholdUseCaseImpl(networkThresholdDAO);
    }
}

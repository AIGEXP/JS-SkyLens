package com.jumia.skylens.app.configurations.domain;

import com.jumia.skylens.domain.SaveHubDailyMetricUseCase;
import com.jumia.skylens.domain.impl.SaveHubDailyMetricUseCaseImpl;
import com.jumia.skylens.persistence.api.HubDailyMetricDAO;
import org.springframework.context.annotation.Bean;
import com.jumia.skylens.domain.ListDateRangeUseCase;
import com.jumia.skylens.domain.impl.ListDateRangeUseCaseImpl;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfiguration {

    @Bean
    public SaveHubDailyMetricUseCase saveHubDailyMetricUseCase(final HubDailyMetricDAO hubDailyMetricDAO) {

        return new SaveHubDailyMetricUseCaseImpl(hubDailyMetricDAO);
    }

    @Bean
    ListDateRangeUseCase listDateRangeUseCase() {

        return new ListDateRangeUseCaseImpl();
    }
}

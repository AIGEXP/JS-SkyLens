package com.jumia.skylens.app.configurations.domain;

import com.jumia.skylens.domain.ClearCacheUseCase;
import com.jumia.skylens.domain.GetCacheUseCase;
import com.jumia.skylens.domain.GetCountryThresholdUseCase;
import com.jumia.skylens.domain.GetCurrentPackageAttemptsMetricsUseCase;
import com.jumia.skylens.domain.GetPackageMetricsUseCase;
import com.jumia.skylens.domain.ListDateRangeUseCase;
import com.jumia.skylens.domain.ListMovementTypeUseCase;
import com.jumia.skylens.domain.ListPaymentTypeUseCase;
import com.jumia.skylens.domain.SaveAlertLevelUseCase;
import com.jumia.skylens.domain.SaveHubDailyMetricUseCase;
import com.jumia.skylens.domain.UpsertCountryThresholdUseCase;
import com.jumia.skylens.domain.impl.ClearCacheUseCaseImpl;
import com.jumia.skylens.domain.impl.GetCacheUseCaseImpl;
import com.jumia.skylens.domain.impl.GetCountryThresholdUseCaseImpl;
import com.jumia.skylens.domain.impl.GetCurrentPackageAttemptsMetricsUseCaseImpl;
import com.jumia.skylens.domain.impl.GetPackageMetricsUseCaseImpl;
import com.jumia.skylens.domain.impl.ListDateRangeUseCaseImpl;
import com.jumia.skylens.domain.impl.ListMovementTypeUseCaseImpl;
import com.jumia.skylens.domain.impl.ListPaymentTypeUseCaseImpl;
import com.jumia.skylens.domain.impl.SaveAlertLevelUseCaseImpl;
import com.jumia.skylens.domain.impl.SaveHubDailyMetricUseCaseImpl;
import com.jumia.skylens.domain.impl.UpsertCountryThresholdUseCaseImpl;
import com.jumia.skylens.persistence.api.AlertLevelDAO;
import com.jumia.skylens.persistence.api.CountryThresholdDAO;
import com.jumia.skylens.persistence.api.HubDailyMetricDAO;
import com.jumia.skylens.ports.http.out.api.cache.CountryCacheManagement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfiguration {

    @Bean
    public SaveAlertLevelUseCase saveAlertLevelUseCaseImpl(final AlertLevelDAO alertLevelDAO) {

        return new SaveAlertLevelUseCaseImpl(alertLevelDAO);
    }

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
    public ListDateRangeUseCase listDateRangeUseCase() {

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
    public UpsertCountryThresholdUseCase saveCountryThresholdUseCase(final CountryThresholdDAO countryThresholdDAO) {

        return new UpsertCountryThresholdUseCaseImpl(countryThresholdDAO);
    }

    @Bean
    public GetCountryThresholdUseCase getCountryThresholdUseCase(final CountryThresholdDAO countryThresholdDAO) {

        return new GetCountryThresholdUseCaseImpl(countryThresholdDAO);
    }

    @Bean
    public GetCacheUseCase getCacheUseCase(final CountryCacheManagement countryCacheManagement) {

        return new GetCacheUseCaseImpl(countryCacheManagement);
    }

    @Bean
    public ClearCacheUseCase clearCacheUseCase(final CountryCacheManagement countryCacheManagement) {

        return new ClearCacheUseCaseImpl(countryCacheManagement);
    }
}

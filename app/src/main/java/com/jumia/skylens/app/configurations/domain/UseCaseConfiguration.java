package com.jumia.skylens.app.configurations.domain;

import com.jumia.skylens.domain.ListDateRangeUseCase;
import com.jumia.skylens.domain.impl.ListDateRangeUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfiguration {

    @Bean
    ListDateRangeUseCase listDateRangeUseCase() {

        return new ListDateRangeUseCaseImpl();
    }
}

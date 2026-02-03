package com.jumia.skylens.persistence.jpa.repositories;

import com.jumia.skylens.persistence.jpa.repositories.custom.HubDailyMetricCustomRepository;
import com.jumia.skylens.persistence.jpa.repositories.jpa.HubDailyMetricJpaRepository;

public interface HubDailyMetricRepository extends HubDailyMetricJpaRepository, HubDailyMetricCustomRepository {

}

package com.jumia.skylens.persistence.jpa.repositories.jpa;

import com.jumia.skylens.persistence.jpa.entities.HubDailyMetricEntity;
import com.jumia.skylens.persistence.jpa.entities.HubDailyMetricEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HubDailyMetricJpaRepository extends JpaRepository<HubDailyMetricEntity, HubDailyMetricEntityId> {

}

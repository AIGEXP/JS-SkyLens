package com.jumia.skylens.persistence.jpa.repositories.jpa;

import com.jumia.skylens.persistence.jpa.entities.CountryThresholdEntity;
import com.jumia.skylens.persistence.jpa.entities.CountryThresholdEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryThresholdJpaRepository extends JpaRepository<CountryThresholdEntity, CountryThresholdEntityId> {

}

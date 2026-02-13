package com.jumia.skylens.persistence.jpa.repositories.jpa;

import com.jumia.skylens.persistence.jpa.entities.NetworkThresholdEntity;
import com.jumia.skylens.persistence.jpa.entities.NetworkThresholdEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NetworkThresholdJpaRepository extends JpaRepository<NetworkThresholdEntity, NetworkThresholdEntityId> {

}

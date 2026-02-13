package com.jumia.skylens.persistence.jpa.repositories.jpa;

import com.jumia.skylens.persistence.jpa.entities.BoundaryEntity;
import com.jumia.skylens.persistence.jpa.entities.BoundaryEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoundaryJpaRepository extends JpaRepository<BoundaryEntity, BoundaryEntityId> {

}

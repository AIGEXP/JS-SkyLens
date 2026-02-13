package com.jumia.skylens.persistence.jpa.repositories.jpa;

import com.jumia.skylens.persistence.jpa.entities.AlertLevelEntity;
import com.jumia.skylens.persistence.jpa.entities.AlertLevelEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertLevelJpaRepository extends JpaRepository<AlertLevelEntity, AlertLevelEntityId> {

}

package com.jumia.skylens.persistence.api;

import com.jumia.skylens.domain.catalog.AlertLevel;

public interface AlertLevelDAO {

    AlertLevel save(AlertLevel alertLevel);
}

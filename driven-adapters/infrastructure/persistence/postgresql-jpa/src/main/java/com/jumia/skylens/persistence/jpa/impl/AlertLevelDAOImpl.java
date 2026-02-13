package com.jumia.skylens.persistence.jpa.impl;

import com.jumia.skylens.domain.catalog.AlertLevel;
import com.jumia.skylens.persistence.api.AlertLevelDAO;
import com.jumia.skylens.persistence.jpa.converters.AlertLevelEntityConverter;
import com.jumia.skylens.persistence.jpa.entities.AlertLevelEntity;
import com.jumia.skylens.persistence.jpa.repositories.AlertLevelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlertLevelDAOImpl implements AlertLevelDAO {

    private final AlertLevelRepository alertLevelRepository;

    private final AlertLevelEntityConverter alertLevelEntityConverter;

    @Override
    public AlertLevel save(final AlertLevel alertLevel) {

        final AlertLevelEntity entity = alertLevelEntityConverter.convert(alertLevel);

        return alertLevelEntityConverter.convert(alertLevelRepository.save(entity));
    }
}

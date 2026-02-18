package com.jumia.skylens.domain.impl;

import com.jumia.skylens.domain.SaveAlertLevelUseCase;
import com.jumia.skylens.domain.catalog.AlertLevel;
import com.jumia.skylens.domain.impl.validators.AlertLevelValidator;
import com.jumia.skylens.persistence.api.AlertLevelDAO;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SaveAlertLevelUseCaseImpl implements SaveAlertLevelUseCase {

    private final AlertLevelDAO alertLevelDAO;

    @Override
    public AlertLevel run(final AlertLevel alertLevel) {

        AlertLevelValidator.validate(alertLevel);

        return alertLevelDAO.save(alertLevel);
    }
}

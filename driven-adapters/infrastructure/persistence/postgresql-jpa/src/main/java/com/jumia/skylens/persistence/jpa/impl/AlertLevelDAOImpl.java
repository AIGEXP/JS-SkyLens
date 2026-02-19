package com.jumia.skylens.persistence.jpa.impl;

import com.jumia.skylens.domain.catalog.AlertLevel;
import com.jumia.skylens.domain.catalog.ReportType;
import com.jumia.skylens.persistence.api.AlertLevelDAO;
import com.jumia.skylens.persistence.jpa.converters.AlertLevelEntityConverter;
import com.jumia.skylens.persistence.jpa.converters.ReportTypeEnumConverter;
import com.jumia.skylens.persistence.jpa.entities.AlertLevelEntity;
import com.jumia.skylens.persistence.jpa.entities.AlertLevelEntityId;
import com.jumia.skylens.persistence.jpa.entities.enums.ReportTypeEnum;
import com.jumia.skylens.persistence.jpa.repositories.AlertLevelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AlertLevelDAOImpl implements AlertLevelDAO {

    private final AlertLevelRepository alertLevelRepository;

    private final AlertLevelEntityConverter alertLevelEntityConverter;

    private final ReportTypeEnumConverter reportTypeEnumConverter;

    @Override
    public AlertLevel save(final AlertLevel alertLevel) {

        final AlertLevelEntity entity = alertLevelEntityConverter.convert(alertLevel);

        return alertLevelEntityConverter.convert(alertLevelRepository.save(entity));
    }

    @Override
    public Optional<AlertLevel> findByCountryAndReportType(String country, ReportType reportType) {

        final ReportTypeEnum reportTypeEnum = reportTypeEnumConverter.convert(reportType);

        return alertLevelRepository.findById(AlertLevelEntityId.builder()
                                                     .country(country)
                                                     .reportType(reportTypeEnum)
                                                     .build())
                .map(alertLevelEntityConverter::convert);
    }
}

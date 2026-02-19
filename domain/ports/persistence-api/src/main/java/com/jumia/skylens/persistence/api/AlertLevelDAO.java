package com.jumia.skylens.persistence.api;

import com.jumia.skylens.domain.catalog.AlertLevel;
import com.jumia.skylens.domain.catalog.ReportType;

import java.util.Optional;

public interface AlertLevelDAO {

    AlertLevel save(AlertLevel alertLevel);

    Optional<AlertLevel> findByCountryAndReportType(String country, ReportType reportType);
}

package com.jumia.skylens.persistence.jpa.impl;

import com.jumia.skylens.domain.catalog.CountryThreshold;
import com.jumia.skylens.domain.catalog.ReportType;
import com.jumia.skylens.persistence.api.CountryThresholdDAO;
import com.jumia.skylens.persistence.jpa.converters.CountryThresholdEntityConverter;
import com.jumia.skylens.persistence.jpa.entities.CountryThresholdEntity;
import com.jumia.skylens.persistence.jpa.entities.CountryThresholdEntityId;
import com.jumia.skylens.persistence.jpa.repositories.CountryThresholdRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CountryThresholdDAOImpl implements CountryThresholdDAO {

    private final CountryThresholdRepository countryThresholdRepository;

    private final CountryThresholdEntityConverter countryThresholdEntityConverter;

    @Override
    public CountryThreshold save(final CountryThreshold countryThreshold) {

        final CountryThresholdEntity entity = countryThresholdEntityConverter.convert(countryThreshold);

        final CountryThresholdEntity savedEntity = countryThresholdRepository.save(entity);

        return countryThresholdEntityConverter.convert(savedEntity);
    }

    @Override
    public CountryThreshold findByCountryAndReportType(final String country, final ReportType reportType) {

        final CountryThresholdEntityId entityId = countryThresholdEntityConverter.toEntityId(country, reportType);

        return countryThresholdRepository.findById(entityId)
                .map(countryThresholdEntityConverter::convert)
                .orElse(null);
    }
}

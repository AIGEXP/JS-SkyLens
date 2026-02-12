package com.jumia.skylens.persistence.jpa.impl;

import com.jumia.skylens.domain.catalog.NetworkThreshold;
import com.jumia.skylens.persistence.api.NetworkThresholdDAO;
import com.jumia.skylens.persistence.jpa.converters.NetworkThresholdEntityConverter;
import com.jumia.skylens.persistence.jpa.entities.NetworkThresholdEntity;
import com.jumia.skylens.persistence.jpa.repositories.NetworkThresholdRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NetworkThresholdDAOImpl implements NetworkThresholdDAO {

    private final NetworkThresholdRepository networkThresholdRepository;

    private final NetworkThresholdEntityConverter networkThresholdEntityConverter;

    @Override
    public NetworkThreshold save(final NetworkThreshold networkThreshold) {

        final NetworkThresholdEntity entity = networkThresholdEntityConverter.convert(networkThreshold);

        final NetworkThresholdEntity savedEntity = networkThresholdRepository.save(entity);

        return networkThresholdEntityConverter.convert(savedEntity);
    }
}

package com.jumia.skylens.domain.impl;

import com.jumia.skylens.domain.UpsertNetworkThresholdUseCase;
import com.jumia.skylens.domain.catalog.NetworkThreshold;
import com.jumia.skylens.persistence.api.NetworkThresholdDAO;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UpsertNetworkThresholdUseCaseImpl implements UpsertNetworkThresholdUseCase {

    private final NetworkThresholdDAO networkThresholdDAO;

    @Override
    public NetworkThreshold run(final NetworkThreshold networkThreshold) {

        return networkThresholdDAO.save(networkThreshold);
    }
}

package com.jumia.skylens.domain;

import com.jumia.skylens.domain.catalog.NetworkThreshold;

public interface UpsertNetworkThresholdUseCase {

    NetworkThreshold run(NetworkThreshold networkThreshold);
}

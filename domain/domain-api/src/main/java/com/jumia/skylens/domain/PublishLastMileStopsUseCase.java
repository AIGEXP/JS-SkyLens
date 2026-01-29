package com.jumia.skylens.domain;

import com.jumia.skylens.domain.catalog.StopPublishingFilter;

public interface PublishLastMileStopsUseCase {

    void run(String network);

    void run(StopPublishingFilter stopPublishingFilter);
}

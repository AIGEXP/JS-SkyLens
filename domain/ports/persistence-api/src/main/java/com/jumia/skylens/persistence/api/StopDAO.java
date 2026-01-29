package com.jumia.skylens.persistence.api;

import com.jumia.skylens.domain.catalog.Stop;
import com.jumia.skylens.domain.catalog.StopPublishingFilter;
import com.jumia.skylens.domain.catalog.StopWithPackageQuantity;
import com.jumia.skylens.domain.catalog.pages.PageRequest;

import java.util.List;
import java.util.Optional;

public interface StopDAO {

    Stop save(Stop stop);

    boolean existsByStopHash(String stopHash);

    Optional<Stop> findByStopHash(String stopHash);

    long countStopsForPublishing(StopPublishingFilter stopPublishingFilter);

    List<StopWithPackageQuantity> getStopsForPublishing(StopPublishingFilter stopPublishingFilter, PageRequest pageRequest);

    void markStopAsPublished(long id);
}

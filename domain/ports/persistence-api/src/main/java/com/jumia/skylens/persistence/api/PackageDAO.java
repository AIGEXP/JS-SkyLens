package com.jumia.skylens.persistence.api;

import com.jumia.skylens.domain.catalog.Package;
import com.jumia.skylens.domain.catalog.PackageFilter;
import com.jumia.skylens.domain.catalog.PackageSummary;
import com.jumia.skylens.domain.catalog.pages.PageRequest;

import java.util.List;

public interface PackageDAO {

    void save(Package pkg);

    int countPackagesOfSizeMByStopId(long stopId);

    List<PackageSummary> getPublishedPackages(PageRequest pageRequest, PackageFilter packageFilter);

    boolean existsByTrackingNumberAndStopId(String trackingNumber, long stopId);
}

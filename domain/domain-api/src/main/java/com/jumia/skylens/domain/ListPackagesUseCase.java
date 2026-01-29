package com.jumia.skylens.domain;

import com.jumia.skylens.domain.catalog.PackageFilter;
import com.jumia.skylens.domain.catalog.PackageSummary;
import com.jumia.skylens.domain.catalog.pages.PageRequest;

import java.util.List;

@FunctionalInterface
public interface ListPackagesUseCase {

    List<PackageSummary> run(PackageFilter packageFilter, PageRequest pageRequest);
}

package com.jumia.skylens.domain;

import com.jumia.skylens.domain.catalog.PackageFilter;
import com.jumia.skylens.domain.catalog.pages.PageRequest;
import java.util.UUID;

@FunctionalInterface
public interface ExportAndPublishPackageUseCase {

    void run(UUID partnerSid,
             Long id,
             String uploadTo,
             PackageFilter packageFilter,
             PageRequest pageRequest);
}

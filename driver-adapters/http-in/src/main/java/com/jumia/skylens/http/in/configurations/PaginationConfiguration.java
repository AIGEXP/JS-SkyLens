package com.jumia.skylens.http.in.configurations;

public interface PaginationConfiguration {

    Integer getDefaultLimit();

    Integer getMaxExportLimit();

    Integer getMaxLimit();
}

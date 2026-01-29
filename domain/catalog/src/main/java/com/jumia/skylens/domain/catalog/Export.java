package com.jumia.skylens.domain.catalog;

import lombok.Builder;

@Builder
public record Export(long id, Status status) {

    public enum Status {
        COMPLETED, FAILED, EMPTY
    }
}

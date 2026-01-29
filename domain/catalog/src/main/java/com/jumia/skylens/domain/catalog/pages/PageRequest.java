package com.jumia.skylens.domain.catalog.pages;

import lombok.Value;

@Value(staticConstructor = "of")
public class PageRequest {

    Integer offset;

    Integer limit;
}

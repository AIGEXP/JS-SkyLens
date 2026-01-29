package com.jumia.skylens.commons.converters;

@FunctionalInterface
public interface Option<S, B> {

    void apply(S source, B builder);
}

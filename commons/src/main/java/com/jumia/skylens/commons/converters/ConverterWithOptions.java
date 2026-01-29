package com.jumia.skylens.commons.converters;

import java.util.Set;

@FunctionalInterface
public interface ConverterWithOptions<S, R, B, O extends Option<S, B>> extends Converter<S, R> {

    default void applyOptions(S source, B builder, Set<O> options) {

        if (options == null) {
            return;
        }

        options.forEach(o -> o.apply(source, builder));
    }

    R convert(S source, Set<O> options);

    default R convert(S source, O option) {

        return convert(source, Set.of(option));
    }

    @Override
    default R convert(S source) {

        return convert(source, (Set<O>) null);
    }
}

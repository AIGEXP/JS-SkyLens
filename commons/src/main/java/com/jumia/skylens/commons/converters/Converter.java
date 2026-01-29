package com.jumia.skylens.commons.converters;

public interface Converter<S, R> {

    R convert(S source);

    interface WithTwoSources<S1, S2, R> extends Converter<S1, R> {

        default R convert(S1 source) {

            return convert(source, null);
        }

        R convert(S1 source, S2 source2);
    }

    interface WithThreeSources<S1, S2, S3, R> extends WithTwoSources<S1, S2, R> {

        default R convert(S1 source) {

            return convert(source, null, null);
        }

        default R convert(S1 source1, S2 source2) {

            return convert(source1, source2, null);
        }

        R convert(S1 source, S2 source2, S3 source3);
    }
}

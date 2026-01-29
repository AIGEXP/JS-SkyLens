package com.jumia.skylens.persistence.jpa.repositories.custom.utils;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Visitor;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

@EqualsAndHashCode
public final class OptionalBooleanBuilder implements Predicate {

    @Serial
    private static final long serialVersionUID = 6825574710438110487L;

    private BooleanExpression predicate;

    public <T> OptionalBooleanBuilder optionalAnd(T value, Function<T, BooleanExpression> expressionFunction) {

        if (Objects.nonNull(value)) {
            predicate = Optional.ofNullable(predicate)
                    .map(_ -> predicate.and(expressionFunction.apply(value)))
                    .orElse(expressionFunction.apply(value));
        }
        return this;
    }

    @Override
    public <R, C> R accept(final Visitor<R, C> visitor, final C context) {

        return Optional.ofNullable(predicate)
                .map(_ -> predicate.accept(visitor, context))
                .orElse(null);
    }

    @Override
    public Class<? extends Boolean> getType() {

        return Boolean.class;
    }

    @Override
    public OptionalBooleanBuilder not() {

        if (Objects.nonNull(predicate)) {
            predicate = predicate.not();
        }

        return this;
    }

    public Predicate getValue() {

        return predicate;
    }
}

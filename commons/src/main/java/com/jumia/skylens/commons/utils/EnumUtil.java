package com.jumia.skylens.commons.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class EnumUtil {

    public static final String NULL = "[NULL]";

    public static <T extends Enum<T>> Optional<T> getEnum(Class<T> enumType, String value) {

        if (value == null) {
            return Optional.empty();
        }

        return getEnum(Set.of(enumType.getEnumConstants()), value);
    }

    private static <T extends Enum<T>> Optional<T> getEnum(Set<T> enumClasses, String value) {

        return enumClasses.stream().filter(o -> o.name().equalsIgnoreCase(value)).findFirst();
    }

    public static <T extends Enum<T>> List<T> getEnumList(Class<T> enumType, Collection<String> values) {

        if (values == null) {
            throw invalidParameterException(NULL);
        }
        final Set<T> enumClasses = Set.of(enumType.getEnumConstants());
        return values.stream().map(s -> getEnumOrThrow(enumClasses, s)).toList();
    }

    public static <T extends Enum<T>> List<T> getEnumListOrEmpty(Class<T> enumType, Collection<String> values) {

        if (values == null) {
            return Collections.emptyList();
        }
        return getEnumList(enumType, values);
    }

    public static <T extends Enum<T>> T getEnumOrNull(Class<T> enumType, String value) {

        if (value == null) {
            return null;
        }
        return getEnum(Set.of(enumType.getEnumConstants()), value).orElse(null);
    }

    public static <T extends Enum<T>> T getEnumOrThrow(Class<T> enumType, String value) {

        if (value == null) {
            throw invalidParameterException("[NULL]");
        }

        return getEnumOrThrow(Set.of(enumType.getEnumConstants()), value);
    }

    public static <T extends Enum<T>> Stream<T> getEnumOrThrow(Class<T> enumType, Collection<String> values) {

        if (values == null) {
            throw invalidParameterException("[NULL]");
        }
        final Set<T> enumClasses = Set.of(enumType.getEnumConstants());
        return values.stream().map(s -> getEnumOrThrow(enumClasses, s));
    }

    private static <T extends Enum<T>> T getEnumOrThrow(Set<T> enumClasses, String value) {

        return getEnum(enumClasses, value).orElseThrow(() -> invalidParameterException(value));
    }

    public static <T extends Enum<T>> Set<T> getEnumSet(Class<T> enumType, Collection<String> values) {

        if (values == null) {
            throw invalidParameterException("[NULL]");
        }
        final Set<T> enumClasses = Set.of(enumType.getEnumConstants());
        return values.stream().map(s -> getEnumOrThrow(enumClasses, s)).collect(Collectors.toSet());
    }

    public static <T extends Enum<T>> Set<T> getEnumSetOrEmpty(Class<T> enumType, Collection<String> values) {

        if (values == null) {
            return Collections.emptySet();
        }
        return getEnumSet(enumType, values);
    }

    public static <T extends Enum<T>> Optional<T> getEnumWithCondition(Class<T> enumeration, Predicate<T> condition) {

        for (T enumerationValue : enumeration.getEnumConstants()) {
            if (condition.test(enumerationValue)) {
                return Optional.of(enumerationValue);
            }
        }
        return Optional.empty();
    }

    public static <T extends Enum<T>> List<T> getEnumsListOrEmpty(Class<T> enumeration, Predicate<T> condition) {

        return Stream.of(enumeration.getEnumConstants())
                .filter(condition)
                .toList();
    }

    public static <E, T extends Enum<T> & Identifiable<E>> T getValueFromIdentifiable(Class<T> enumeration, E identifier) {

        if (identifier == null) {
            return null;
        }

        return getEnumWithCondition(enumeration, e -> identifier.equals(e.getIdentifier()))
                .orElseThrow(() -> new UnknownEnumException("No matching type for id " + identifier));
    }

    private static UnknownEnumException invalidParameterException(String value) {

        return new UnknownEnumException(String.format("No matching enum type for name %s", value));
    }

    public static <T extends Enum<T>, R> Set<R> mapEnumTo(Class<T> enumeration, Function<T, R> mapFunction) {

        return Stream.of(enumeration.getEnumConstants())
                .map(mapFunction)
                .collect(Collectors.toSet());
    }

    private static class UnknownEnumException extends RuntimeException {

        @Serial
        private static final long serialVersionUID = -2254098269532422793L;

        UnknownEnumException(String message) {

            super(message);
        }
    }
}

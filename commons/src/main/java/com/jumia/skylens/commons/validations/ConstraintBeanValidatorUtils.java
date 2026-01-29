package com.jumia.skylens.commons.validations;

import com.jumia.skylens.commons.validations.exceptions.ConstraintValidationException;
import com.jumia.skylens.commons.validations.exceptions.Error;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class ConstraintBeanValidatorUtils {

    private static <T> Error buildError(ConstraintViolation<T> errorConstraintViolation) {

        return new Error(errorConstraintViolation.getPropertyPath().toString(),
                         errorConstraintViolation.getMessage(),
                         errorConstraintViolation.getInvalidValue());
    }

    public static <T> Set<ConstraintViolation<T>> checkBean(T bean, Class<?>... groups) {

        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            final Validator validator = factory.getValidator();
            if (groups.length == 0) {
                return validator.validate(bean);
            } else {
                return validator.validate(bean, groups);
            }
        }
    }

    public static <T> void checkErrors(Logger logger, Set<ConstraintViolation<T>> errors, Class<?> clazz) {

        if (!CollectionUtils.isEmpty(errors)) {
            final List<Error> errorList = errors.stream()
                    .map(ConstraintBeanValidatorUtils::buildError)
                    .toList();

            final Set<String> errorMessages = errorList.stream()
                    .map(Error::toString)
                    .collect(Collectors.toSet());

            logger.error("Invalid payload Class:{}: {}", clazz.getSimpleName(), String.join(",", errorMessages));
            throw createException(clazz, errorList, errorMessages);
        }
    }

    public static ConstraintValidationException createException(Class<?> clazz,
                                                                Collection<Error> errors,
                                                                Collection<String> errorMessages) {

        return new ConstraintValidationException(
                ValidationErrorCode.VALIDATION_ERROR,
                String.format("Invalid Payload: %s:. %s", clazz.getSimpleName(), String.join(",", errorMessages)),
                errors);
    }

    public static <T> void validate(Logger logger, T body) {

        try {
            final Set<ConstraintViolation<T>> errors = new HashSet<>(ConstraintBeanValidatorUtils.checkBean(body));
            ConstraintBeanValidatorUtils.checkErrors(logger, errors, body.getClass());
        } catch (Exception exception) {
            logger.warn("Invalid instance values for {}: {}", body.getClass(), body);
            throw exception;
        }
    }
}

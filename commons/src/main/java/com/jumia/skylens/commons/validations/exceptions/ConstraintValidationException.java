package com.jumia.skylens.commons.validations.exceptions;

import com.jumia.skylens.commons.exceptions.CodedException;
import com.jumia.skylens.commons.validations.ValidationErrorCode;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Getter;

import java.io.Serial;
import java.util.Collection;

@Getter
@SuppressFBWarnings(value = {"EI_EXPOSE_REP"}, justification = "I prefer to suppress these FindBugs warnings")
public class ConstraintValidationException extends CodedException {

    @Serial
    private static final long serialVersionUID = 8816061848321265898L;

    private final Collection<Error> errors;

    public ConstraintValidationException(ValidationErrorCode errorCode, String message, Collection<Error> errors) {

        super(errorCode, message);
        this.errors = errors;
    }
}

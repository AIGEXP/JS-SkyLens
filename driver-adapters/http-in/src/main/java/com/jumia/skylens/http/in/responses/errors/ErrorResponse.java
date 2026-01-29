package com.jumia.skylens.http.in.responses.errors;

import com.jumia.skylens.commons.validations.exceptions.Error;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Builder;
import lombok.Value;

import java.util.Collection;

@Value
@Builder
@SuppressFBWarnings(value = { "EI_EXPOSE_REP" }, justification = "I prefer to suppress these FindBugs warnings")
public class ErrorResponse {

    int code;

    String codeName;

    String message;

    Collection<Error> errors;

    Object extraInfo;
}

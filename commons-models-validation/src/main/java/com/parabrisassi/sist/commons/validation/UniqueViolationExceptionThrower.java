package com.parabrisassi.sist.commons.validation;

import com.parabrisassi.sist.commons.errors.UniqueViolationError;
import com.parabrisassi.sist.commons.exceptions.UniqueViolationException;

import java.util.List;

/**
 * Created by Juan Marcos Bellini on 10/2/18.
 * Questions at jbellini@bellotapps.com
 */
public interface UniqueViolationExceptionThrower {

    /* package */ UniqueViolationValidator UNIQUE_VIOLATION_VALIDATOR = new UniqueViolationValidator();

    default void throwIfNotEmpty(List<UniqueViolationError> errorList) throws UniqueViolationException {
        UNIQUE_VIOLATION_VALIDATOR.throwIfNotEmpty(errorList);
    }
}

package com.parabrisassi.sist.commons.validation;

import com.parabrisassi.sist.commons.errors.ValidationError;
import com.parabrisassi.sist.commons.exceptions.ValidationException;

import java.util.List;

/**
 * Created by Juan Marcos Bellini on 10/2/18.
 * Questions at jbellini@bellotapps.com
 */
public interface ValidationExceptionThrower {

    /* package */ ValidationValidator VALIDATION_VALIDATOR = new ValidationValidator();

    default void throwIfNotEmpty(List<ValidationError> errorList) throws ValidationException {
        VALIDATION_VALIDATOR.throwIfNotEmpty(errorList);
    }
}

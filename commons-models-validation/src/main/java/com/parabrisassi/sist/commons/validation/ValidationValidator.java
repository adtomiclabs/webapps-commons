package com.parabrisassi.sist.commons.validation;

import com.parabrisassi.sist.commons.errors.ValidationError;
import com.parabrisassi.sist.commons.exceptions.ValidationException;

import java.util.List;

/**
 * An object extending {@link ErrorListValidator},
 * using {@link ValidationError} as {@link com.parabrisassi.sist.commons.errors.EntityError},
 * and {@link ValidationException} as {@link RuntimeException}.
 */
/* package */ class ValidationValidator extends ErrorListValidator<ValidationException, ValidationError> {

    @Override
    /* package */ ValidationException provideException(List<ValidationError> errorList) {
        return new ValidationException(errorList);
    }
}

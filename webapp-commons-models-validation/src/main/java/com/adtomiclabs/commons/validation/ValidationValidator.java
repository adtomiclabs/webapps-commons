package com.adtomiclabs.commons.validation;

import com.adtomiclabs.commons.errors.ValidationError;
import com.adtomiclabs.commons.exceptions.ValidationException;

import java.util.List;

/**
 * An object extending {@link ErrorListValidator},
 * using {@link ValidationError} as {@link com.adtomiclabs.commons.errors.EntityError},
 * and {@link ValidationException} as {@link RuntimeException}.
 */
/* package */ class ValidationValidator extends ErrorListValidator<ValidationException, ValidationError> {

    @Override
    /* package */ ValidationException provideException(List<ValidationError> errorList) {
        return new ValidationException(errorList);
    }
}

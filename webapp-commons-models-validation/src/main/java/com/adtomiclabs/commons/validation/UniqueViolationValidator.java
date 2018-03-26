package com.adtomiclabs.commons.validation;

import com.adtomiclabs.commons.errors.UniqueViolationError;
import com.adtomiclabs.commons.exceptions.UniqueViolationException;

import java.util.List;

/**
 * An object extending {@link ErrorListValidator},
 * using {@link UniqueViolationError} as {@link com.adtomiclabs.commons.errors.EntityError},
 * and {@link UniqueViolationException} as {@link RuntimeException}.
 */
/* package */ class UniqueViolationValidator extends ErrorListValidator<UniqueViolationException, UniqueViolationError> {

    @Override
    /* package */ UniqueViolationException provideException(List<UniqueViolationError> errorList) {
        return new UniqueViolationException(errorList);
    }
}

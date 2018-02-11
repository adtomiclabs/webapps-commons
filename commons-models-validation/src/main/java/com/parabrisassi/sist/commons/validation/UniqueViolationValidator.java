package com.parabrisassi.sist.commons.validation;

import com.parabrisassi.sist.commons.errors.UniqueViolationError;
import com.parabrisassi.sist.commons.exceptions.UniqueViolationException;

import java.util.List;

/**
 * An object extending {@link ErrorListValidator},
 * using {@link UniqueViolationError} as {@link com.parabrisassi.sist.commons.errors.EntityError},
 * and {@link UniqueViolationException} as {@link RuntimeException}.
 */
/* package */ class UniqueViolationValidator extends ErrorListValidator<UniqueViolationException, UniqueViolationError> {

    @Override
    /* package */ UniqueViolationException provideException(List<UniqueViolationError> errorList) {
        return new UniqueViolationException(errorList);
    }
}

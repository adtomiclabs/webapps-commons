package com.adtomiclabs.commons.validation;

import com.adtomiclabs.commons.errors.UniqueViolationError;
import com.adtomiclabs.commons.exceptions.UniqueViolationException;

import java.util.List;

/**
 * A wrapper interface for {@link UniqueViolationValidator}, in order to avoid instantiation boilerplate.
 */
public interface UniqueViolationExceptionThrower {

    /**
     * The {@link UniqueViolationValidator} being wrapped.
     */
    /* package */ UniqueViolationValidator UNIQUE_VIOLATION_VALIDATOR = new UniqueViolationValidator();

    /**
     * Throws a {@link UniqueViolationException} in case the given {@code errorList} is not empty.
     *
     * @param errorList The {@link List} to be checked if it's not empty.
     * @throws UniqueViolationException In case the given {@code errorList} is not empty.
     */
    default void throwIfNotEmpty(List<UniqueViolationError> errorList) throws UniqueViolationException {
        UNIQUE_VIOLATION_VALIDATOR.throwIfNotEmpty(errorList);
    }
}

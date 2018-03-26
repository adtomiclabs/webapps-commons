package com.adtomiclabs.commons.validation;

import com.adtomiclabs.commons.errors.ValidationError;
import com.adtomiclabs.commons.exceptions.ValidationException;

import java.util.List;

/**
 * A wrapper interface for {@link ValidationValidator}, in order to avoid instantiation boilerplate.
 */
public interface ValidationExceptionThrower {

    /**
     * The {@link ValidationValidator} being wrapped.
     */
    /* package */ ValidationValidator VALIDATION_VALIDATOR = new ValidationValidator();

    /**
     * Throws a {@link ValidationException} in case the given {@code errorList} is not empty.
     *
     * @param errorList The {@link List} to be checked if it's not empty.
     * @throws ValidationException In case the given {@code errorList} is not empty.
     */
    default void throwIfNotEmpty(List<ValidationError> errorList) throws ValidationException {
        VALIDATION_VALIDATOR.throwIfNotEmpty(errorList);
    }
}

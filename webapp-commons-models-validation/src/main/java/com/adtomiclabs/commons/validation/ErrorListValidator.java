package com.adtomiclabs.commons.validation;

import com.adtomiclabs.commons.errors.EntityError;
import org.springframework.util.Assert;

import java.util.List;

/**
 * A helper abstract class which encapsulates logic to throw a given {@link RuntimeException}
 * in case a given {@link List} of {@link EntityError} is not empty.
 */
/* package */ abstract class ErrorListValidator<E extends RuntimeException, T extends EntityError> {


    /**
     * Throws a {@link RuntimeException} in case the given {@code errorList} is not empty.
     *
     * @param errorList The {@link List} to be checked if it's not empty.
     * @throws E In case the given {@code errorList} is not empty.
     */
    /* package */ void throwIfNotEmpty(List<T> errorList) throws E {
        Assert.notNull(errorList, "The error list must not be null");
        if (!errorList.isEmpty()) {
            throw provideException(errorList);
        }
    }

    /**
     * Method to be overridden, which provides the {@link RuntimeException} to be thrown
     * in case the {@link #throwIfNotEmpty(List)} must throw an exception.
     *
     * @param errorList A {@link List} of {@link EntityError} to be used by the resulting exception.
     * @return The provided exception.
     */
    /* package */
    abstract E provideException(List<T> errorList);
}

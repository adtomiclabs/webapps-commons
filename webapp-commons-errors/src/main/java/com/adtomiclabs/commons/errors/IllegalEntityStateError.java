package com.adtomiclabs.commons.errors;

/**
 * Class representing an error that occurs when operating over an entity in an invalid state
 * (to perform that operation).
 */
public class IllegalEntityStateError extends MultiFieldError {

    /**
     * Constructor.
     *
     * @param message                A human-readable message to be used for debugging purposes.
     * @param illegalStateProperties The properties of the entity that have illegal state.
     */
    public IllegalEntityStateError(String message, String... illegalStateProperties) {
        super(message, illegalStateProperties);
    }
}

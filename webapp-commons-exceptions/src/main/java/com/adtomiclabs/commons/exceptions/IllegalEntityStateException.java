package com.adtomiclabs.commons.exceptions;

import com.adtomiclabs.commons.errors.IllegalEntityStateError;

/**
 * {@link RuntimeException} thrown when trying to perform an operation over an entity
 * when the state of it is invalid for that operation.
 */
public class IllegalEntityStateException extends RuntimeException {

    /**
     * The {@link IllegalEntityStateError} that caused this exception to be thrown.
     */
    private final IllegalEntityStateError error;

    /**
     * Default constructor.
     *
     * @param error The {@link IllegalEntityStateError} that caused this exception to be thrown.
     */
    public IllegalEntityStateException(IllegalEntityStateError error) {
        super();
        this.error = error;
    }

    /**
     * Constructor which can set a {@code message}.
     *
     * @param message The detail message, which is saved for later retrieval by the {@link #getMessage()} method.
     * @param error   The {@link IllegalEntityStateError} that caused this exception to be thrown.
     */
    public IllegalEntityStateException(IllegalEntityStateError error, String message) {
        super(message);
        this.error = error;
    }

    /**
     * Constructor which can set a {@code message} and a {@code cause}.
     *
     * @param message The detail message, which is saved for later retrieval by the {@link #getMessage()} method.
     * @param cause   The cause (which is saved for later retrieval by the {@link #getCause()} method).
     *                For more information, see {@link RuntimeException#RuntimeException(Throwable)}.
     * @param error   The {@link IllegalEntityStateError} that caused this exception to be thrown.
     */
    public IllegalEntityStateException(IllegalEntityStateError error, String message, Throwable cause) {
        super(message, cause);
        this.error = error;
    }

    /**
     * @return The {@link IllegalEntityStateError} that caused this exception to be thrown.
     */
    public IllegalEntityStateError getError() {
        return error;
    }
}

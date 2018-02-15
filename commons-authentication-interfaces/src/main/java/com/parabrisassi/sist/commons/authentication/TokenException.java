package com.parabrisassi.sist.commons.authentication;

import com.parabrisassi.sist.commons.exceptions.UnauthenticatedException;

/**
 * Exception to be thrown when there is any problem with a token (i.e decoding, invalid, blacklisted, etc.).
 */
public class TokenException extends UnauthenticatedException {

    /**
     * Default constructor.
     */
    public TokenException() {
        super();
    }

    /**
     * Constructor which can set a {@code message}.
     *
     * @param message The detail message, which is saved for later retrieval by the {@link #getMessage()} method.
     */
    public TokenException(String message) {
        super(message);
    }

    /**
     * Constructor which can set a mes{@code message} and a {@code cause}.
     *
     * @param message The detail message, which is saved for later retrieval by the {@link #getMessage()} method.
     * @param cause   The cause (which is saved for later retrieval by the {@link #getCause()} method).
     *                For more information, see {@link RuntimeException#RuntimeException(Throwable)}.
     */
    public TokenException(String message, Throwable cause) {
        super(message, cause);
    }
}

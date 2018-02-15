package com.parabrisassi.sist.commons.authentication;

/**
 * Exception thrown when there are token decoding issues.
 */
/* package */ final class TokenDecodingException extends TokenException {

    /**
     * Default constructor.
     */
    /* package */ TokenDecodingException() {
        super();
    }

    /**
     * Constructor which can set a {@code message}.
     *
     * @param message The detail message, which is saved for later retrieval by the {@link #getMessage()} method.
     */
    /* package */ TokenDecodingException(String message) {
        super(message);
    }

    /**
     * Constructor which can set a mes{@code message} and a {@code cause}.
     *
     * @param message The detail message, which is saved for later retrieval by the {@link #getMessage()} method.
     * @param cause   The cause (which is saved for later retrieval by the {@link #getCause()} method).
     *                For more information, see {@link RuntimeException#RuntimeException(Throwable)}.
     */
    /* package */ TokenDecodingException(String message, Throwable cause) {
        super(message, cause);
    }
}

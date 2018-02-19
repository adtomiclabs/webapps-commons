package com.parabrisassi.sist.commons.authentication;

import org.springframework.security.core.AuthenticationException;

/**
 * Exception to be thrown in case the set authentication scheme is not supported.
 */
/* package */ class UnsupportedAuthenticationSchemeException extends AuthenticationException {

    /**
     * Default constructor.
     */
    /* package */ UnsupportedAuthenticationSchemeException(String scheme) {
        super("The set scheme (" + scheme + ") is not supported");
    }
}

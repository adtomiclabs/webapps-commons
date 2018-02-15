package com.parabrisassi.sist.commons.authentication;

/**
 * Defines behaviour of the service in charge of managing authentication tokens.
 * Defines behavior for an object that can decode a token from a {@link String}.
 */
@FunctionalInterface
public interface AuthenticationTokenDecoder {

    /**
     * Retrieves {@link TokenData} from a {@link String} representation of it.
     *
     * @param encodedToken The encoded token.
     * @return {@link TokenData} taken from the given raw token.
     * @throws TokenException In case the token is not valid.
     */
    TokenData decode(String encodedToken) throws TokenException;
}

package com.adtomiclabs.commons.authentication;

/**
 * Defines behaviour of the service in charge of providing a {@link TokenData} instance,
 * built from a raw token (represented by a {@link String}).
 */
@FunctionalInterface
public interface AuthenticationTokenDataProvider {

    /**
     * Retrieves {@link TokenData} from a {@link String} representation of it,
     * throwing a {@link TokenException} in case the raw token is not valid
     * (i.e malformation, expired, blacklisted, etc).
     *
     * @param encodedToken The encoded token.
     * @return {@link TokenData} taken from the given raw token.
     * @throws TokenException In case the token is not valid.
     */
    TokenData provide(String encodedToken) throws TokenException;
}

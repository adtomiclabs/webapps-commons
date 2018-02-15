package com.parabrisassi.sist.commons.authentication;

/**
 * Defines behaviour for an object that can tell if a token is valid (i.e not blacklisted),
 * from a given {@link TokenData} instance.
 */
@FunctionalInterface
public interface AuthenticationTokenBlacklistedChecker {

    /**
     * Indicates whether a token (represented by a {@link TokenData} instance) is valid (i.e not blacklisted).
     *
     * @param tokenId The id of the token to be checked.
     * @return {@code true} if the token is valid, or {@code false} otherwise.
     */
    boolean isBlacklisted(long tokenId);
}

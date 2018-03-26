package com.adtomiclabs.commons.authentication;

/**
 * Concrete implementation of {@link AuthenticationTokenDataProvider}.
 */
public class JwtAuthenticationTokenDataProvider implements AuthenticationTokenDataProvider {

    /**
     * An {@link AuthenticationTokenDecoder} in charge of decoding a raw token.
     */
    private final AuthenticationTokenDecoder authenticationTokenDecoder;

    /**
     * An {@link AuthenticationTokenBlacklistedChecker} in charge of checking if the token is not blacklisted.
     */
    private final AuthenticationTokenBlacklistedChecker authenticationTokenBlacklistedChecker;

    /**
     * Constructor.
     *
     * @param authenticationTokenDecoder            An {@link AuthenticationTokenDecoder}
     *                                              in charge of decoding a raw token.
     * @param authenticationTokenBlacklistedChecker An {@link AuthenticationTokenBlacklistedChecker}
     *                                              in charge of checking if the token is not blacklisted.
     */
    public JwtAuthenticationTokenDataProvider(AuthenticationTokenDecoder authenticationTokenDecoder,
                                              AuthenticationTokenBlacklistedChecker
                                                      authenticationTokenBlacklistedChecker) {
        this.authenticationTokenDecoder = authenticationTokenDecoder;
        this.authenticationTokenBlacklistedChecker = authenticationTokenBlacklistedChecker;
    }

    @Override
    public TokenData provide(String encodedToken) throws TokenException {
        final TokenData tokenData = authenticationTokenDecoder.decode(encodedToken);
        if (tokenData == null || !authenticationTokenBlacklistedChecker.isBlacklisted(tokenData.getId())) {
            throw new TokenException("Blacklisted token");
        }
        return tokenData;
    }
}

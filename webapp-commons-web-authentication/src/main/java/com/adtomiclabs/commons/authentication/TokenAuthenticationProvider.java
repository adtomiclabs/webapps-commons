package com.adtomiclabs.commons.authentication;


import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.util.Assert;

/**
 * {@link AuthenticationProvider} in charge of performing token authentication.
 */
public final class TokenAuthenticationProvider implements AuthenticationProvider {

    /**
     * The {@link AuthenticationTokenDataProvider} that will provide a {@link TokenData} instance from a raw token.
     */
    private final AuthenticationTokenDataProvider authenticationTokenDataProvider;

    /**
     * Constructor.
     *
     * @param authenticationTokenDataProvider The {@link AuthenticationTokenDataProvider}
     *                                        that will provide a {@link TokenData} instance from a raw token.
     */
    public TokenAuthenticationProvider(AuthenticationTokenDataProvider authenticationTokenDataProvider) {
        this.authenticationTokenDataProvider = authenticationTokenDataProvider;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.notNull(authentication, "The authentication must not be null");
        Assert.isInstanceOf(RawAuthenticationToken.class, authentication,
                "The authentication must be a RawAuthenticationToken");

        final RawAuthenticationToken rawAuthenticationToken = (RawAuthenticationToken) authentication;
        try {
            // Throws TokenException in case the token is not valid
            final TokenData tokenData = authenticationTokenDataProvider.provide(rawAuthenticationToken.getToken());

            // We create a new token with the needed data (username, roles, etc.)
            final AuthenticationTokenAdapter resultToken =
                    new AuthenticationTokenAdapter(tokenData.getUsername(), tokenData.getRoles());
            resultToken.authenticate();

            return resultToken;
        } catch (TokenException e) {
            throw new FailedTokenAuthenticationException(e);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return RawAuthenticationToken.class.isAssignableFrom(authentication);
    }
}

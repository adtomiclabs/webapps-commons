package com.adtomiclabs.commons.authentication;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static com.adtomiclabs.commons.authentication.AuthenticationConstants.AUTHENTICATION_HEADER;
import static com.adtomiclabs.commons.authentication.AuthenticationConstants.AUTHENTICATION_SCHEME;

/**
 * {@link AbstractAuthenticationProcessingFilter} in charge of performing token authentication.
 */
public class TokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    /**
     * A {@link List} of {@link RequestMatcher} for optional authentication requests.
     */
    private final List<RequestMatcher> optionalAuthenticationMatchers;

    /**
     * Constructor.
     *
     * @param tokenAuthenticationFailureHandler The {@link RequestMatcher} for the endpoint
     *                                          to which token validation is performed against to.
     */
    public TokenAuthenticationFilter(TokenAuthenticationFailureHandler tokenAuthenticationFailureHandler) {
        super("/**");
        Assert.notNull(tokenAuthenticationFailureHandler, "A token authentication failure handler must be set");

        this.optionalAuthenticationMatchers = new LinkedList<>();
        this.setAuthenticationFailureHandler(tokenAuthenticationFailureHandler);
        this.setAuthenticationSuccessHandler((request, response, authentication) -> {
            // Do nothing
        });
    }

    /**
     * Adds all of the {@link RequestMatcher} in the given {@link List} of {@code matchers}
     * to the list of optional authentication matchers.
     *
     * @param matchers The {@link List} of {@link RequestMatcher} to be added.
     */
    public void addOptionalAuthenticationMatcher(List<RequestMatcher> matchers) {
        optionalAuthenticationMatchers.addAll(matchers);
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        final AuthenticationManager authenticationManager = getAuthenticationManager();
        Assert.notNull(authenticationManager, "authenticationManager must be specified");

        final String authorizationHeader = request.getHeader(AUTHENTICATION_HEADER);
        if (!StringUtils.hasText(authorizationHeader)) {
            // Anonymous request. Continue only if it matches the optionalAuthenticationMatcher
            if (matchesAny(request)) {
                return new AnonymousAuthenticationToken("ANONYMOUS", "ANONYMOUS",
                        Collections.singletonList(new SimpleGrantedAuthority("ANONYMOUS")));
            }
            throw new UnsupportedAnonymousAuthenticationException();
        }

        // If reached here, authentication credentials are present and, at least, the scheme is present
        final String[] credentials = authorizationHeader.split(" ");
        // We must check the scheme
        final String scheme = credentials[0];
        if (!AUTHENTICATION_SCHEME.equals(scheme)) {
            throw new UnsupportedAuthenticationSchemeException(AUTHENTICATION_HEADER);
        }

        // If reached here, the scheme is supported. We must check the token is present
        if (credentials.length <= 1) {
            throw new MissingTokenException();
        }

        // If reached here, the token is present. We assume everything is well formed (token is just one "word").
        final String rawToken = credentials[1];

        return authenticationManager.authenticate(new RawAuthenticationToken(rawToken));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);

        // Continue with normal flow
        chain.doFilter(request, response);
    }

    @Override
    public void afterPropertiesSet() {
        // Do nothing (and avoid crashing because of the authenticationManager not being set during bean initialization)
    }

    /**
     * Checks if any of the optional {@link RequestMatcher} in the {@link #optionalAuthenticationMatchers}
     * matches the given {@code request}
     *
     * @param request The {@link HttpServletRequest} to be matched.
     * @return {@code true} if the request matches any of the optional {@link RequestMatcher}s,
     * or {@code false} otherwise.
     */
    private boolean matchesAny(HttpServletRequest request) {
        return this.optionalAuthenticationMatchers.stream().anyMatch(matcher -> matcher.matches(request));
    }
}

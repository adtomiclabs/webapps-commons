package com.parabrisassi.sist.commons.authentication;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.List;

/**
 * Abstract web security configuration class.
 */
public abstract class AbstractWebSecurityConfig extends WebSecurityConfigurerAdapter implements InitializingBean {

    /**
     * The {@link TokenAuthenticationFilter} to which the request will be delegated.
     */
    private final TokenAuthenticationFilter tokenAuthenticationFilter;

    /**
     * The {@link TokenAuthenticationProvider} which will perform the authentication step in the filter chain.
     */
    private final TokenAuthenticationProvider tokenAuthenticationProvider;

    /**
     * Constructor.
     *
     * @param tokenAuthenticationFilter   The {@link TokenAuthenticationFilter} to which the request will be delegated.
     * @param tokenAuthenticationProvider The {@link TokenAuthenticationProvider}
     *                                    which will perform the authentication step in the filter chain.
     */
    protected AbstractWebSecurityConfig(TokenAuthenticationFilter tokenAuthenticationFilter,
                                        TokenAuthenticationProvider tokenAuthenticationProvider) {
        this.tokenAuthenticationFilter = tokenAuthenticationFilter;
        this.tokenAuthenticationProvider = tokenAuthenticationProvider;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.tokenAuthenticationFilter.setAuthenticationManager(this.authenticationManager());
        this.tokenAuthenticationFilter.addOptionalAuthenticationMatcher(optionalAuthenticationMatchers());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(tokenAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .rememberMe().disable()
                .logout().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * Returns a {@link List} of {@link RequestMatcher}s that will be used to know if the request can be made
     * anonymously
     *
     * @return The {@link List} of {@link RequestMatcher}s.
     */
    protected abstract List<RequestMatcher> optionalAuthenticationMatchers();
}

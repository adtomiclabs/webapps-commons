package com.adtomiclabs.commons.authentication;

import com.bellotapps.utils.error_handler.ErrorHandler;
import com.bellotapps.utils.error_handler.ErrorHandlerFactory;
import com.adtomiclabs.commons.data_transfer.json.ApiObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

/**
 * Configuration class, which defines several beans to be used by the token authentication mechanism.
 * An {@link ApiObjectMapper}, an {@link ErrorHandler}, and a {@link AuthenticationTokenDataProvider} beans
 * must be defined the same context.
 *
 * @see ErrorHandler
 * @see ApiObjectMapper
 * @see AuthenticationTokenDataProvider
 * @see com.bellotapps.utils.error_handler.EnableErrorHandler
 * @see EnableTokenAuthentication
 * @see AbstractWebSecurityConfig
 */
@Configuration
public class TokenAuthenticationConfigurer implements BeanFactoryAware, BeanClassLoaderAware {

    private final static Logger LOGGER = LoggerFactory.getLogger(TokenAuthenticationConfigurer.class);

    /**
     * The {@link BeanFactory} used to build an {@link ErrorHandlerFactory}.
     */
    private BeanFactory beanFactory;

    /**
     * The {@link ClassLoader} used to build an {@link ErrorHandlerFactory}.
     */
    private ClassLoader classLoader;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Bean
    @ConditionalOnMissingBean
    public ApiObjectMapper mockedApiObjectMapper() {
        LOGGER.warn("No ApiObjectMapper bean found in context. Using mocked mapper.");
        return new ApiObjectMapper();
    }

    @Bean
    @ConditionalOnMissingBean
    public ErrorHandler mockedErrorHandler() {
        if (beanFactory == null || classLoader == null) {
            throw new IllegalStateException("Missing BeanFactory or ClassLoader");
        }
        LOGGER.warn("No ErrorHandler found in context. " +
                "Using mocked handler, " +
                "which uses only the error handlers defined by the webapp-commons-error-handlers library.");
        return new ErrorHandlerFactory(classLoader, beanFactory)
                .createErrorHandler("com.adtomiclabs.commons.commons.error_handlers");
    }

    @Bean
    @ConditionalOnMissingBean
    public AuthenticationTokenDataProvider mockedTokenDataProvider() {
        LOGGER.warn("No AuthenticationTokenDataProvider found in context. " +
                "Using mocked provider, which will not hand-in any useful data from the raw token.");
        return rawToken -> new TokenData(-1, "", Collections.emptyList());
    }

    @Bean
    @ConditionalOnMissingBean
    public TokenAuthenticationProvider tokenAuthenticationProvider(AuthenticationTokenDataProvider provider) {
        return new TokenAuthenticationProvider(provider);
    }

    @Bean
    @ConditionalOnMissingBean
    public TokenAuthenticationFilter tokenAuthenticationFilter(TokenAuthenticationFailureHandler handler) {
        return new TokenAuthenticationFilter(handler);
    }

    @Bean
    @Autowired
    @ConditionalOnMissingBean
    public TokenAuthenticationFailureHandler tokenAuthenticationFailureHandler(ErrorHandler errorHandler,
                                                                               ApiObjectMapper objectMapper) {
        return new TokenAuthenticationFailureHandler(errorHandler, objectMapper);
    }
}

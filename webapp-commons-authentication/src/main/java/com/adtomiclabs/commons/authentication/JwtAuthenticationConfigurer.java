package com.adtomiclabs.commons.authentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Base64Utils;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

/**
 * Configuration class for the jwt authentication system.
 * In order to be used, an {@link AuthenticationTokenBlacklistedChecker} bean must be defined within the scope,
 * and the "com.adtomiclabs.commons.authentication.jwt.key.public" property must be set.
 */
@Configuration
public class JwtAuthenticationConfigurer {

    /**
     * The {@link Logger} object.
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationConfigurer.class);

    @Bean
    @ConditionalOnMissingBean
    public KeyFactory keyFactory() throws NoSuchAlgorithmException {
        return KeyFactory.getInstance(JwtAuthenticationTokenConstants.KEY_FACTORY_ALGORITHM);
    }

    @Bean
    @Autowired
    @ConditionalOnMissingBean
    public AuthenticationTokenDecoder authenticationTokenDecoder(KeyFactory keyFactory,
                                                                 @Value("${com.adtomiclabs.commons.authentication" +
                                                                         ".jwt.key.public}") String publicKeyString)
            throws InvalidKeySpecException {
        final X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(Base64Utils.decodeFromString(publicKeyString));
        final PublicKey publicKey = keyFactory.generatePublic(keySpecX509);
        return new JwtAuthenticationTokenDecoder(publicKey);
    }

    @Bean
    @Autowired
    @ConditionalOnMissingBean
    public AuthenticationTokenDataProvider authenticationTokenDataProvider(AuthenticationTokenDecoder decoder,
                                                                           AuthenticationTokenBlacklistedChecker
                                                                                   checker) {
        return new JwtAuthenticationTokenDataProvider(decoder, checker);
    }

    @Bean
    @ConditionalOnMissingBean
    public AuthenticationTokenBlacklistedChecker mockedChecker() {
        LOGGER.warn("No AuthenticationTokenBlacklistedChecker bean found. " +
                "Using mocked checker, which will state that all tokens are blacklisted. " +
                "Authentication won't work as expected");
        return id -> true; // This mocked checker is used to set up the context, but it MUST be overridden
    }
}

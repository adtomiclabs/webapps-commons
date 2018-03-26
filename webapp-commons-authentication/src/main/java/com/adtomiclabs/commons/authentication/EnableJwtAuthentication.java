package com.adtomiclabs.commons.authentication;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Enables the jwt authentication system, by importing needed beans into the scope.
 * In order to be used, an {@link AuthenticationTokenBlacklistedChecker} bean must be defined,
 * and the "com.adtomiclabs.commons.authentication.jwt.key.public" property must be set.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Import(JwtAuthenticationConfigurer.class)
public @interface EnableJwtAuthentication {
}

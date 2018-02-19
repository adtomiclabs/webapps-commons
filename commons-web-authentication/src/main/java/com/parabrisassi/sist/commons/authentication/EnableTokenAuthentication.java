package com.parabrisassi.sist.commons.authentication;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Enables the token authentication configuration, creating several beans for this process.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Import(TokenAuthenticationConfigurer.class)
public @interface EnableTokenAuthentication {
}

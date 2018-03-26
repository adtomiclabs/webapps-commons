package com.adtomiclabs.commons.config;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import javax.ws.rs.ext.Provider;
import java.lang.annotation.*;

/**
 * Indicates that a class is a Jersey Controller.
 * <p>
 * This annotation serves as a specialization of {@link Component},
 * allowing for implementation classes to be autodetected through classpath scanning,
 * and {@link Provider}, allowing annotated classes to be detected for registering by package registration by Jersey.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Provider
@Component
public @interface JerseyController {

    /**
     * Alias for {@link Component#value()} attribute.
     *
     * @return The same as {@link Component#value()}.
     */
    @AliasFor(annotation = Component.class, attribute = "value")
    String value() default "";
}

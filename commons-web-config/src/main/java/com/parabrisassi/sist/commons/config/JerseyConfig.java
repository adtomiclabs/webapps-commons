package com.parabrisassi.sist.commons.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.ClassUtils;

import javax.ws.rs.ext.Provider;
import java.util.Arrays;
import java.util.Collection;

/**
 * Jersey configuration class, an extension of {@link ResourceConfig}.
 */
public class JerseyConfig extends ResourceConfig {

    /**
     * Constructor.
     *
     * @param objectMapper    The {@link ObjectMapper} to be used by Jersey.
     * @param throwableMapper The {@link ThrowableMapper} to be used by Jersey,
     *                        in order to bypass all {@link Throwable}s
     *                        to the {@link com.bellotapps.utils.error_handler.ErrorHandler}.
     * @param packages        Packages to be scanned for Jersey {@link Provider}s.
     */
    public JerseyConfig(ObjectMapper objectMapper, ThrowableMapper throwableMapper, String... packages) {
        // Register packages with resources and providers
        registerPackages(packages);
        // Register ObjectMapper which will be used to serialize/deserialize JSON
        register(new JacksonJaxbJsonProvider(objectMapper, JacksonJaxbJsonProvider.DEFAULT_ANNOTATIONS));
        // Register the ThrowableMapper that will wire the exception into the error handler
        register(throwableMapper);
    }

    /**
     * Registers the classes annotated with the {@link Provider} annotation in the given {@code packages}.
     * This allows package scanning with Jersey (as currently not supported by library).
     *
     * @param packages The packages containing providers.
     */
    private void registerPackages(String... packages) {
        // Register packages of in app Providers
        final ClassPathScanningCandidateComponentProvider scanner =
                new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(Provider.class));

        Arrays.stream(packages)
                .map(scanner::findCandidateComponents).flatMap(Collection::stream)
                .map(beanDefinition ->
                        ClassUtils.resolveClassName(beanDefinition.getBeanClassName(), this.getClassLoader()))
                .forEach(this::register);
    }
}

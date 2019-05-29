package com.adtomiclabs.commons.config;

import io.swagger.jaxrs.config.BeanConfig;
//import io.swagger.jaxrs.listing.ApiListingResource;
//import io.swagger.jaxrs.listing.SwaggerSerializers;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import io.swagger.jaxrs.config.SwaggerConfigLocator;
import io.swagger.jaxrs.config.SwaggerContextService;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;
import io.swagger.models.Contact;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import javax.annotation.PostConstruct;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.ext.Provider;
import java.util.Arrays;
import java.util.Collection;

/**
 * Jersey configuration class, an extension of {@link ResourceConfig}.
 */
@Component
@ApplicationPath("/api")
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
        register(ApiListingResource.class);
        register(SwaggerSerializers.class);

        BeanConfig swaggerConfigBean = new BeanConfig();
        swaggerConfigBean.setConfigId("Adtomic API");
        swaggerConfigBean.setTitle("Adtomic API REST");
        swaggerConfigBean.setVersion("v1");
        swaggerConfigBean.setContact("Adtomic IT");
        swaggerConfigBean.setSchemes(new String[] { "http", "https" });
        swaggerConfigBean.setBasePath("/api");
        swaggerConfigBean.setResourcePackage("com.adtomiclabs.admin.backend.web.controller.rest_endpoints");
        swaggerConfigBean.setPrettyPrint(true);
        swaggerConfigBean.setScan(true);
        SwaggerConfigLocator.getInstance().putConfig(SwaggerContextService.CONFIG_ID_DEFAULT, swaggerConfigBean);
        packages(getClass().getPackage().getName(), ApiListingResource.class.getPackage().getName());


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

//    @PostConstruct
//    public void init() {
//        // Register components where DI is needed
//        this.SwaggerConfig();
//    }
//    private void SwaggerConfig() {
//        this.register(ApiListingResource.class);
//        this.register(SwaggerSerializers.class);
//
//        BeanConfig swaggerConfigBean = new BeanConfig();
//        swaggerConfigBean.setConfigId("Frugalis Swagger Jersey Example");
//        swaggerConfigBean.setTitle("Using Swagger ,Jersey And Spring Boot ");
//        swaggerConfigBean.setVersion("v1");
//        swaggerConfigBean.setContact("frugalisAdmin");
//        swaggerConfigBean.setSchemes(new String[] { "http", "https" });
//        swaggerConfigBean.setBasePath("/");
//        swaggerConfigBean.setResourcePackage(packages[0]);
//        swaggerConfigBean.setPrettyPrint(true);
//        swaggerConfigBean.setScan(true);
//    }
}

package com.adtomiclabs.commons.config;

import io.swagger.jaxrs.config.BeanConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import io.swagger.jaxrs.config.SwaggerConfigLocator;
import io.swagger.jaxrs.config.SwaggerContextService;
import io.swagger.models.SecurityRequirement;
import io.swagger.models.Swagger;
import io.swagger.models.auth.*;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletProperties;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.web.context.ServletConfigAware;
import javax.servlet.ServletConfig;
import javax.ws.rs.ext.Provider;
import java.util.Arrays;
import java.util.Collection;

/**
 * Jersey configuration class, an extension of {@link ResourceConfig}.
 */
@Component
public class JerseyConfig extends ResourceConfig implements ServletConfigAware {

    private ServletConfig servletConfig;

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
        register(CustomApiListingResource.class);
        register(CustomSwaggerSerializer.class);

        BeanConfig swaggerConfigBean = new BeanConfig();
        swaggerConfigBean.setConfigId("adtomic-api-docs");
        swaggerConfigBean.setTitle("Adtomic API REST");
        swaggerConfigBean.setVersion("v1");
        swaggerConfigBean.setBasePath("/");
        swaggerConfigBean.setContact("Adtomic IT");
        swaggerConfigBean.setSchemes(new String[] { "http", "https" });
        swaggerConfigBean.setResourcePackage("com.adtomiclabs.admin.backend.web.controller.rest_endpoints");
        swaggerConfigBean.setPrettyPrint(true);
        swaggerConfigBean.setScan(true);

        Swagger swagger = new Swagger();
        ApiKeyAuthDefinition bearerAuth = new ApiKeyAuthDefinition();
        bearerAuth.setType("apiKey");
        bearerAuth.setIn(In.HEADER);
        bearerAuth.setName("Authorization");
        swagger.securityDefinition("Bearer", bearerAuth);
        SecurityRequirement securityRequirement = new SecurityRequirement();
        securityRequirement.requirement("Bearer");
        swagger.security(securityRequirement);

        new SwaggerContextService().withServletConfig(servletConfig).updateSwagger(swagger);

        SwaggerConfigLocator.getInstance().putConfig(SwaggerContextService.CONFIG_ID_DEFAULT, swaggerConfigBean);
        SwaggerConfigLocator.getInstance().putConfig(SwaggerContextService.USE_PATH_BASED_CONFIG, swaggerConfigBean);

        packages(getClass().getPackage().getName(), CustomApiListingResource.class.getPackage().getName());

        // Register packages with resources and providers
        registerPackages(packages);
        // Register ObjectMapper which will be used to serialize/deserialize JSON
        register(new JacksonJaxbJsonProvider(objectMapper, JacksonJaxbJsonProvider.DEFAULT_ANNOTATIONS));
        // Register the ThrowableMapper that will wire the exception into the error handler
        register(throwableMapper);

        property(ServletProperties.FILTER_STATIC_CONTENT_REGEX, ".*(html|css|js)$");
    }

    @Override
    public void setServletConfig(ServletConfig servletConfig) {
//        logger.info("Setting ServletConfig");
        this.servletConfig = servletConfig;
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

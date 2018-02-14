package com.parabrisassi.sist.commons.config;

import com.bellotapps.utils.error_handler.AnnotationErrorHandlerCreationConfigurer;
import com.bellotapps.utils.error_handler.EnableErrorHandlerFactory;
import com.bellotapps.utils.error_handler.ErrorHandler;
import com.bellotapps.utils.error_handler.ErrorHandlerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A configuration class to configure the {@link ErrorHandler} using {@link EnableJerseyApplication} annotation.
 */
@Configuration
@EnableErrorHandlerFactory
/* package */ class ErrorHandlerConfigurer extends AnnotationErrorHandlerCreationConfigurer<EnableJerseyApplication> {

    /**
     * Constructor.
     *
     * @param errorHandlerFactory The {@link ErrorHandlerFactory} to be used to create the {@link ErrorHandler} bean.
     */
    @Autowired
    public ErrorHandlerConfigurer(ErrorHandlerFactory errorHandlerFactory) {
        super(errorHandlerFactory);
    }

    @Override
    protected Collection<String> getPackagesCollectionFromAnnotation(EnableJerseyApplication annotation) {
        final String[] basePackages = annotation.errorHandlersPackages();
        final String[] basePackageClasses = Arrays.stream(annotation.errorHandlersPackagesClasses())
                .map(Class::getPackage)
                .map(Package::getName)
                .toArray(String[]::new);
        return Stream.concat(Arrays.stream(basePackages), Arrays.stream(basePackageClasses))
                .collect(Collectors.toSet());
    }

    @Override
    protected Class<EnableJerseyApplication> getAnnotationClass() {
        return EnableJerseyApplication.class;
    }
}

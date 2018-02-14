package com.parabrisassi.sist.commons.config;

import com.bellotapps.utils.error_handler.ErrorHandler;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import java.util.Optional;

/**
 * A global {@link ExceptionMapper} that is in charge of mapping any {@link Throwable} thrown
 * within a Jersey application, using an {@link ErrorHandler}.
 */
public class ThrowableMapper implements ExceptionMapper<Throwable> {

    /**
     * The {@link ErrorHandler} in charge of transforming an exception into data to be returned in the response.
     */
    private final ErrorHandler errorHandler;

    /**
     * Constructor.
     *
     * @param exceptionHandler The {@link ErrorHandler} to be used to map errors into responses.
     */
    public ThrowableMapper(ErrorHandler exceptionHandler) {
        this.errorHandler = exceptionHandler;
    }

    @Override
    public Response toResponse(Throwable exception) {
        final ErrorHandler.HandlingResult result = errorHandler.handle(exception);
        return Response.status(result.getHttpErrorCode())
                .entity(Optional.ofNullable(result.getErrorRepresentationEntity()).orElse(""))
                .build();
    }
}

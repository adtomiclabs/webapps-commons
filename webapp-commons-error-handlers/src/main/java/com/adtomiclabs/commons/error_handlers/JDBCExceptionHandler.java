package com.adtomiclabs.commons.error_handlers;

import com.bellotapps.utils.error_handler.ErrorHandler;
import com.bellotapps.utils.error_handler.ExceptionHandler;
import com.bellotapps.utils.error_handler.ExceptionHandlerObject;
import com.adtomiclabs.commons.dtos.api_errors.ServerErrorDto;
import org.hibernate.JDBCException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;

/**
 * {@link ExceptionHandler} in charge of handling {@link JDBCException}.
 * Will result into a <b>503 Service Unavailable</b> response.
 */
@ExceptionHandlerObject
/* package */ class JDBCExceptionHandler implements ExceptionHandler<JDBCException> {

    /**
     * The {@link Logger} object.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(JDBCExceptionHandler.class);

    @Override
    public ErrorHandler.HandlingResult handle(JDBCException exception) {
        LOGGER.error("Could not access database", exception);

        return new ErrorHandler.HandlingResult(Response.Status.SERVICE_UNAVAILABLE.getStatusCode(),
                ServerErrorDto.DATABASE_ACCESS_ERROR_DTO);
    }
}

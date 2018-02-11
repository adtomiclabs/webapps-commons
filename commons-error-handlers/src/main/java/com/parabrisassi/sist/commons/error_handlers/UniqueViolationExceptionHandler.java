package com.parabrisassi.sist.commons.error_handlers;

import com.bellotapps.utils.error_handler.ErrorHandler;
import com.bellotapps.utils.error_handler.ExceptionHandler;
import com.bellotapps.utils.error_handler.ExceptionHandlerObject;
import com.parabrisassi.sist.commons.dtos.api_errors.UniqueViolationErrorDto;
import com.parabrisassi.sist.commons.exceptions.UniqueViolationException;

import javax.ws.rs.core.Response;

/**
 * {@link ExceptionHandler} in charge of handling {@link UniqueViolationException}.
 * Will result into a <b>409 Conflict</b> response.
 */
@ExceptionHandlerObject
/* package */ class UniqueViolationExceptionHandler implements ExceptionHandler<UniqueViolationException> {

    @Override
    public ErrorHandler.HandlingResult handle(UniqueViolationException exception) {
        return new ErrorHandler.HandlingResult(Response.Status.CONFLICT.getStatusCode(),
                new UniqueViolationErrorDto(exception.getErrors()));
    }
}

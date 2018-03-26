package com.adtomiclabs.commons.error_handlers;

import com.bellotapps.utils.error_handler.ErrorHandler;
import com.bellotapps.utils.error_handler.ExceptionHandler;
import com.bellotapps.utils.error_handler.ExceptionHandlerObject;
import com.adtomiclabs.commons.constants.WebConstants;
import com.adtomiclabs.commons.dtos.api_errors.ValidationErrorDto;
import com.adtomiclabs.commons.exceptions.ValidationException;

/**
 * {@link ExceptionHandler} in charge of handling {@link ValidationException}.
 * Will result into a <b>422 Unprocessable Entity</b> response.
 */
@ExceptionHandlerObject
/* package */ class ValidationViolationExceptionHandler implements ExceptionHandler<ValidationException> {

    @Override
    public ErrorHandler.HandlingResult handle(ValidationException exception) {
        return new ErrorHandler.HandlingResult(WebConstants.MissingHttpStatuses.UNPROCESSABLE_ENTITY.getStatusCode(),
                new ValidationErrorDto(exception.getErrors()));
    }
}

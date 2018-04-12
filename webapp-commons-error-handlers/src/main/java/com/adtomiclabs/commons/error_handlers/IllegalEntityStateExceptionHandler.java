package com.adtomiclabs.commons.error_handlers;

import com.adtomiclabs.commons.constants.WebConstants;
import com.adtomiclabs.commons.dtos.api_errors.IllegalEntityStateErrorDto;
import com.adtomiclabs.commons.exceptions.IllegalEntityStateException;
import com.bellotapps.utils.error_handler.ErrorHandler;
import com.bellotapps.utils.error_handler.ExceptionHandler;
import com.bellotapps.utils.error_handler.ExceptionHandlerObject;

/**
 * {@link ExceptionHandler} in charge of handling {@link IllegalEntityStateException}.
 * Will result into a <b>422 Unprocessable Entity</b> response.
 */
@ExceptionHandlerObject
/* package */ class IllegalEntityStateExceptionHandler implements ExceptionHandler<IllegalEntityStateException> {

    @Override
    public ErrorHandler.HandlingResult handle(IllegalEntityStateException exception) {
        return new ErrorHandler.HandlingResult(WebConstants.MissingHttpStatuses.UNPROCESSABLE_ENTITY.getStatusCode(),
                new IllegalEntityStateErrorDto(exception.getError()));
    }
}

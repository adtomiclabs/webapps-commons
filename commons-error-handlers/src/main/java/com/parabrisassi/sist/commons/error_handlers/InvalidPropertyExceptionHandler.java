package com.parabrisassi.sist.commons.error_handlers;

import com.bellotapps.utils.error_handler.ErrorHandler;
import com.bellotapps.utils.error_handler.ExceptionHandler;
import com.bellotapps.utils.error_handler.ExceptionHandlerObject;
import com.parabrisassi.sist.commons.dtos.api_errors.IllegalParamValueErrorDto;
import com.parabrisassi.sist.commons.exceptions.InvalidPropertiesException;

/**
 * {@link ExceptionHandler} in charge of handling {@link InvalidPropertiesException}.
 * Will result in the return value of
 * {@link IllegalParamValueExceptionHandler#illegalParamValueHandlingResult(IllegalParamValueErrorDto)}.
 */
@ExceptionHandlerObject
/* package */ class InvalidPropertyExceptionHandler implements ExceptionHandler<InvalidPropertiesException> {

    @Override
    public ErrorHandler.HandlingResult handle(InvalidPropertiesException exception) {
        return IllegalParamValueExceptionHandler
                .illegalParamValueHandlingResult(new IllegalParamValueErrorDto(exception.getErrors()));
    }
}

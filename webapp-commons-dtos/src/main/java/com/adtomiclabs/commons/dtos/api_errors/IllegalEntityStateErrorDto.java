package com.adtomiclabs.commons.dtos.api_errors;

import com.adtomiclabs.commons.errors.IllegalEntityStateError;

/**
 * Data transfer object for client errors caused when an operation over an entity is not valid  because of its state.
 */
public class IllegalEntityStateErrorDto extends ClientErrorDto {

    /**
     * The {@link IllegalEntityStateError}.
     */
    private final IllegalEntityStateError error;

    /**
     * Constructor.
     *
     * @param error The {@link IllegalEntityStateError}.
     */
    public IllegalEntityStateErrorDto(IllegalEntityStateError error) {
        super(ErrorFamily.ILLEGAL_STATE);
        this.error = error;
    }

    /**
     * @return The {@link IllegalEntityStateError}.
     */
    public IllegalEntityStateError getError() {
        return error;
    }
}

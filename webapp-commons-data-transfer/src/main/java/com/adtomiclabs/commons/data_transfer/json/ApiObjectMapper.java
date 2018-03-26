package com.adtomiclabs.commons.data_transfer.json;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * An extension of {@link ObjectMapper} to be used within jersey, as a JSON Provider for the API to be built.
 */
public class ApiObjectMapper extends ObjectMapper {


    // TODO: move to data transfer package
    /**
     * Constructor.
     */
    public ApiObjectMapper() {
        // Serialization
        this.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
        // Deserialization
        this.disable(DeserializationFeature.ACCEPT_FLOAT_AS_INT);
        this.enable(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES);
        this.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
    }
}

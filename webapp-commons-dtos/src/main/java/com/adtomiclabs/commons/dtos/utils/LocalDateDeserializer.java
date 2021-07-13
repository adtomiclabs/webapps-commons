package com.adtomiclabs.commons.dtos.utils;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class LocalDateDeserializer extends JsonDeserializer<LocalDate> {

    private static final String LOCAL_DATE_FORMAT = "yyyy-MM-dd";

    @Override
    public LocalDate deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException {
        return LocalDate.parse(jsonParser.readValueAs(String.class), DateTimeFormatter.ofPattern(LOCAL_DATE_FORMAT));
    }
}

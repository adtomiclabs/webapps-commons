package com.adtomiclabs.commons.dtos.utils;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class LocalDateSerializer extends JsonSerializer<LocalDate> {

    private static final String LOCAL_DATE_FORMAT = "yyyy-MM-dd";

    @Override
    public void serialize(LocalDate localDate, JsonGenerator jsonGenerator, SerializerProvider serializers) throws IOException {
        jsonGenerator.writeString(localDate.format(DateTimeFormatter.ofPattern(LOCAL_DATE_FORMAT)));
    }
}

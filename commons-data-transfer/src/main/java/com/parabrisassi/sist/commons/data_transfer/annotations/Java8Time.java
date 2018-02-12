package com.parabrisassi.sist.commons.data_transfer.annotations;

import com.parabrisassi.sist.commons.data_transfer.date_time.DateTimeFormatters;

import java.lang.annotation.*;
import java.time.format.DateTimeFormatter;

/**
 * Indicates that parameter is one of the Java 8 time types.
 */
@Target({ElementType.PARAMETER,})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Java8Time {

    /**
     * @return The {@link DateTimeFormatter} to be used to create the Java 8 type.
     */
    DateTimeFormatters formatter();
}

package com.parabrisassi.sist.commons.config;

import com.bellotapps.utils.error_handler.ErrorHandler;
import com.parabrisassi.sist.commons.data_transfer.json.ApiObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Optional;

/**
 * Filter that is in charge of handling gracefully any uncaught exception.
 * Will return data in JSON format.
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
public class UncaughtExceptionHandlerFilter extends GenericFilterBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(UncaughtExceptionHandlerFilter.class);

    /**
     * Indicates the type of data being returned in the response by this filter.
     */
    private static final String CONTENT_TYPE = "application/json";

    /**
     * The {@link ErrorHandler} in charge of transforming an exception into data to be returned in the response.
     */
    private final ErrorHandler handler;

    /**
     * The {@link ApiObjectMapper} in charge of serializing data into JSON to be returned in the response body.
     */
    private final ApiObjectMapper apiObjectMapper;

    /**
     * Constructor.
     *
     * @param handler         The {@link ErrorHandler} to be used to handle errors by this filter.
     * @param apiObjectMapper The {@link ApiObjectMapper} to be used to transform responses into JSONs.
     */
    public UncaughtExceptionHandlerFilter(ErrorHandler handler, ApiObjectMapper apiObjectMapper) {
        this.handler = handler;
        this.apiObjectMapper = apiObjectMapper;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) {
        try {
            chain.doFilter(req, resp);
        } catch (Throwable exception) {
            LOGGER.debug("An exception was not caught and is being handled by the UncaughtExceptionFilter", exception);
            final HttpServletResponse response = (HttpServletResponse) resp;
            if (response.isCommitted()) {
                LOGGER.error("Response was committed before handling exception");
                return;
            }
            try {
                final ErrorHandler.HandlingResult container = handler.handle(exception);
                response.setStatus(container.getHttpErrorCode());
                Optional.ofNullable(container.getErrorRepresentationEntity())
                        .ifPresent(entity -> {
                            try {
                                response.setContentType(CONTENT_TYPE);
                                apiObjectMapper.writeValue(response.getOutputStream(), entity);
                            } catch (IOException e) {
                                throw new UncheckedIOException(e);
                            }
                        });
            } catch (Throwable e) {
                LOGGER.error("Exception was thrown when handling exception!", e);
            }
        }
    }
}

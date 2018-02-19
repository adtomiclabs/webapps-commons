package com.parabrisassi.sist.commons.authentication;

import com.bellotapps.utils.error_handler.ErrorHandler;
import com.parabrisassi.sist.commons.data_transfer.json.ApiObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.parabrisassi.sist.commons.authentication.AuthenticationConstants.AUTHENTICATION_SCHEME;


/**
 * {@link AuthenticationFailureHandler} in charge of translating {@link AuthenticationException} into error responses.
 */
public class TokenAuthenticationFailureHandler implements AuthenticationFailureHandler {

    /**
     * Indicates the type of data being returned in the response by this filter.
     */
    private static final String CONTENT_TYPE = "application/json";

    /**
     * Indicates the header that will include the applicable authentication schemes supported by the system
     */
    private static final String APPLICABLE_AUTHENTICATION_SCHEMES_HEADER = "WWW-Authenticate";


    /**
     * The error handler in charge of handling exceptions.
     */
    private final ErrorHandler errorHandler;

    /**
     * {@link ApiObjectMapper} used to map results into JSON objects in the response body.
     */
    private final ApiObjectMapper objectMapper;

    /**
     * Constructor.
     *
     * @param errorHandler The error handler in charge of handling exceptions.
     * @param objectMapper {@link ApiObjectMapper} used to map results into JSON objects in the response body.
     */
    public TokenAuthenticationFailureHandler(ErrorHandler errorHandler, ApiObjectMapper objectMapper) {
        this.errorHandler = errorHandler;
        this.objectMapper = objectMapper;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException authException)
            throws IOException, ServletException {
        // Check if the authException is not a FailedJwtAuthenticationException,
        // in which case we must extract the original JwtException
        final Throwable throwable = authException instanceof FailedTokenAuthenticationException ?
                ((FailedTokenAuthenticationException) authException).getOriginalTokenException() : authException;
        // Ask the ErrorHandler to handle the exception
        final ErrorHandler.HandlingResult result = errorHandler.handle(throwable);

        // Set up response with data to be sent to the client
        setUpResponse(response, result);
    }

    /**
     * Sets up the given {@code response} in order to send the authentication error to the client.
     *
     * @param response The {@link HttpServletResponse} to be set up.
     * @param result   The {@link ErrorHandler.HandlingResult} containing the data to be sent to the client.
     * @throws IOException In case the {@link ApiObjectMapper} could not write the value in the response body.
     */
    private void setUpResponse(HttpServletResponse response, ErrorHandler.HandlingResult result)
            throws IOException {
        response.setStatus(result.getHttpErrorCode());
        response.addHeader(APPLICABLE_AUTHENTICATION_SCHEMES_HEADER, AUTHENTICATION_SCHEME);
        final Object entity = result.getErrorRepresentationEntity();
        if (entity == null) {
            return;
        }
        response.setContentType(CONTENT_TYPE);
        objectMapper.writeValue(response.getOutputStream(), entity);
    }
}

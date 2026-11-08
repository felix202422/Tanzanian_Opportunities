package tz.tdop.api.error;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import java.time.Instant;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import tz.tdop.logging.CorrelationId;

/**
 * Maps every request failure onto the single error body documented in docs/API_CONTRACT.md.
 * <p>
 * Rules this class exists to guarantee:
 * <ul>
 * <li>Clients always receive the same JSON shape with a stable {@code code}, whatever went wrong.
 * <li>Responses never leak internals: 500 bodies contain no exception message, stack trace, SQL or class name; those
 * details go to the server log under the request correlation id instead.
 * <li>4xx details are written here, not taken from exception messages that may echo user input or framework internals
 * verbatim.
 * </ul>
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // --- request that never reached a handler -------------------------------------------

    @ExceptionHandler(NoResourceFoundException.class)
    ResponseEntity<ApiError> noResource(NoResourceFoundException ex, HttpServletRequest request) {
        return build("NOT_FOUND", "Not found", HttpStatus.NOT_FOUND, "No resource exists at that path.", request);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    ResponseEntity<ApiError> noHandler(NoHandlerFoundException ex, HttpServletRequest request) {
        return build("NOT_FOUND", "Not found", HttpStatus.NOT_FOUND, "No handler is registered for that request.",
                request);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    ResponseEntity<ApiError> methodNotAllowed(HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {
        String supported = ex.getSupportedHttpMethods() == null ? ""
                : " Supported: "
                        + String.join(", ", ex.getSupportedHttpMethods().stream().map(method -> method.name()).toList())
                        + ".";
        return build("METHOD_NOT_ALLOWED", "Method not allowed", HttpStatus.METHOD_NOT_ALLOWED,
                ex.getMethod() + " is not supported for this path." + supported, request);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    ResponseEntity<ApiError> unsupportedMediaType(HttpMediaTypeNotSupportedException ex, HttpServletRequest request) {
        return build("UNSUPPORTED_MEDIA_TYPE", "Unsupported media type", HttpStatus.UNSUPPORTED_MEDIA_TYPE,
                "The request body content type is not supported by this endpoint.", request);
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    ResponseEntity<ApiError> notAcceptable(HttpMediaTypeNotAcceptableException ex, HttpServletRequest request) {
        return build("NOT_ACCEPTABLE", "Not acceptable", HttpStatus.NOT_ACCEPTABLE,
                "The requested response format is not supported by this endpoint.", request);
    }

    // --- request reached a handler but was malformed --------------------------------------

    @ExceptionHandler(HttpMessageNotReadableException.class)
    ResponseEntity<ApiError> unreadable(HttpMessageNotReadableException ex, HttpServletRequest request) {
        // The cause may contain the raw payload or Jackson internals, so it is never echoed.
        return build("MALFORMED_REQUEST", "Malformed request", HttpStatus.BAD_REQUEST,
                "The request body could not be read; it is not valid for this endpoint.", request);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    ResponseEntity<ApiError> missingParameter(MissingServletRequestParameterException ex, HttpServletRequest request) {
        return build("MISSING_PARAMETER", "Missing parameter", HttpStatus.BAD_REQUEST,
                "Required parameter '" + ex.getParameterName() + "' is missing.", request);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    ResponseEntity<ApiError> typeMismatch(MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
        return build("INVALID_PARAMETER", "Invalid parameter", HttpStatus.BAD_REQUEST,
                "Parameter '" + ex.getName() + "' has an unsupported value.", request);
    }

    // --- validation -----------------------------------------------------------------------

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ApiError> bodyNotValid(MethodArgumentNotValidException ex, HttpServletRequest request) {
        return validationFailure(ex.getBindingResult().getFieldErrors(), request);
    }

    @ExceptionHandler(BindException.class)
    ResponseEntity<ApiError> bindingFailed(BindException ex, HttpServletRequest request) {
        return validationFailure(ex.getBindingResult().getFieldErrors(), request);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<ApiError> constraintViolation(ConstraintViolationException ex, HttpServletRequest request) {
        List<ApiError.FieldError> errors = ex.getConstraintViolations().stream().map(
                violation -> new ApiError.FieldError(violation.getPropertyPath().toString(), violation.getMessage()))
                .toList();
        return build("VALIDATION_FAILED", "Validation failed", HttpStatus.BAD_REQUEST,
                "One or more fields are invalid.", request, errors);
    }

    // --- anything else --------------------------------------------------------------------

    /**
     * An exception that deliberately carries an HTTP status (Spring's own {@code @ResponseStatus} and
     * {@code ResponseStatusException}). The reason text was written by application code, so it is safe to return.
     */
    @ExceptionHandler(ResponseStatusException.class)
    ResponseEntity<ApiError> responseStatus(ResponseStatusException ex, HttpServletRequest request) {
        HttpStatusCode status = ex.getStatusCode();
        String reason = ex.getReason() == null ? "The request could not be completed." : ex.getReason();
        return build(codeFor(status), titleFor(status), status, reason, request);
    }

    /**
     * Last resort: a 500 with no internal detail. The exception itself is logged in full under the request correlation
     * id so operators can find it; the client only gets the correlation id.
     */
    @ExceptionHandler(Exception.class)
    ResponseEntity<ApiError> unexpected(Exception ex, HttpServletRequest request) {
        log.error("Unhandled exception while processing {} {}", request.getMethod(), request.getRequestURI(), ex);
        return build("INTERNAL_ERROR", "Internal error", HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred. Quote the correlation id when reporting this problem.", request);
    }

    // --- helpers --------------------------------------------------------------------------

    private ResponseEntity<ApiError> validationFailure(List<FieldError> fieldErrors, HttpServletRequest request) {
        List<ApiError.FieldError> errors = fieldErrors.stream()
                .map(error -> new ApiError.FieldError(error.getField(), error.getDefaultMessage())).toList();
        return build("VALIDATION_FAILED", "Validation failed", HttpStatus.BAD_REQUEST,
                "One or more fields are invalid.", request, errors);
    }

    private ResponseEntity<ApiError> build(String code, String title, HttpStatusCode status, String detail,
            HttpServletRequest request) {
        return build(code, title, status, detail, request, null);
    }

    private ResponseEntity<ApiError> build(String code, String title, HttpStatusCode status, String detail,
            HttpServletRequest request, List<ApiError.FieldError> fieldErrors) {
        ApiError body = new ApiError(code, title, status.value(), detail, request.getRequestURI(),
                MDC.get(CorrelationId.MDC_KEY), Instant.now(), fieldErrors);
        return ResponseEntity.status(status).body(body);
    }

    private static String codeFor(HttpStatusCode status) {
        return switch (status.value()) {
            case 400 -> "BAD_REQUEST";
            case 401 -> "UNAUTHORIZED";
            case 403 -> "FORBIDDEN";
            case 404 -> "NOT_FOUND";
            case 405 -> "METHOD_NOT_ALLOWED";
            case 409 -> "CONFLICT";
            case 503 -> "SERVICE_UNAVAILABLE";
            default -> status.is5xxServerError() ? "INTERNAL_ERROR" : "REQUEST_REJECTED";
        };
    }

    private static String titleFor(HttpStatusCode status) {
        return switch (status.value()) {
            case 400 -> "Bad request";
            case 401 -> "Unauthorized";
            case 403 -> "Forbidden";
            case 404 -> "Not found";
            case 405 -> "Method not allowed";
            case 409 -> "Conflict";
            case 503 -> "Service unavailable";
            default -> status.is5xxServerError() ? "Internal error" : "Request rejected";
        };
    }
}

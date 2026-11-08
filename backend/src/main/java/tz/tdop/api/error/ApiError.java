package tz.tdop.api.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.Instant;
import java.util.List;

/**
 * The single error body returned by every failing request of the TDOP API.
 * <p>
 * The contract is documented in docs/API_CONTRACT.md and is stable across versions: clients switch on {@code code} (a
 * machine-readable, lower-case identifier) and may display {@code
 * detail}. {@code fieldErrors} is present only for validation failures. {@code correlationId} ties the response to the
 * server log line for the same request.
 *
 * @param code
 *            stable machine-readable error code, e.g. {@code VALIDATION_FAILED}
 * @param title
 *            short human-readable summary of the error class
 * @param status
 *            HTTP status code, duplicated in the body for log/pipeline convenience
 * @param detail
 *            request-specific message that is safe to show to a user; never contains stack traces, SQL, or internal
 *            class names
 * @param instance
 *            the request path that produced the error
 * @param correlationId
 *            identifier shared with the log line for this request
 * @param timestamp
 *            UTC instant the error was produced
 * @param fieldErrors
 *            per-field validation messages; {@code null} when not applicable
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiError(String code, String title, int status, String detail, String instance, String correlationId,
        Instant timestamp, List<FieldError> fieldErrors) {

    /** One invalid field of a validation failure. */
    public record FieldError(String field, String message) {
    }
}

package tz.tdop.logging;

import java.util.UUID;
import java.util.regex.Pattern;
import org.slf4j.MDC;

/**
 * Request correlation: one identifier shared by the response header, every log line of the request and the
 * {@code correlationId} field of an error body.
 * <p>
 * Client-supplied identifiers are accepted only when they look like an identifier; anything else is replaced by a
 * generated UUID so a caller cannot inject newlines or control characters into the log stream.
 */
public final class CorrelationId {

    /** Response/request header carrying the identifier. */
    public static final String HEADER = "X-Correlation-Id";

    /** MDC key used by the log pattern in application.yml. */
    public static final String MDC_KEY = "correlationId";

    private static final Pattern ACCEPTABLE = Pattern.compile("[A-Za-z0-9-]{1,64}");

    private CorrelationId() {
    }

    /** The identifier for the current request, or {@code null} outside a request thread. */
    public static String current() {
        return MDC.get(MDC_KEY);
    }

    /**
     * Returns the caller's identifier when it is safe to log, otherwise a freshly generated UUID.
     */
    public static String orGenerate(String candidate) {
        if (candidate != null && ACCEPTABLE.matcher(candidate).matches()) {
            return candidate;
        }
        return UUID.randomUUID().toString();
    }
}

package tz.tdop.api.error;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;
import org.springframework.core.MethodParameter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.HttpRequestMethodNotSupportedException;

/**
 * Unit-level checks of the error contract's mapping rules (docs/API_CONTRACT.md): stable codes, field-level validation
 * detail, and — most importantly — that no response ever echoes internal exception text such as messages carrying
 * credentials or SQL.
 */
class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @AfterEach
    void clearMdc() {
        MDC.clear();
    }

    private static HttpServletRequest request(String uri) {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", uri);
        return request;
    }

    @Test
    void validationFailureCarriesEveryInvalidField() throws Exception {
        BeanPropertyBindingResult result = new BeanPropertyBindingResult(new Object(), "request");
        result.addError(new FieldError("request", "name", "must not be blank"));
        result.addError(new FieldError("request", "email", "must be a well-formed email address"));
        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(parameter(), result);

        var response = handler.bodyNotValid(ex, request("/api/v1/accounts"));

        assertThat(response.getStatusCode().value()).isEqualTo(400);
        ApiError body = response.getBody();
        assertThat(body.code()).isEqualTo("VALIDATION_FAILED");
        assertThat(body.status()).isEqualTo(400);
        assertThat(body.detail()).isEqualTo("One or more fields are invalid.");
        assertThat(body.instance()).isEqualTo("/api/v1/accounts");
        assertThat(body.fieldErrors()).extracting(ApiError.FieldError::field).containsExactly("name", "email");
        assertThat(body.fieldErrors().get(0).message()).isEqualTo("must not be blank");
        assertThat(body.correlationId()).isNull();
        assertThat(body.timestamp()).isNotNull();
    }

    @Test
    void validationFailureIncludesTheCorrelationIdWhenPresent() throws Exception {
        MDC.put("correlationId", "unit-test-7");
        BeanPropertyBindingResult result = new BeanPropertyBindingResult(new Object(), "request");
        result.addError(new FieldError("request", "name", "must not be blank"));
        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(parameter(), result);

        ApiError body = handler.bodyNotValid(ex, request("/api/v1/accounts")).getBody();

        assertThat(body.correlationId()).isEqualTo("unit-test-7");
    }

    @Test
    void serverErrorsNeverEchoExceptionInternals() {
        // Simulates an exception whose message would leak credentials or SQL if echoed.
        Exception boom = new IllegalStateException("connection failed: password=super-secret-value");

        ApiError body = handler.unexpected(boom, request("/api/v1/health")).getBody();

        assertThat(body.code()).isEqualTo("INTERNAL_ERROR");
        assertThat(body.status()).isEqualTo(500);
        assertThat(body.detail()).doesNotContain("super-secret-value").doesNotContain("connection failed")
                .doesNotContain("IllegalStateException").doesNotContain("password");
        assertThat(body.detail()).contains("correlation id");
    }

    @Test
    void malformedBodyIsNeverEchoedBack() {
        HttpMessageNotReadableException ex = new HttpMessageNotReadableException(
                "JSON parse error: Unexpected end-of-input near {\"token\": \"secret-payload\"",
                new ServletServerHttpRequest(new MockHttpServletRequest()));

        ApiError body = handler.unreadable(ex, request("/api/v1/accounts")).getBody();

        assertThat(body.code()).isEqualTo("MALFORMED_REQUEST");
        assertThat(body.status()).isEqualTo(400);
        assertThat(body.detail()).doesNotContain("secret-payload").doesNotContain("JSON parse error")
                .doesNotContain("token");
    }

    @Test
    void unknownPathIsMappedToNotFound() {
        ApiError body = handler.noResource(null, request("/api/v1/ghost")).getBody();

        assertThat(body.code()).isEqualTo("NOT_FOUND");
        assertThat(body.status()).isEqualTo(404);
        assertThat(body.instance()).isEqualTo("/api/v1/ghost");
        assertThat(body.fieldErrors()).isNull();
    }

    @Test
    void unsupportedMethodListsWhatIsAllowed() {
        HttpRequestMethodNotSupportedException ex = new HttpRequestMethodNotSupportedException("POST",
                List.of("GET", "HEAD"));

        ApiError body = handler.methodNotAllowed(ex, request("/api/v1/health")).getBody();

        assertThat(body.code()).isEqualTo("METHOD_NOT_ALLOWED");
        assertThat(body.status()).isEqualTo(405);
        assertThat(body.detail()).contains("POST").contains("GET").contains("HEAD");
    }

    @Test
    void missingParameterNamesTheParameter() {
        MissingServletRequestParameterException ex = new MissingServletRequestParameterException("page", "Integer");

        ApiError body = handler.missingParameter(ex, request("/api/v1/opportunities")).getBody();

        assertThat(body.code()).isEqualTo("MISSING_PARAMETER");
        assertThat(body.status()).isEqualTo(400);
        assertThat(body.detail()).contains("'page'");
    }

    /** A throwaway handler method used only to build a {@link MethodParameter} in tests. */
    @SuppressWarnings("unused")
    private void fixtureHandler(String ignored) {
    }

    private MethodParameter parameter() throws Exception {
        return new MethodParameter(getClass().getDeclaredMethod("fixtureHandler", String.class), 0);
    }
}

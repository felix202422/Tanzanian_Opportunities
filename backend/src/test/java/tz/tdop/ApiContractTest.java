package tz.tdop;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.matchesPattern;
import static org.hamcrest.Matchers.startsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tz.tdop.logging.CorrelationId;

/**
 * Verifies the Phase 3 acceptance criteria that are visible over HTTP: the versioned health endpoints respond, the
 * error contract in docs/API_CONTRACT.md is what clients actually receive, only safe actuator endpoints are exposed,
 * the OpenAPI document is generated from the running application, and every response carries a correlation id.
 */
@SpringBootTest
@AutoConfigureMockMvc
class ApiContractTest {

    @Autowired
    private MockMvc mockMvc;

    // --- health ---------------------------------------------------------------------------

    @Test
    void healthRespondsUnderTheVersionedPath() throws Exception {
        mockMvc.perform(get("/api/v1/health")).andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("UP"));
    }

    @Test
    void healthIsNotServedOutsideTheVersionedPath() throws Exception {
        mockMvc.perform(get("/health")).andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("NOT_FOUND")).andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.instance").value("/health")).andExpect(jsonPath("$.fieldErrors").doesNotExist());
    }

    @Test
    void actuatorExposesHealthProbesOnly() throws Exception {
        mockMvc.perform(get("/actuator/health")).andExpect(status().isOk()).andExpect(jsonPath("$.status").value("UP"));

        mockMvc.perform(get("/actuator/health/liveness")).andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"));

        // The readiness group answers as soon as the application reports its state; 503 is a
        // legitimate answer (not accepting traffic) and proves the endpoint exists.
        mockMvc.perform(get("/actuator/health/readiness")).andExpect(result -> {
            int status = result.getResponse().getStatus();
            if (status != 200 && status != 503) {
                throw new AssertionError("readiness probe must answer 200 or 503, got " + status);
            }
        });

        // Anything beyond health must stay unreachable.
        mockMvc.perform(get("/actuator/env")).andExpect(status().isNotFound());
        mockMvc.perform(get("/actuator/beans")).andExpect(status().isNotFound());
        mockMvc.perform(get("/actuator/health/all")).andExpect(status().isNotFound());
    }

    // --- error contract -------------------------------------------------------------------

    @Test
    void unknownPathReturnsTheDocumentedErrorBody() throws Exception {
        mockMvc.perform(get("/api/v1/does-not-exist")).andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("NOT_FOUND")).andExpect(jsonPath("$.title").value("Not found"))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.instance").value("/api/v1/does-not-exist"))
                .andExpect(jsonPath("$.timestamp").exists()).andExpect(jsonPath("$.correlationId").exists());
    }

    @Test
    void wrongHttpMethodReturnsMethodNotAllowedWithSupportedMethods() throws Exception {
        mockMvc.perform(post("/api/v1/health").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isMethodNotAllowed()).andExpect(jsonPath("$.code").value("METHOD_NOT_ALLOWED"))
                .andExpect(jsonPath("$.status").value(405)).andExpect(jsonPath("$.detail", containsString("GET")));
    }

    @Test
    void errorBodyCarriesTheSameCorrelationIdAsTheResponseHeader() throws Exception {
        mockMvc.perform(get("/api/v1/ghost").header(CorrelationId.HEADER, "req-contract-1"))
                .andExpect(status().isNotFound()).andExpect(header().string(CorrelationId.HEADER, "req-contract-1"))
                .andExpect(jsonPath("$.correlationId").value("req-contract-1"));
    }

    // --- correlation ids ------------------------------------------------------------------

    @Test
    void callerSuppliedCorrelationIdIsEchoed() throws Exception {
        mockMvc.perform(get("/api/v1/health").header(CorrelationId.HEADER, "req-42")).andExpect(status().isOk())
                .andExpect(header().string(CorrelationId.HEADER, "req-42"));
    }

    @Test
    void missingCorrelationIdIsGenerated() throws Exception {
        mockMvc.perform(get("/api/v1/health")).andExpect(status().isOk()).andExpect(header().string(
                CorrelationId.HEADER, matchesPattern("[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}")));
    }

    @Test
    void unusableCorrelationIdIsReplacedInsteadOfEchoed() throws Exception {
        String hostile = "not a safe log value <script>";
        mockMvc.perform(get("/api/v1/health").header(CorrelationId.HEADER, hostile)).andExpect(status().isOk())
                .andExpect(header().string(CorrelationId.HEADER,
                        matchesPattern("[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}")));
    }

    // --- OpenAPI --------------------------------------------------------------------------

    @Test
    void openApiDocumentIsGeneratedFromTheRunningApplication() throws Exception {
        mockMvc.perform(get("/v3/api-docs")).andExpect(status().isOk())
                .andExpect(jsonPath("$.openapi", startsWith("3.")))
                .andExpect(jsonPath("$.info.title").value("Tanzania Digital Opportunity Platform API"))
                .andExpect(jsonPath("$.info.version").value("v1"))
                .andExpect(jsonPath("$.paths['/api/v1/health']").exists());
    }

    @Test
    void swaggerUiIsServed() throws Exception {
        mockMvc.perform(get("/swagger-ui/index.html")).andExpect(status().isOk());
    }
}

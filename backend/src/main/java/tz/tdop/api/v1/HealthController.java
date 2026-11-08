package tz.tdop.api.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.boot.health.actuate.endpoint.HealthEndpoint;
import org.springframework.boot.health.contributor.Status;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * Versioned health endpoint for API consumers: {@code GET /api/v1/health}.
 * <p>
 * It reports the aggregate health of the service (the same indicator that backs {@code
 * /actuator/health}) without exposing component details. Operational tooling should keep using the actuator endpoint,
 * which follows the platform conventions for probes.
 */
@Tag(name = "system", description = "Service-level endpoints that are not feature APIs.")
@RestController
@RequestMapping("/health")
public class HealthController {

    private final HealthEndpoint healthEndpoint;

    public HealthController(HealthEndpoint healthEndpoint) {
        this.healthEndpoint = healthEndpoint;
    }

    @Operation(summary = "Service health", description = "Returns UP when every required component (including the database) is healthy.")
    @ApiResponse(responseCode = "200", description = "Service is healthy")
    @ApiResponse(responseCode = "503", description = "A required component is not healthy")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HealthResponse> health() {
        Status status = healthEndpoint.health().getStatus();
        if (!Status.UP.equals(status)) {
            // Mapped to the standard error body by GlobalExceptionHandler (code SERVICE_UNAVAILABLE).
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                    "A required service component is not healthy.");
        }
        return ResponseEntity.ok(new HealthResponse(status.getCode()));
    }

    /** Response body of {@code GET /api/v1/health}. */
    public record HealthResponse(String status) {
    }
}

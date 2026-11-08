package tz.tdop.api.v1;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.boot.health.actuate.endpoint.HealthDescriptor;
import org.springframework.boot.health.actuate.endpoint.HealthEndpoint;
import org.springframework.boot.health.actuate.endpoint.TestHealthDescriptors;
import org.springframework.boot.health.contributor.Status;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

/**
 * Unit tests of the versioned health endpoint's contract: UP maps to 200 with the status echoed, anything else maps to
 * 503 so the shared error contract produces {@code SERVICE_UNAVAILABLE}.
 * <p>
 * The aggregate status comes from the actuator {@link HealthEndpoint}, mocked here; the end-to-end behaviour against a
 * live application context is covered by {@code ApiContractTest}. The descriptor itself cannot be mocked (sealed type,
 * see TestHealthDescriptors), so real instances are built for each status.
 */
class HealthControllerTest {

    private final HealthEndpoint healthEndpoint = mock(HealthEndpoint.class);
    private final HealthController controller = new HealthController(healthEndpoint);

    private void report(Status status) {
        HealthDescriptor descriptor = TestHealthDescriptors.withStatus(status);
        when(healthEndpoint.health()).thenReturn(descriptor);
    }

    @Test
    void upStatusIsReportedAsOk() {
        report(Status.UP);

        ResponseEntity<HealthController.HealthResponse> response = controller.health();

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().status()).isEqualTo("UP");
    }

    @Test
    void downStatusBecomesServiceUnavailable() {
        report(Status.DOWN);

        assertThatThrownBy(controller::health).isInstanceOf(ResponseStatusException.class)
                .hasFieldOrPropertyWithValue("statusCode", HttpStatus.SERVICE_UNAVAILABLE)
                .hasMessageContaining("not healthy");
    }

    @Test
    void outOfServiceIsNotHealthyEither() {
        report(Status.OUT_OF_SERVICE);

        assertThatThrownBy(controller::health).isInstanceOf(ResponseStatusException.class)
                .hasFieldOrPropertyWithValue("statusCode", HttpStatus.SERVICE_UNAVAILABLE);
    }
}

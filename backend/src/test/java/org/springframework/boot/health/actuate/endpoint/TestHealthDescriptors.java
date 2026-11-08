package org.springframework.boot.health.actuate.endpoint;

import org.springframework.boot.health.contributor.Health;
import org.springframework.boot.health.contributor.Status;

/**
 * Test-only helper: Boot 4 made {@link HealthDescriptor} a sealed type whose concrete implementations have
 * package-private constructors, so Mockito cannot mock it. Constructing {@link IndicatedHealthDescriptor} is only
 * possible from within its own package — which is why this fixture lives here (test sources only; it is never shipped).
 */
public final class TestHealthDescriptors {

    private TestHealthDescriptors() {
    }

    /** A descriptor whose status is exactly {@code status}. */
    public static HealthDescriptor withStatus(Status status) {
        return new IndicatedHealthDescriptor(new Health.Builder(status).build());
    }
}

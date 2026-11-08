package tz.tdop.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * API versioning by package: every controller under {@code tz.tdop.api.v1} is served under {@code
 * /api/v1} without repeating the prefix in each mapping.
 * <p>
 * To introduce a new API version, add controllers under {@code tz.tdop.api.v2} and register its prefix here; the
 * previous version keeps working unchanged. Controllers outside these packages (operational endpoints such as actuator)
 * are not prefixed. See docs/API_CONTRACT.md.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    static final String V1_PACKAGE = "tz.tdop.api.v1";

    static final String V1_PREFIX = "/api/v1";

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.addPathPrefix(V1_PREFIX, WebConfig::isVersionedController);
    }

    private static boolean isVersionedController(Class<?> controllerType) {
        String packageName = controllerType.getPackageName();
        return packageName.equals(V1_PACKAGE) || packageName.startsWith(V1_PACKAGE + ".");
    }
}

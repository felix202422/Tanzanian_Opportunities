package tz.tdop.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Metadata for the generated OpenAPI document (served at {@code /v3/api-docs}). */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI tdopOpenApi() {
        return new OpenAPI().info(new Info().title("Tanzania Digital Opportunity Platform API").version("v1")
                .description("Bilingual (English/Swahili) opportunity discovery platform. This document is "
                        + "generated from the running application; it lists only the endpoints "
                        + "that exist in this build.")
                .license(new License().name("Apache-2.0").url("https://www.apache.org/licenses/LICENSE-2.0")));
    }
}

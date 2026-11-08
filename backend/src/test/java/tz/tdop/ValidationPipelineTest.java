package tz.tdop;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Proves the validation pipeline end to end in the real application context: Bean Validation on a request DTO, the
 * global exception advice, and the error contract documented in docs/API_CONTRACT.md.
 * <p>
 * The controller below is a <em>test fixture</em>, not a product endpoint. Phase 3 defines the infrastructure; the
 * first production DTOs to use it are the registration/login contracts of Phase 4. It lives outside {@code tz.tdop.api}
 * on purpose so it never appears in the generated OpenAPI document.
 */
@SpringBootTest
@AutoConfigureMockMvc
@Import(ValidationPipelineTest.ValidationFixtureController.class)
class ValidationPipelineTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void invalidRequestBodyProducesTheDocumentedErrorBody() throws Exception {
        mockMvc.perform(post("/api/v1/__fixture/echo").contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\" \",\"email\":\"not-an-email\"}")).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_FAILED"))
                .andExpect(jsonPath("$.title").value("Validation failed")).andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.instance").value("/api/v1/__fixture/echo"))
                .andExpect(jsonPath("$.detail").value("One or more fields are invalid."))
                .andExpect(jsonPath("$.fieldErrors.length()").value(2))
                .andExpect(jsonPath("$.fieldErrors[*].field").value(org.hamcrest.Matchers.hasItems("name", "email")));
    }

    @Test
    void validRequestBodyPassesThrough() throws Exception {
        mockMvc.perform(post("/api/v1/__fixture/echo").contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Asha Juma\",\"email\":\"asha@example.org\"}")).andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Asha Juma"))
                .andExpect(jsonPath("$.email").value("asha@example.org"));
    }

    @Test
    void malformedJsonIsReportedAsMalformedRequest() throws Exception {
        mockMvc.perform(post("/api/v1/__fixture/echo").contentType(MediaType.APPLICATION_JSON).content("{\"name\": "))
                .andExpect(status().isBadRequest()).andExpect(jsonPath("$.code").value("MALFORMED_REQUEST"))
                .andExpect(jsonPath("$.status").value(400))
                // the fragment the client sent is never echoed back
                .andExpect(jsonPath("$.detail")
                        .value("The request body could not be read; it is not valid for this endpoint."));
    }

    @Test
    void unsupportedContentTypeIsReported() throws Exception {
        mockMvc.perform(post("/api/v1/__fixture/echo").contentType(MediaType.APPLICATION_XML)
                .content("<echo><name>Asha</name></echo>")).andExpect(status().isUnsupportedMediaType())
                .andExpect(jsonPath("$.code").value("UNSUPPORTED_MEDIA_TYPE"))
                .andExpect(jsonPath("$.status").value(415));
    }

    /** Test-only endpoint exercising the production validation and error-handling machinery. */
    @RestController
    static class ValidationFixtureController {

        record EchoRequest(@NotBlank String name, @Email String email) {
        }

        record EchoResponse(String name, String email) {
        }

        @PostMapping("/api/v1/__fixture/echo")
        EchoResponse echo(@Valid @RequestBody EchoRequest request) {
            return new EchoResponse(request.name(), request.email());
        }
    }
}

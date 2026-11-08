# TDOP API Contract

**Status:** Phase 3 baseline, generated from the running application. This document describes what the backend actually serves today; it is updated in the same change as the code it describes.

**Generated companion:** `GET /v3/api-docs` returns the OpenAPI 3 document built from the live controllers, and `/swagger-ui/index.html` renders it. Where this file and the generated document disagree, the generated document describes the code and this file must be corrected.

## 1. Base path and versioning

| Item | Value |
| --- | --- |
| Base path | `/api/v1` |
| Content type | `application/json; charset=UTF-8` |
| Timestamps | UTC, ISO-8601 (for example `2026-11-08T09:30:00Z`) |
| Identifiers | UUIDs as canonical strings |

Versioning is structural: every controller in the `tz.tdop.api.v1` package is served under `/api/v1` (see `WebConfig`), so the prefix is never repeated in a controller mapping. A future `/api/v2` adds a new package and prefix; v1 keeps working unchanged. Operational endpoints (actuator, the OpenAPI document itself, Swagger UI) are intentionally **not** versioned.

Breaking changes to a response body, a field name, an error `code` or the meaning of an existing status code require a new version. Adding an endpoint, adding an optional response field or adding a new error `code` for a new failure mode is not breaking.

## 2. Endpoints served in this phase

| Method | Path | Purpose | Success | Failure |
| --- | --- | --- | --- | --- |
| GET | `/api/v1/health` | Service health for API consumers | `200 {"status":"UP"}` | `503` with the standard error body when any required component is not `UP` |
| GET | `/actuator/health` | Operations health (Spring Boot convention) | `200` | `503` |
| GET | `/actuator/health/liveness` | Kubernetes-style liveness probe | `200` | `503` |
| GET | `/actuator/health/readiness` | Kubernetes-style readiness probe | `200` when accepting traffic | `503` when not |
| GET | `/v3/api-docs` | Generated OpenAPI 3 document | `200` | — |
| GET | `/swagger-ui/index.html` | Interactive API documentation | `200` | — |

Feature endpoints (opportunities, accounts, applications) do not exist yet; they arrive in later phases and will be listed here when they do.

### Actuator exposure policy

Only `health` is exposed over HTTP. Health details and components are never shown (`show-details: never`), so internal contributor names, exceptions and configuration never leave the process. `/actuator/env`, `/actuator/beans` and similar endpoints answer `404`. The `/api/v1/health` body reports the aggregate status only.

## 3. Error contract

Every failing request — whatever the cause — returns the same JSON body shape. Clients switch on `code` and may display `detail`; `title` is a human-readable summary of the error class.

```json
{
  "code": "VALIDATION_FAILED",
  "title": "Validation failed",
  "status": 400,
  "detail": "One or more fields are invalid.",
  "instance": "/api/v1/accounts",
  "correlationId": "8f14e45f-ceea-467f-a0f4-2f4a3c8b7d11",
  "timestamp": "2026-11-08T09:30:00Z",
  "fieldErrors": [
    { "field": "email", "message": "must be a well-formed email address" }
  ]
}
```

| Field | Type | Always present | Meaning |
| --- | --- | --- | --- |
| `code` | string | yes | Stable, upper-case, machine-readable identifier. Clients branch on this. |
| `title` | string | yes | Short human-readable summary of the error class. |
| `status` | integer | yes | HTTP status, duplicated in the body for log/pipeline convenience. |
| `detail` | string | yes | Request-specific message safe to show a user. Never contains stack traces, SQL, class names or framework internals. |
| `instance` | string | yes | Request path that produced the error. |
| `correlationId` | string | yes | Identifier shared with the server log line for this request (section 4). |
| `timestamp` | string | yes | UTC instant the error was produced. |
| `fieldErrors` | array | only for validation failures | Per-field messages: `field` is the request DTO property, `message` the validation message. Absent (`null`) on every other error. |

### Error codes

| `code` | HTTP | Cause |
| --- | --- | --- |
| `VALIDATION_FAILED` | 400 | Bean Validation rejected the request body, query/path parameter or constraint. Carries `fieldErrors`. |
| `MALFORMED_REQUEST` | 400 | The request body could not be parsed (invalid JSON, wrong shape). The payload fragment is never echoed. |
| `MISSING_PARAMETER` | 400 | A required query or path parameter is absent. |
| `INVALID_PARAMETER` | 400 | A parameter value has an unsupported type or value. |
| `BAD_REQUEST` | 400 | A `ResponseStatusException(400)` raised deliberately by application code. |
| `NOT_FOUND` | 404 | No resource or handler exists at that path. |
| `METHOD_NOT_ALLOWED` | 405 | Wrong HTTP method; `detail` lists the supported methods. |
| `NOT_ACCEPTABLE` | 406 | The requested response format is not supported. |
| `UNSUPPORTED_MEDIA_TYPE` | 415 | The request body content type is not supported. |
| `UNAUTHORIZED` | 401 | Authentication is required (introduced with Spring Security in a later phase). |
| `FORBIDDEN` | 403 | Authenticated but not permitted (later phase). |
| `CONFLICT` | 409 | State conflict, for example a duplicate registration (later phase). |
| `SERVICE_UNAVAILABLE` | 503 | A required component is unhealthy (for example `GET /api/v1/health` while the database is down). |
| `INTERNAL_ERROR` | 500 | Unexpected server fault. `detail` never names the fault; see below. |
| `REQUEST_REJECTED` | 4xx | Other rejections raised deliberately by framework or application code. |

`GlobalExceptionHandler` is the single place that maps exceptions onto this table. New failure modes must be given a stable `code` here and a row in this table in the same change.

### Server errors never leak internals

For any `500`, the response contains only `INTERNAL_ERROR`, a generic `detail` and the `correlationId`. The exception message, stack trace, SQL and class names are written to the server log under that same correlation id, where operators can find them. The same rule applies to `MALFORMED_REQUEST`: the raw body fragment the client sent is never echoed back.

## 4. Correlation ids and logging

- Every response carries `X-Correlation-Id`.
- A caller-supplied `X-Correlation-Id` is echoed **only** if it matches `[A-Za-z0-9-]{1,64}`; any other value (too long, control characters, newlines) is replaced by a generated UUID. This prevents log injection through the header.
- If the header is absent, a UUID is generated.
- The identifier is placed in the SLF4J MDC for the whole request, included in the log pattern of every line, returned in the `correlationId` field of any error body, and cleared when the request finishes.

Clients should log the value from the response header and quote it in bug reports; it is the join key between a user-visible failure and the server-side stack trace.

**Never logged:** passwords, tokens, cookies, authorization headers, seed values, personal data of applicants, or full request/response bodies.

## 5. Request conventions

- Request DTOs are validated with Bean Validation annotations; invalid input never reaches application services.
- `Content-Type: application/json` is required for bodies. Unsupported types produce `UNSUPPORTED_MEDIA_TYPE`, unparseable bodies produce `MALFORMED_REQUEST`.
- Pagination, filtering and sorting conventions are introduced with the first paginated endpoint (opportunity search) in a later phase and will be documented here then.
- Authentication is not implemented yet; `401`/`403` codes are reserved and will be exercised when Spring Security lands.

## 6. Not yet implemented

Listed explicitly so no consumer assumes otherwise:

- Feature endpoints: accounts, organizations, opportunities, search, applications — later phases.
- Authentication and authorization (`401`, `403`) — Phase 4+.
- Pagination and filtering conventions — with the first paginated endpoint.
- Rate limiting, request size limits beyond container defaults, API keys — not designed yet.
- Version 2 of the API — no `tz.tdop.api.v2` package exists.

## 7. Verification

The contract above is enforced by tests in the backend module:

- `ApiContractTest` — health under the versioned path, actuator exposure policy, error body shape for `404`/`405`, correlation id echo/generation/sanitisation, OpenAPI document content, Swagger UI.
- `ValidationPipelineTest` — validation failure, valid pass-through, malformed JSON, unsupported media type.
- `GlobalExceptionHandlerTest` — field-level error mapping, and that `500`/`MALFORMED_REQUEST` bodies never echo exception internals.
- `HealthControllerTest` — `UP` maps to `200`, `DOWN`/`OUT_OF_SERVICE` map to `503`.

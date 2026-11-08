# Development Guide

**Status:** Phase 1 foundation merged to `develop` but incomplete in Testing (D-015); Phase 2 database architecture and Phase 3 backend core architecture merged to `develop` (`3e0cd70`, `513f5b5`) and Done; Phase 4 authentication and authorization starting. Flyway migrations, the development seed, database integrity tests, the `/api/v1` error contract, correlation-id logging, generated OpenAPI and health endpoints exist; identity, opportunity and application features are not yet implemented. Actual command results are in the [Phase 1](#phase-1-verification-record-2026-11-04), [Phase 2](#phase-2-verification-record-2026-11-07) and [Phase 3](#phase-3-verification-record-2026-11-08) verification records below.

## Prerequisites

- JDK 25 (the Maven Wrapper is pinned to Maven 3.10.0).
- Node.js 26.13.0 LTS as the project target. Node 24.13.1 LTS also satisfies the frontend engine requirements.
- npm 11.8 or newer.
- Docker Desktop with Docker Compose v2 for local PostgreSQL.
- Git.

The repository was developed on Windows as well as CI's Linux runner. Use the Maven Wrapper rather than a machine-global Maven installation. `.nvmrc` records the Node target.

## Environment setup

From the repository root, create a local environment file:

```powershell
Copy-Item .env.example .env
```

Review the local-only placeholder values in `.env`. Never use them in production, never commit `.env`, and never put credentials in issue reports. `.env.example` is safe to commit.

## Start local services

Start Docker Desktop, then run:

```powershell
docker compose up -d postgres
docker compose ps
```

The PostgreSQL 18.6 service binds to loopback only and persists data in the named `tdop_postgres_data` volume. Stop it without deleting the data using `docker compose down`. To delete local database data, use `docker compose down --volumes` only when intentionally resetting your own development database.

Phase 1 provides the PostgreSQL service. Phase 2 adds the Flyway schema (`V1`–`V4`), the development seed and database-backed tests. Two ways to reach a database:

- **Docker (preferred once Docker is available):** `docker compose up -d postgres` starts PostgreSQL 18.6 with the `tdop_dev`/`tdop_test` databases from `.env`.
- **Existing local PostgreSQL (D-017):** the backend reads `TDOP_DB_URL`, `TDOP_DB_USERNAME` and `TDOP_DB_PASSWORD` from the environment only — copy the commented block in `.env.example` into your gitignored `.env` and fill in your own values. Never commit them.

Tests use a separate `tdop_test` database (`TDOP_TEST_DB_*`, falling back to `TDOP_DB_*`), so a test run never touches development data.

## Run the backend

Windows PowerShell (loads values from the root `.env` file):

```powershell
./scripts/dev.ps1 backend-run
```

macOS/Linux:

```sh
make backend-run
```

The Spring Boot foundation listens on `http://localhost:8080` by default. If that port is occupied, change `SERVER_PORT` in `.env`; the Vite proxy uses the same value unless `TDOP_API_PROXY_TARGET` is set. The API has no product endpoints yet. Stop the process with `Ctrl+C`.

### Load development seed data

Seed data is applied only when the Spring `seed` profile is active (see `docs/DATA_MODEL.md` §6.2); a normal start never writes it:

```powershell
./scripts/dev.ps1 backend-run-seed
```

```sh
make backend-run-seed
```

Re-running is safe: every seed row uses a fixed UUID and `ON CONFLICT DO NOTHING`.

## Run the frontend

In a second terminal, from the repository root:

```powershell
./scripts/dev.ps1 frontend-install
./scripts/dev.ps1 frontend-dev
```

macOS/Linux:

```sh
make frontend-install
make frontend-dev
```

Vite binds to `127.0.0.1:5173` by default and uses strict port selection so it does not silently attach to another local application. If the port is occupied, set `VITE_DEV_PORT` in `.env` and restart. The minimal page only confirms the development foundation; it is not the public TDOP website. The Vite dev server proxies `/api` to the backend port selected by `SERVER_PORT` (default `8080`); `TDOP_API_PROXY_TARGET` can override the proxy target when needed.

## Quality checks

From the repository root, use `make` where available:

```sh
make backend-test
make frontend-lint
make frontend-typecheck
make frontend-format-check
make frontend-build
make check
```

On Windows, the corresponding commands are available through `scripts/dev.ps1`, for example:

```powershell
./scripts/dev.ps1 backend-test
./scripts/dev.ps1 frontend-install
./scripts/dev.ps1 frontend-lint
./scripts/dev.ps1 frontend-typecheck
./scripts/dev.ps1 frontend-format-check
./scripts/dev.ps1 frontend-build
./scripts/dev.ps1 check
```

`docker compose config` validates the Compose file; `docker compose up -d postgres` additionally requires a running Docker engine. CI runs backend verification and frontend install, lint, type-check, format-check and production build on pull requests to `develop`/`main`.

## API and configuration

The frontend's Axios client uses `VITE_API_BASE_URL` or defaults to `/api/v1`. Vite proxies `/api` to `http://127.0.0.1:8080`. API endpoints, database credentials, security tokens and production configuration will be added only in their approved phases.

## Phase 1 verification record (2026-11-04)

Actual results from the Phase 1 implementation host (Windows 11, Oracle JDK 25.0.4.1, Node 24.13.1, npm 11.8.0):

| Command | Result |
| --- | --- |
| `backend ./mvnw.cmd -B verify` | BUILD SUCCESS; 1 test run, 0 failures, 0 errors; formatter validation processed 2 files with 0 changes; boot jar produced. |
| `frontend npm ci` | 92 packages installed from the lockfile; `npm audit` reports 0 vulnerabilities. |
| `frontend npm run lint` | Passes (`oxlint --deny-warnings`). |
| `frontend npm run typecheck` | Passes (TypeScript 7.0.2 project build). |
| `frontend npm run format:check` | Passes (all matched files use Prettier code style). |
| `frontend npm run build` | Passes (Vite production build, 165 modules). |
| `./scripts/dev.ps1 check` | Passes end-to-end (runs the backend verify plus all frontend checks above). |
| Backend smoke run | Spring Boot 4.1.2 started; `GET /api/v1/nonexistent` returns the Spring JSON 404 body, confirming no product endpoints exist yet. |
| Frontend page check | `http://127.0.0.1:5176` renders the foundation page with English translations in a browser. |
| Vite `/api` proxy | `GET http://127.0.0.1:5176/api/v1/nonexistent` returns the same Spring JSON 404 as a direct backend request, proving the proxy chain. |
| `docker compose config` equivalent | `docker-compose.yml` parsed and read back correctly with a YAML parser. Docker is **not installed** on this host, so container startup, healthcheck and volume behaviour are **unverified** and recorded as an open blocker on the board. |
| GitHub Actions CI (run `41815255222`, commit `5c2526c`) | `Backend verification` **success**, `Frontend quality checks` **success**. The first CI run failed with exit 126 because the Windows-generated `mvnw` lacked the executable bit; fixed by mode change `100644 → 100755`. |
| PR #1 | Owner-approved and merged to `develop` as `50766b3` (merge commit). Board card moved Code Review → Testing. |

Note on ports: this machine already uses ports 8080, 5173 and 5174 for unrelated applications, so `.env` was set locally (gitignored) to `SERVER_PORT=8081` and `VITE_DEV_PORT=5176` for the smoke runs. The committed defaults remain 8080/5173 with strict port binding so a clash fails loudly instead of attaching to another app.

## Phase 2 verification record (2026-11-07)

Actual results from the Phase 2 implementation host (Windows 11, Oracle JDK 25.0.4.1, local PostgreSQL 18.6 service on `127.0.0.1:5432`):

| Check | Result |
| --- | --- |
| `backend ./mvnw.cmd -B verify` | **BUILD SUCCESS** (exit 0), total time 43.5 s. **Tests run: 23, Failures: 0, Errors: 0, Skipped: 0.** |
| `FlywayMigrationTest` (3 tests) | Migrations `V1`–`V4` apply from an empty database with the expected script order and all 21 application tables; every history row reports success; `flyway validate` clean. Re-applying `migrate()` is a no-op. Dropping the schema and replaying reproduces a byte-identical snapshot of tables, columns, defaults, constraints and indexes. |
| `SchemaIntegrityTest` (14 tests) | Against the real schema: FK violation `23503`, duplicate e-mail ignoring case `23505`, duplicate category code `23505`, duplicate application `23505`, duplicate saved opportunity `23505`, `closes_at < opens_at` `23514`, `EXTERNAL` without `external_url` `23514`, `PUBLISHED` without `APPROVED` `23514`, missing required column `23502`, status outside its vocabulary `23514`, non-object JSONB `23514`, `ON DELETE RESTRICT` `23001` (not `23503` — corrected after the first run), cascade delete of profile rows, and a JSONB write/read round trip. |
| `SeedProfileTest` (5 tests) | With `@ActiveProfiles("seed")`: 12 bilingual categories; versioned attribute definitions with `ENUM` allowed-values; every seeded opportunity attribute covered by its category's schema at its pinned version; 3 development actors whose password hashes are all `PLACEHOLDER%`; publication rules hold (no `PUBLISHED` without `APPROVED`, no `EXTERNAL` without a destination). |
| `formatter:validate` (2.29.0) | Passes — 0 changes required. |
| First failing runs (honest record) | The initial runs failed: a missing `ActiveProfiles` import, an undeclared `throws Exception`, `String` bound to `uuid` columns (fixed by using `java.util.UUID`), rows leaking between test classes (fixed by truncating before **and** after each class, and by scoping seed assertions to the seed UUID prefix), and the `23503`→`23001` RESTRICT correction above. All fixed; the table above is the post-fix state. |
| CI (Postgres service) | GitHub Actions run [`42391643651`](https://github.com/felix202422/Tanzanian_Opportunities/actions/runs/42391643651) on PR #23, commit `c134769`: `Backend verification` **success** (with the `postgres:18.6-alpine` service container) and `Frontend quality checks` **success**. |
| Docker / `docker compose` | **Still not verified** — Docker is not installed on this host (D-015, not waived). Phase 1 remains incomplete in Testing for this reason. |
| Board | Phase 2 card: Backlog → To Do → In Progress (verified read-back). Phase 1 card left at Testing. |

Local databases used (D-017): `tdop_dev` for development runs and `tdop_test` for tests, both owned by the non-superuser role `tdop_app`. Credentials live only in the gitignored `.env` / process environment; nothing was written into this repository. The orphaned pre-existing `tdop` database was left untouched by owner decision.

## Phase 3 verification record (2026-11-08)

Actual results from the Phase 3 implementation host (Windows 11, Oracle JDK 25.0.4.1, local PostgreSQL 18.6 service on `127.0.0.1:5432`):

| Check | Result |
| --- | --- |
| `backend ./mvnw.cmd -B verify` | **BUILD SUCCESS** (exit 0). **Tests run: 48, Failures: 0, Errors: 0, Skipped: 0** — 23 Phase 2 tests plus 25 new Phase 3 tests. Boot jar produced. |
| `GlobalExceptionHandlerTest` (7) | Validation failures carry every invalid field plus the MDC correlation id; a `500` built from an exception whose message contains a fake password returns `INTERNAL_ERROR` with **no** echo of the message, exception class or `password`; malformed-body errors never echo the raw fragment; `404`/`405`/`MISSING_PARAMETER` map to their documented codes and `405` lists supported methods. |
| `HealthControllerTest` (3) | `UP` → `200 {"status":"UP"}`; `DOWN` and `OUT_OF_SERVICE` → `ResponseStatusException` with `503`. (Boot 4 sealed `HealthDescriptor`, so a test-only factory builds real descriptors — `TestHealthDescriptors` in the Boot package, test sources only.) |
| `ApiContractTest` (11) | `GET /api/v1/health` → 200 `UP`; unversioned `/health` → `404 NOT_FOUND`; actuator exposes `health`, `liveness`, `readiness` (200 or 503) only — `/actuator/env`, `/actuator/beans`, `/actuator/health/all` → 404; documented error body for 404/405; correlation id echoed, generated as a UUID when absent, and replaced when hostile (`not a safe log value <script>`); `/v3/api-docs` returns OpenAPI 3 with title `Tanzania Digital Opportunity Platform API` and path `/api/v1/health`; Swagger UI served. |
| `ValidationPipelineTest` (4) | Invalid DTO → `400 VALIDATION_FAILED` with 2 `fieldErrors`; valid DTO passes through; truncated JSON → `MALFORMED_REQUEST` without echoing the fragment; `application/xml` → `415 UNSUPPORTED_MEDIA_TYPE`. |
| `formatter:format` + `formatter:validate` (2.29.0) | Run before verify; 0 changes required. |
| Dependency checks | `springdoc-openapi-starter-webmvc-ui:3.2.0` confirmed present in Maven Central metadata and documented for Spring Boot 4; `spring-boot-starter-validation` and `spring-boot-starter-actuator` resolved at 4.1.2. Boot 4 relocation handled: health classes now live in `org.springframework.boot.health.*`, and MockMvc test support moved to `spring-boot-starter-webmvc-test` (added test-scope). |
| Honest record of failures fixed | First compile pass failed on the relocated `HealthEndpoint`/`Status` packages, `Enum::name` on Spring Framework 7's non-enum `HttpMethod`, an `HttpStatus` vs `HttpStatusCode` overload mismatch and a missing import — all fixed before tests ran. The first test run failed with no DB password (`.env` not loaded) and with Mockito unable to mock the sealed `HealthDescriptor` — both fixed; the table above is the post-fix state. |
| CI | GitHub Actions run [`42482770203`](https://github.com/felix202422/Tanzanian_Opportunities/actions/runs/42482770203) on PR #24, commit `4106ebd`: `Backend verification` **success** and `Frontend quality checks` **success**; `mergeable_state=clean`. PR merged by owner approval as `513f5b5`. |
| Docker / `docker compose` | **Still not verified** — Docker is not installed on this host (D-015, not waived). Phase 1 remains incomplete in Testing for this reason. |
| Board | Phase 3 card: Backlog → To Do → In Progress → Code Review → Testing → Done, each move with an evidence comment. Phase 1 card left at Testing; Phase 2 card Done. |

New in this phase: `docs/API_CONTRACT.md` documents versioning, the endpoint table, the error-code table and examples, correlation/logging rules and the not-yet-implemented list. The contract is enforced by the four test classes above.

## Current environment note

The implementation host has JDK 25.0.4.1, Maven 3.9.16, Node 24.13.1 and npm 11.8.0. The project wrappers target Maven 3.10.0 and Node 26.13.0; the installed Node 24 release meets the declared engine range. The Docker CLI is not installed in the implementation environment, so Compose startup remains unverified until Docker Desktop is available. Phase 2 therefore verifies against the host's own PostgreSQL 18.6 service rather than the Compose container.

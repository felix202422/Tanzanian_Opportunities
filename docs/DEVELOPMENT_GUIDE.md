# Development Guide

**Status:** Phase 1 foundation. The backend and frontend are scaffolded; opportunity, identity, database and application features are not yet implemented. Runtime checks are recorded in the Phase 1 completion report.

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

Phase 1 provides the PostgreSQL service but no Flyway schema or database-backed application feature; those are Phase 2 and later.

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

## Current environment note

The Phase 1 implementation host has JDK 25.0.4.1, Maven 3.9.16, Node 24.13.1 and npm 11.8.0. The project wrappers target Maven 3.10.0 and Node 26.13.0; the installed Node 24 release meets the declared engine range. The Docker CLI is not installed in the implementation environment, so Compose startup remains unverified until Docker Desktop is available.

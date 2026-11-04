# Tanzania Digital Opportunity Platform (TDOP)

TDOP is a planned bilingual platform for discovering, evaluating, publishing and managing educational, professional and business opportunities, with an initial focus on Tanzania.

## Project status

**Phase 1 — Repository foundation merged; incomplete in Testing.** Phase 0 is marked complete by the project owner. The Phase 0 planning baseline, including `VERSIONS.md`, is published on `main` and `develop`; the Phase 1 foundation was delivered through [PR #1](https://github.com/felix202422/Tanzanian_Opportunities/pull/1), owner-approved and merged to `develop` as `50766b3` with both CI jobs green. The phase is **not Done**: the required `docker compose` startup check could not be run because Docker is not installed here, and the owner declined to waive it (see [D-015](docs/DECISIONS.md)), so its card stays in Testing on [TDOP — Development Roadmap](https://github.com/users/felix202422/projects/3). Phase 2 has not been started. Backend/frontend scaffolds and quality tooling all pass locally. No opportunity, authentication or other product features are implemented yet.

## Objectives

- Make legitimate opportunities easier to find and evaluate.
- Connect seekers with verified opportunity providers.
- Help seekers manage saved opportunities, applications and deadlines.
- Support English and Swahili and accessible use across desktop and mobile.
- Provide organizations and administrators with trustworthy publishing and moderation workflows.

## Planned first release

The initial release is planned to cover public discovery, seeker accounts and profiles, saved opportunities, organization verification and publishing, administrator moderation, bilingual core interfaces, basic in-app notifications, and both application modes:

- **External:** link to the provider’s official application destination; TDOP does not represent an external submission as a TDOP-managed application.
- **Internal:** submit a profile-backed structured application and track its status. Application file attachments are out of the initial release scope; opportunity images/profile pictures and private organization verification documents remain planned platform uploads.

Personalized recommendations, source integrations, SMS/WhatsApp delivery, advanced analytics and international-specific features are later work. The detailed boundary is in [Requirements](docs/REQUIREMENTS.md).

## Main capabilities planned

- Browse and search across 12 extensible opportunity categories.
- Manage seeker profiles, saved opportunities and internal applications.
- Register organizations and require verification before public publishing.
- Review opportunities, organizations and reports through protected administration workflows.
- Provide English and Swahili user interfaces.

These are plans, not implemented features. See the [roadmap](docs/ROADMAP.md) for phase status.

## Technology direction

The Phase 1 foundation uses Java 25 LTS, Spring Boot 4.1.2, Maven Wrapper 3.10.0, PostgreSQL 18.6, React 19.3.1, TypeScript 7.0.2, Vite 8.3.5 and Tailwind CSS 4.3.4. Compatibility rationale and exact tool versions are in [VERSIONS.md](VERSIONS.md) and [architecture decisions](docs/DECISIONS.md).

## Architecture overview

The planned system is a modular Spring Boot monolith exposing REST APIs under `/api/v1`, with a separate React single-page application, PostgreSQL as the system of record, and private object storage for uploaded media. Nginx is the planned production edge/reverse proxy. See [System architecture](docs/SYSTEM_ARCHITECTURE.md).

## Planned repository structure

```text
TDOP/
├── backend/                 # Spring Boot foundation, Maven Wrapper and tests
├── frontend/                # React/TypeScript app, Vite config and npm lockfile
├── infrastructure/          # Nginx and production deployment (later phases)
├── scripts/                 # Windows development helper
├── docs/                    # Product, architecture and development docs
├── .github/                 # CI, issue forms and pull-request template
├── docker-compose.yml       # Local PostgreSQL service
├── .env.example             # Safe local configuration template
├── Makefile                 # POSIX development commands
└── README.md
```

The Phase 1 skeleton is in place. Product features remain unimplemented until their approved phases.

## Prerequisites and local setup

Development uses JDK 25, the Maven Wrapper (Maven 3.10.0), Node.js 26 LTS and npm. Docker Compose is required for the local PostgreSQL service. Detailed setup and platform-specific commands are in [docs/DEVELOPMENT_GUIDE.md](docs/DEVELOPMENT_GUIDE.md).

## Environment and database

Copy `.env.example` to `.env` for local services. The real `.env` is ignored by Git; do not commit secrets or production data. PostgreSQL runs through Compose; migrations and seed data are scheduled for Phase 2.

## Running and testing

Run `./scripts/dev.ps1 check` on Windows or `make check` where Make is available. The backend context test and frontend lint, type-check, format and production build are configured. Docker Compose startup still needs validation on a host with Docker installed. No product/API features or related tests are claimed.

## API documentation

The planned API prefix is `/api/v1`. OpenAPI documentation will be exposed by the backend after Phase 3; there is no live API or Swagger UI yet.

## Screenshots

None yet. Screenshots will be added after the public website and responsive interfaces are implemented and reviewed.

## Contributing

Read [CONTRIBUTING.md](CONTRIBUTING.md) and the [Code of Conduct](CODE_OF_CONDUCT.md). The documented workflow uses `main`, `develop`, feature branches and pull requests; Phase 1 was delivered through [PR #1](https://github.com/felix202422/Tanzanian_Opportunities/pull/1) into `develop`. Track phase state on the live board, [TDOP — Development Roadmap](https://github.com/users/felix202422/projects/3), using Backlog → To Do → In Progress → Code Review → Testing → Done.

## Security

Read [SECURITY.md](SECURITY.md). Do not publish vulnerability details or secrets in public issues.

## License

TDOP is planned to be distributed under the [Apache License 2.0](LICENSE).

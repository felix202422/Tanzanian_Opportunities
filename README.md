# Tanzania Digital Opportunity Platform (TDOP)

TDOP is a planned bilingual platform for discovering, evaluating, publishing and managing educational, professional and business opportunities, with an initial focus on Tanzania.

## Project status

**Phase 0 — Project foundation and planning.** The local workspace and public GitHub repository were empty at inspection. The Phase 0 planning baseline, including `VERSIONS.md`, is published on `main` and `develop`. There is no runnable backend, frontend, database schema or deployment configuration yet. The requested live Kanban board is approved but not provisioned because this session is not authenticated to GitHub; see the [roadmap](docs/ROADMAP.md). Phase 1 will begin only after separate owner approval.

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

The proposed stack is Java 25 LTS with Spring Boot 4.1.2, PostgreSQL 18.6 with Flyway, and a React 19/TypeScript frontend built with Vite and Tailwind CSS 4. Versions were checked on 2026-11-04; see the [version baseline](VERSIONS.md) and [architecture decisions](docs/DECISIONS.md). Re-check and lock versions during Phase 1 before project initialization.

## Architecture overview

The planned system is a modular Spring Boot monolith exposing REST APIs under `/api/v1`, with a separate React single-page application, PostgreSQL as the system of record, and private object storage for uploaded media. Nginx is the planned production edge/reverse proxy. See [System architecture](docs/SYSTEM_ARCHITECTURE.md).

## Planned repository structure

```text
TDOP/
├── backend/                 # Spring Boot application (Phase 1+)
├── frontend/                # React + TypeScript application (Phase 1+)
├── infrastructure/          # Nginx and deployment configuration (later phases)
├── scripts/                 # Cross-platform development helpers
├── docs/                    # Product, architecture and delivery documentation
├── .github/                 # CI, issue and pull-request templates (Phase 1+)
├── docker-compose.yml       # Local services (Phase 1+)
├── .env.example             # Safe configuration template (Phase 1+)
└── README.md
```

Only Phase 0 documentation and repository policy files are present locally at this stage. Application directories and runtime configuration have not been created.

## Prerequisites and local setup

Candidate development prerequisites are JDK 25, Maven 3.10, Node.js 26 LTS, npm, Docker Compose and PostgreSQL 18 (normally supplied through Docker for development). Exact installation instructions and cross-platform commands will be verified and added in Phase 1. **There is no application to install or run yet.**

## Environment and database

No `.env` file or credentials are needed in Phase 0. A safe `.env.example`, local PostgreSQL service, Flyway migrations and development seed data are planned for their assigned phases. Never commit real secrets or production data.

## Running and testing

Backend, frontend, Docker, database, build and automated-test commands are **not available until their implementation phases**. No runtime or application tests have been run for this planning-only phase. Phase 1 will add validated commands; later phase gates will report actual test results.

## API documentation

The planned API prefix is `/api/v1`. OpenAPI documentation will be exposed by the backend after Phase 3; there is no live API or Swagger UI yet.

## Screenshots

None yet. Screenshots will be added after the public website and responsive interfaces are implemented and reviewed.

## Contributing

Read [CONTRIBUTING.md](CONTRIBUTING.md) and the [Code of Conduct](CODE_OF_CONDUCT.md). The documented workflow uses `main`, `develop`, feature branches and pull requests. The initial Phase 0 baseline is published to both branches. Live Kanban board creation is approved but awaits GitHub authentication; see [CONTRIBUTING.md](CONTRIBUTING.md) and [the roadmap](docs/ROADMAP.md).

## Security

Read [SECURITY.md](SECURITY.md). Do not publish vulnerability details or secrets in public issues.

## License

TDOP is planned to be distributed under the [Apache License 2.0](LICENSE).

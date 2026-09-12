# TDOP — Tanzania Digital Opportunity Platform

A digital jobs and opportunities platform connecting **job seekers**, **organizations**, and **administrators** in Tanzania.

This is a **single repository** containing the backend API, the web frontend, and Docker infrastructure.

## Repository Layout

| Folder | Description | Stack |
|---|---|---|
| [`TDOP-backend/`](TDOP-backend/README.md) | REST API | Java 21, Spring Boot 3.3, Spring Security + JWT, PostgreSQL, Flyway |
| [`TDOP-frontend/`](TDOP-frontend/README.md) | Web application | React 18, TypeScript, Vite, Tailwind CSS, React Router, i18next |
| [`TDOP-infra/`](TDOP-infra/) | Deployment | Docker Compose, Nginx |

## Features

- JWT-based authentication with three roles: **Seeker**, **Organization**, **Admin**
- Opportunity listings, search, and filtering
- Applications and application status tracking (pending, reviewed, interview, offer, rejected)
- Saved opportunities and side-by-side opportunity comparison
- Organization verification workflow (admin approval)
- Reporting with an admin review queue
- Admin analytics dashboard and user management (suspend / change role)
- Notifications (in-app + email)
- English / Swahili localization

## Quick Start (local development)

1. **Database** — create a PostgreSQL database (default names: `tdop` for prod-like, `tdop_dev` for the dev profile). Flyway creates and seeds the schema on backend startup.

2. **Backend**
   ```bash
   cd TDOP-backend
   cp .env.example .env   # set DB_PASSWORD
   mvn spring-boot:run
   ```
   Serves on `http://localhost:8081` (API base `/api/v1`).

3. **Frontend**
   ```bash
   cd TDOP-frontend
   cp .env.example .env
   npm install
   npm run dev
   ```
   Serves on `http://localhost:3000`, proxying `/api` to the backend.

   > Requires Node 18+ and Java 21 / Maven 3.8+.

## Seeded Demo Accounts

Flyway seeds sample users (`TDOP-backend/src/main/resources/db/migration/V2__seed_data.sql`):

| Role | Email | Password |
|---|---|---|
| Seeker | `john.mwangi@email.com` | `seeker123` |
| Organization | `info@tanzgold.com` | `org123` |
| Admin | `admin@tdop.go.tz` | `admin123` |

## Docker Deployment

```bash
cd TDOP-infra
cp .env.example .env   # REQUIRED: set POSTGRES_PASSWORD
docker compose up -d --build
# development overlay:
docker compose -f docker-compose.yml -f docker-compose.dev.yml up -d --build
```

Services: `tdop-postgres` (PostgreSQL 16), `tdop-backend`, `tdop-adminer` (DB browser, port 8081).

## Environment Variables

Secrets are never committed to this repository. Every credential is supplied at runtime via environment variables — see:

- [`TDOP-backend/.env.example`](TDOP-backend/.env.example) — `DB_PASSWORD`, `JWT_SECRET`, `MAIL_*`
- [`TDOP-frontend/.env.example`](TDOP-frontend/.env.example) — `VITE_API_URL`
- [`TDOP-infra/.env.example`](TDOP-infra/.env.example) — `POSTGRES_USER`, `POSTGRES_PASSWORD`

## License

MIT
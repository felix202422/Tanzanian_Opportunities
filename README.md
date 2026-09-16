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

### Prerequisites

- Java 21 (JDK 21+)
- Maven 3.8+
- Node.js 18+
- PostgreSQL running locally (port 5432)

### 1. Database

Create a PostgreSQL database (default names: `tdop`, or `tdop_dev` for the `dev` profile). Flyway creates and seeds the schema on backend startup — see `TDOP-backend/src/main/resources/db/migration/`.

### 2. Backend

```bash
cd TDOP-backend
cp .env.example .env   # set DB_PASSWORD
mvn spring-boot:run
```

Serves on `http://localhost:8080` (API base `/api/v1`).

### 3. Frontend

```bash
cd TDOP-frontend
cp .env.example .env
npm install
npm run dev
```

Serves on `http://localhost:3000`, proxying `/api` to the backend.

## Seeded Demo Accounts

Flyway seeds 11 sample users in `V2__seed_data.sql`:

### Admins

| Email | Password |
|---|---|
| `admin@tdop.go.tz` | `admin123` |
| `superadmin@tdop.go.tz` | `superadmin` |
| `admin2024@tdop.go.tz` | `admin2024` |

### Seekers (all `seeker123`)

| Email | Name |
|---|---|
| `john.mwangi@email.com` | John Mwangi |
| `amina.hassan@email.com` | Amina Hassan |
| `peter.okech@email.com` | Peter Okech |
| `fatima.bakari@email.com` | Fatima Bakari |
| `david.mwinyi@email.com` | David Mwinyi |

### Organizations (all `org123`)

| Email | Name | Verified |
|---|---|---|
| `info@tanzgold.com` | Tanzania Gold Mining Ltd | Yes |
| `contact@safaricomTZ.com` | Safaricom Tanzania PLC | No |
| `hr@crdbbank.com` | CRDB Bank PLC | No |

## Docker Deployment

```bash
cd TDOP-infra
cp .env.example .env   # REQUIRED: set POSTGRES_PASSWORD
docker compose up -d --build
# development overlay:
docker compose -f docker-compose.yml -f docker-compose.dev.yml up -d --build
```

Services: `tdop-postgres` (PostgreSQL 16), `tdop-backend` (port 8080), `tdop-adminer` (DB browser, port 8082).

## Environment Variables

Secrets are never committed to this repository. Every credential is supplied at runtime via environment variables:

- [`TDOP-backend/.env.example`](TDOP-backend/.env.example) — `DB_USERNAME`, `DB_PASSWORD`, `JWT_SECRET`, `MAIL_*`
- [`TDOP-frontend/.env.example`](TDOP-frontend/.env.example) — `VITE_API_URL`
- [`TDOP-infra/.env.example`](TDOP-infra/.env.example) — `POSTGRES_USER`, `POSTGRES_PASSWORD`

## License

MIT

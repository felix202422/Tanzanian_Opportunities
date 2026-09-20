# TDOP — Tanzania Digital Opportunity Platform

A digital opportunity discovery and progress platform connecting **seekers**, **organizations**, and **platform operators** in Tanzania.

**Single repository** — backend API, web frontend, and Docker infrastructure.

## Repository Layout

| Folder | Stack |
|---|---|
| `TDOP-backend/` | Java 21, Spring Boot 3.3, Spring Security + JWT, PostgreSQL, Flyway |
| `TDOP-frontend/` | React 18, TypeScript, Vite, Tailwind CSS, React Router |
| `TDOP-infra/` | Docker Compose, Nginx |
| `Docs/` | Master specification and roadmap |

## Current Implementation Status

### Implemented

- JWT authentication (login, register, refresh, logout, forgot/reset password)
- 9 platform roles: `SEEKER`, `ORGANIZATION`, `ORGANIZATION_ADMIN`, `ORGANIZATION_MEMBER`, `VERIFICATION_OFFICER`, `MODERATOR`, `ADMIN`, `SUPER_ADMIN`
- User profiles (education, skills, experience, interests, career goals)
- Organization profiles with verification workflow
- Opportunity listings with full lifecycle (DRAFT → SUBMITTED → VERIFIED → APPROVED → PUBLISHED → CLOSING_SOON → EXPIRED)
- Applications with status tracking and history
- Saved opportunities and side-by-side comparison
- Moderation workflow (approve/reject/suspend/archive/request-information)
- Report investigation (create → assign → investigate → resolve/dismiss)
- Escalation and appeal systems
- Anti-fraud risk signal detection
- Deadline engine (hourly: expire, closing_soon, reminders with email delivery)
- In-app notifications and email notifications (SMTP)
- File upload (local filesystem with path traversal protection)
- Platform configuration (key-value store in DB)
- RBAC infrastructure (roles, permissions, role-permission assignments)
- Admin dashboard, user management, analytics, audit log
- Trust workspace (verification, moderation, reports, escalations, appeals)
- Super Admin workspace (18 pages: governance, security, intelligence, taxonomy, features, sessions, notifications, integrations, background jobs)
- English/Swahili partial localization
- Multi-field search (title, description, category, tags) with filtered search endpoint
- Opportunity recommendations (skill/interest/location matching)
- Similar opportunities engine
- Social sharing (WhatsApp, Twitter, LinkedIn, Facebook)
- Weekly digest email service (Monday 8AM cron)
- Security headers (X-Frame-Options, HSTS, Referrer-Policy, XSS-Protection)
- Account lockout (5 failed attempts → 15min lockout)
- Login rate limiting (20 requests/min/IP)
- Password reset token expiry (30min)
- Email verification tokens with expiry (60min)
- Opportunity ownership checks (org members can only manage their own)
- Lifecycle state machine validation (Opportunity, Application, Report)
- Production-ready Dockerfile (non-root user, multi-stage build)
- Frontend Dockerfile with nginx (SPA fallback, security headers, gzip)
- Production logging (logback: console + rolling file)

### Planned / Not Yet Implemented

- Redis caching and token revocation
- Push notifications (web/mobile)
- WhatsApp / SMS integration
- Payment / subscription engine
- Machine learning matching
- Elasticsearch
- E2E testing, load testing
- Full accessibility (WCAG 2.2 AA)

## Quick Start (Local Development)

### Prerequisites

- Java 21+
- Maven 3.8+
- Node.js 18+
- PostgreSQL (port 5432)

### 1. Database

Create a PostgreSQL database named `tdop`. Flyway creates and seeds the schema on backend startup.

### 2. Backend

```bash
cd TDOP-backend
cp .env.example .env   # set DB_PASSWORD, JWT_SECRET
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

Flyway seeds 13 users in `V2__seed_data.sql` and `V8__sample_organizations.sql`:

| Email | Role | Password |
|---|---|---|
| `admin@tdop.go.tz` | ADMIN | `admin123` |
| `superadmin@tdop.go.tz` | ADMIN | `superadmin` |
| `admin2024@tdop.go.tz` | ADMIN | `admin2024` |
| `john.mwangi@email.com` | SEEKER | `seeker123` |
| `amina.hassan@email.com` | SEEKER | `seeker123` |
| `info@tanzgold.com` | ORGANIZATION (verified) | `org123` |
| `contact@safaricomTZ.com` | ORGANIZATION | `org123` |
| `hr@crdbbank.com` | ORGANIZATION | `org123` |
| `careers@vodacom.co.tz` | ORGANIZATION (verified) | `org123` |
| `hr@nmbbank.com` | ORGANIZATION | `org123` |

## Docker Deployment

```bash
cd TDOP-infra
cp .env.example .env   # REQUIRED: set POSTGRES_PASSWORD and JWT_SECRET
docker compose up -d --build
```

Services: `tdop-postgres` (PostgreSQL 16), `tdop-backend` (port 8080), `tdop-frontend` (port 3000), `tdop-adminer` (port 8082).

## Environment Variables

| File | Variables |
|---|---|
| `TDOP-backend/.env.example` | `DB_USERNAME`, `DB_PASSWORD`, `JWT_SECRET`, `MAIL_*` |
| `TDOP-frontend/.env.example` | `VITE_API_URL` |
| `TDOP-infra/.env.example` | `POSTGRES_USER`, `POSTGRES_PASSWORD`, `JWT_SECRET` |

## Documentation

| Document | Purpose |
|---|---|
| `README.md` | This file — repository overview |
| `README_PRD.md` | Product requirements document |
| `Docs/TDOP_MASTER_SPEC.md` | Architecture and implementation specification |

## License

MIT

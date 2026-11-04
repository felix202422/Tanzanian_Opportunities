# Architecture and Planning Decisions

Decisions below are planning baselines recorded on **2026-11-04**. The standalone [version baseline](../VERSIONS.md) records Phase 1 selections and compatibility rationale. Significant changes should be documented here with rationale and impact.

## D-001 — Start with a modular monolith

**Status:** Accepted for planning. Use one Spring Boot backend organized around clear feature boundaries and a separate React SPA in a monorepo. This minimizes operational complexity while preserving modularity. Do not introduce microservices until measured scale or ownership boundaries justify them.

## D-002 — Select a version-compatible Java/Spring baseline

**Status:** Accepted as the Phase 1 candidate. JDK 25 LTS is a suitable baseline: OpenJDK records general availability on 2025-09-16 and says it is LTS for most vendors. Spring Boot 4.1.2 is listed as stable, requires at least Java 17, and supports Java through 27, so JDK 25 is within its documented range. Spring Boot 4.2.0-RC1 is a preview and is not selected.

| Component | Candidate as checked | Compatibility / selection note |
| --- | --- | --- |
| Java | JDK 25 LTS | CI uses Temurin 25; local validation uses the available Oracle JDK 25.0.4.1. |
| Spring Boot | 4.1.2 | Official system requirements: Java 17–27; Spring Framework 7.0.10+. Stable channel. |
| Maven | Wrapper 3.10.0; wrapper plugin 3.3.4 | Apache lists 3.10.0 as current recommended; Maven 4.0.0-rc-7 is a preview and not selected. |
| Java formatting | Formatter Maven Plugin 2.29.0 | Eclipse JDT formatter configured for Java 25 compliance and LF line endings. |
| Springdoc OpenAPI | 3.2.0 | Current docs state support for Spring Boot 4 and Java 17+. Verify the chosen Spring MVC starter during backend scaffold. |
| Spring Security, Hibernate, JUnit, Testcontainers and Flyway | Spring Boot 4.1.2 dependency management where possible | Avoid independent overrides; verify required Flyway PostgreSQL module and Testcontainers integration while scaffolding. |

Sources: [OpenJDK JDK 25](https://openjdk.org/projects/jdk/25/), [Spring Boot requirements](https://docs.spring.io/spring-boot/system-requirements.html), [Apache Maven downloads](https://maven.apache.org/download.cgi), [Springdoc documentation](https://springdoc.org/).

## D-003 — PostgreSQL is the relational system of record

**Status:** Accepted for planning. Use PostgreSQL with Flyway migrations; do not rely on Hibernate schema auto-generation in production. PostgreSQL 18.6 is the current minor for supported major 18 in the official version table and is supported through 2030-11-14. PostgreSQL 19 was at release-candidate stage when checked and is not selected. Recheck the current minor before Phase 1.

Source: [PostgreSQL versioning and support](https://www.postgresql.org/support/versioning/).

## D-004 — Normalize core records and validate extensible category fields

**Status:** Accepted for design; detailed schema deferred to Phase 2. Use UUID identifiers where appropriate, normalized relational entities, UTC timestamps, foreign keys, uniqueness constraints and targeted indexes. Keep opportunity fields common to all categories as typed columns. Use only a bounded, versioned JSONB extension for category-specific fields, validated by server-side category rules. Phase 2 must produce the ER design and verify query/index implications before creating migrations.

## D-005 — Use REST DTO contracts under `/api/v1`

**Status:** Accepted. REST controllers communicate through validated request/response DTOs, not JPA entities. Use consistent error bodies, pagination and OpenAPI. Business authorization remains in Spring Security/services and checks roles, ownership and organization membership.

## D-006 — Use short-lived access JWTs with rotating refresh credentials

**Status:** Accepted for design; cookie/CORS behavior requires environment tests. Keep short-lived signed access JWTs in browser memory. Use high-entropy opaque refresh tokens in Secure, HttpOnly, SameSite cookies; store token hashes, rotate on refresh, detect reuse and revoke sessions. Apply CSRF protections to cookie-authenticated endpoints. Prefer same-origin routing in production where practical. Do not use local storage for long-lived bearer credentials.

## D-007 — Support both internal and external application modes

**Status:** Accepted for initial-release planning. External-mode opportunities link to the provider’s official destination and are not represented as internally submitted applications. Internal-mode submissions use profile-backed structured form data and status history, with duplicate prevention. Application file attachments are out of the initial release; revisit after storage, retention and privacy requirements are settled.

## D-008 — Keep private uploads outside PostgreSQL

**Status:** Accepted for planning. Profile images and organization verification documents use a private object-storage abstraction compatible with S3. Validate file type, size and content; use generated storage keys, malware scanning where available and authorization-controlled access. Production vendor, region, retention and backup design are deferred to Phase 17.

## D-009 — Localize UI while storing canonical times

**Status:** Accepted. Use i18next with English and Swahili UI resources. Store event times in UTC; display with locale-aware formatting and `Africa/Dar_es_Salaam` as the initial user-facing default. Preserve a user-selected language preference. Organization-provided copy is not assumed to be translated.

## D-010 — Use a compatible React/Vite/Tailwind baseline

**Status:** Accepted and rechecked during Phase 1. Node.js 26.13.0 is the target LTS; local Node 24.13.1 also meets Vite 8.3.5 (`^20.19.0 || >=22.12.0`) and React Router 8.5.1 (`>=22.22.0`) engine floors. React Router requires React/DOM `>=19.2.7`, so React 19.3.1 satisfies it. The Vite React plugin peers with Vite 8; Tailwind’s Vite plugin accepts Vite 5–8. Oxlint 1.91.0 provides native JavaScript, TypeScript, React, React Hooks and accessibility rules, so the project can use TypeScript 7.0.2 without ESLint plugin peer incompatibilities.

| Package | Candidate |
| --- | --- |
| Node.js | 26.13.0 LTS |
| React / React DOM | 19.3.1 / 19.3.1 |
| TypeScript | 7.0.2 |
| Vite / `@vitejs/plugin-react` | 8.3.5 / 6.2.1 |
| Tailwind CSS / `@tailwindcss/vite` | 4.3.4 / 4.3.4 |
| React Router | 8.5.1 |
| TanStack React Query | 5.105.3 (React 18/19 peer support) |
| React Hook Form / resolvers / Zod | 7.91.0 / 7.0.2 / 4.6.7 (resolver supports Zod 4 and RHF 7) |
| Axios | 1.21.0 |
| Motion | 14.9.1 (React 18/19 peer support) |
| Lucide React | 1.68.0 (React 19 peer support) |
| i18next / react-i18next | 26.7.1 / 17.1.12 (React and TypeScript 7 peer support) |

Sources: [Node.js releases](https://nodejs.org/en/about/previous-releases), [React versions](https://react.dev/versions), [Vite package metadata](https://registry.npmjs.org/vite/latest), [Vite React plugin metadata](https://registry.npmjs.org/%40vitejs%2Fplugin-react/latest), [Tailwind package metadata](https://registry.npmjs.org/tailwindcss/latest), [Tailwind Vite plugin metadata](https://registry.npmjs.org/%40tailwindcss%2Fvite/latest), [TypeScript download](https://www.typescriptlang.org/download), [React Router metadata](https://registry.npmjs.org/react-router/latest), [TanStack Query metadata](https://registry.npmjs.org/%40tanstack%2Freact-query/latest), [React Hook Form metadata](https://registry.npmjs.org/react-hook-form/latest), [resolver metadata](https://registry.npmjs.org/%40hookform%2Fresolvers/latest), [Zod metadata](https://registry.npmjs.org/zod/latest), [Axios metadata](https://registry.npmjs.org/axios/latest), [Motion metadata](https://registry.npmjs.org/motion/latest), [Lucide React metadata](https://registry.npmjs.org/lucide-react/latest), [i18next metadata](https://registry.npmjs.org/i18next/latest), [react-i18next metadata](https://registry.npmjs.org/react-i18next/latest).

This is a candidate compatibility matrix, not a tested lockfile. Phase 1 must create reproducible lockfiles and verify install, build, lint and type-check on the target environment. Use TypeScript strict mode and do not introduce `any` without justification.

## D-011 — Bootstrap the documented Git workflow; defer the live board

**Status:** Branch workflow bootstrapped; live board pending authentication. Use `main`, `develop`, `feature/*`, `fix/*` and `docs/*` with pull requests and Conventional Commit messages. Track work through Backlog → To Do → In Progress → Code Review → Testing → Done. The owner authorized the initial commit and the Phase 0 baseline was pushed to both `main` and `develop`. The owner has since approved live board creation, but this session is not authenticated to GitHub; no project or issue cards have been created. See [Roadmap](ROADMAP.md).

## D-012 — License the project under Apache-2.0

**Status:** Selected by the owner for Phase 0. The root `LICENSE` contains Apache License 2.0 terms. Confirm ownership and any third-party notice obligations as contributions and dependencies are added.

## D-013 — Defer deployment vendor and legal conclusions

**Status:** Accepted. Keep cloud hosting and object storage vendor-neutral through architecture planning. Before production, review applicable Tanzanian privacy/data-protection obligations and any additional jurisdiction requirements with qualified counsel; these documents do not claim legal compliance or select data residency.

## D-014 — Treat repository truth and product data honestly

**Status:** Accepted. Documentation, product status and analytics must distinguish plans from implemented behavior. Production dashboard statistics must come from actual backend data. Development seed data is isolated from production.

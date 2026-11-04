# Technology Version Baseline

**Checked:** 2026-11-04

**Status:** Phase 0 planning recommendation only. No dependencies are installed, no manifests or lockfiles exist, and these versions have not been built together in this repository. Recheck official sources and run install/build/type-check validation during Phase 1 before locking them.

## Backend and database

| Component | Candidate version | Basis |
| --- | --- | --- |
| Java JDK | 25 LTS | OpenJDK 25 reached GA on 2025-09-16 and is an LTS release for most vendors. Select the maintained vendor distribution in Phase 1. |
| Spring Boot | 4.1.2 | Listed as stable; documented support is Java 17 through 27 and Spring Framework 7.0.10 or later. Java 25 is within the supported range. |
| Springdoc OpenAPI | 3.2.0 | Current Springdoc documentation lists Spring Boot 4 and Java 17+ support. Use the MVC starter for the planned Spring MVC REST API. |
| Apache Maven | 3.10.0 | Apache lists this as its current recommended release. Maven 4.0.0-rc-7 is a preview and is not selected for production. Use Maven Wrapper. |
| PostgreSQL | 18.6 | Current minor release listed for supported PostgreSQL 18; support is listed through 2030-11-14. PostgreSQL 19 was at release-candidate stage when checked. |
| Spring Security, Hibernate, Flyway, JUnit 5 and Testcontainers | Spring Boot 4.1.2 managed versions where possible | Prefer Spring Boot's dependency management to avoid incompatible independent overrides. Confirm the PostgreSQL Flyway module and Testcontainers integration during backend initialization. |

Sources: [OpenJDK 25](https://openjdk.org/projects/jdk/25/), [Spring Boot system requirements](https://docs.spring.io/spring-boot/system-requirements.html), [Springdoc](https://springdoc.org/), [Apache Maven downloads](https://maven.apache.org/download.cgi), [PostgreSQL versioning](https://www.postgresql.org/support/versioning/).

## Frontend runtime and framework

| Component | Candidate version | Compatibility basis |
| --- | --- | --- |
| Node.js | 26.13.0 LTS | Listed as the current LTS. Vite 8 requires `^20.19.0 || >=22.12.0`; React Router 8 requires `>=22.22.0`. Node 26 meets both. |
| React / React DOM | 19.3.1 / 19.3.1 | Matching versions; React Router 8 requires React/React DOM 19.2.7 or newer. |
| TypeScript | 7.0.2 | Current stable package version at check time. Keep strict compiler settings; verify the complete toolchain with a Phase 1 build. |
| Vite / `@vitejs/plugin-react` | 8.3.5 / 6.2.1 | React plugin declares Vite 8 compatibility. |
| Tailwind CSS / `@tailwindcss/vite` | 4.3.4 / 4.3.4 | Matching releases; the Vite adapter accepts Vite 5 through 8. |
| React Router | 8.5.1 | Requires Node `>=22.22.0` and React/React DOM `>=19.2.7`; compatible with the selected runtime/framework candidates. |

## Frontend libraries

| Package | Candidate version | Compatibility note |
| --- | --- | --- |
| `@tanstack/react-query` | 5.105.3 | Peer support for React 18 and 19. |
| `react-hook-form` | 7.91.0 | Peer support includes React 19. |
| `@hookform/resolvers` | 7.0.2 | Supports React Hook Form 7 and Zod 4. |
| `zod` | 4.6.7 | Selected schema validation major; integrate through the compatible resolver. |
| `axios` | 1.21.0 | Use a centralized client with explicit timeout, error and credential policy. |
| `motion` | 14.9.1 | Peer support includes React 18 and 19; prefer restrained, reduced-motion-aware animation. |
| `lucide-react` | 1.68.0 | Peer support includes React 19. |
| `i18next` / `react-i18next` | 26.7.1 / 17.1.12 | Current packages; peer metadata includes React and TypeScript 7. |

Package versions were checked through the official npm registry's `latest` package metadata on the date above. Registry links: [React](https://registry.npmjs.org/react/latest), [React DOM](https://registry.npmjs.org/react-dom/latest), [TypeScript](https://registry.npmjs.org/typescript/latest), [Vite](https://registry.npmjs.org/vite/latest), [Vite React plugin](https://registry.npmjs.org/%40vitejs%2Fplugin-react/latest), [Tailwind CSS](https://registry.npmjs.org/tailwindcss/latest), [Tailwind Vite adapter](https://registry.npmjs.org/%40tailwindcss%2Fvite/latest), [React Router](https://registry.npmjs.org/react-router/latest), [TanStack Query](https://registry.npmjs.org/%40tanstack%2Freact-query/latest), [React Hook Form](https://registry.npmjs.org/react-hook-form/latest), [Hook Form resolvers](https://registry.npmjs.org/%40hookform%2Fresolvers/latest), [Zod](https://registry.npmjs.org/zod/latest), [Axios](https://registry.npmjs.org/axios/latest), [Motion](https://registry.npmjs.org/motion/latest), [Lucide React](https://registry.npmjs.org/lucide-react/latest), [i18next](https://registry.npmjs.org/i18next/latest) and [react-i18next](https://registry.npmjs.org/react-i18next/latest).

## Version policy

- Treat this file as a Phase 0 compatibility snapshot, not a dependency lock or proof of runtime compatibility.
- Recheck releases and security advisories before Phase 1 initializes the backend and frontend.
- Commit reproducible Maven Wrapper and npm lockfile versions during Phase 1; do not use floating `latest` dependencies in project manifests.
- Keep Spring-managed libraries aligned to the Spring Boot BOM unless a documented compatibility need justifies an override.
- Revisit versions through reviewed dependency updates; record meaningful stack changes in `docs/DECISIONS.md`.

# TDOP Roadmap and Kanban

**Last updated:** 2026-11-08. Phase 0 is complete by owner direction. Phase 1 was owner-approved and merged to `develop` as `50766b3` with both CI jobs green but remains **incomplete in Testing**: the owner declined to waive the unperformed Docker check (see D-015). Phase 2 was authorized while Phase 1 stays incomplete (see D-016), was owner-approved and merged to `develop` as `3e0cd70`, and is **Done**. Phase 3 was started by owner instruction ("push phase 2 and start 3") and is **In Progress**. The live GitHub Kanban board has been provisioned and linked to this repository.

## Kanban workflow

The required workflow is **Backlog → To Do → In Progress → Code Review → Testing → Done**.

| Status | Definition |
| --- | --- |
| Backlog | Captured idea or requirement; not yet sufficiently scoped or scheduled. |
| To Do | Authorized and ready: owner, dependencies, scope and objective acceptance criteria are known. |
| In Progress | Work is actively underway on the approved branch/phase. |
| Code Review | Implementation is complete and a reviewable pull request is open. |
| Testing | Review is approved; automated and required manual checks are being completed. |
| Done | Acceptance criteria are verified, documentation is current, required checks pass, and changes are merged through the approved process. |

An item must not skip required states or be marked Done because work was merely written. Record failed checks and blockers in the item. The owner-authorized live GitHub Project exists as [TDOP — Development Roadmap](https://github.com/users/felix202422/projects/3), is linked to this repository and uses the six statuses above; it is the authoritative view of phase state, and the table below mirrors it.

## Phase status

| Phase | Status | Gate / outcome |
| --- | --- | --- |
| 0 — Foundation and planning | **Done and owner-accepted** | Requirements, MVP, architecture, versions, policies and roadmap published to `main` and `develop`; its board card is Done. |
| 1 — Repository and development environment | **Testing (incomplete)** | Implemented in PR #1, owner-approved and merged to `develop` as `50766b3`; CI passed (backend verification + frontend quality checks). Required manual check not performed: Docker is not installed on the implementation host, so `docker compose up` startup is unverified. The owner declined to waive this (D-015), so the phase is not Done. Its outstanding check does not block Phase 2, which the owner authorized separately (D-016). |
| 2 — Database architecture | **Done** | Owner-approved and merged to `develop` as `3e0cd70` (PR #23) on 2026-11-07; board card Done with evidence. Local `./mvnw -B verify` and CI run [`42391882890`](https://github.com/felix202422/Tanzanian_Opportunities/actions/runs/42391882890) both green: 23 tests, 0 failures, formatter clean. Acceptance: migrations apply from an empty database (FlywayMigrationTest, V1–V4, 21 tables), rollback/forward policy documented in `docs/DATA_MODEL.md` §6.3, persistence tests pass, no credentials in the repository (D-017). Phase 1 remains incomplete in Testing (D-015); that blocker is untouched. |
| 3 — Backend core architecture | **In Progress** | Started 2026-11-07 by owner instruction after Phase 2 merged. Scope: application layers, DTO contracts under `/api/v1`, request validation, the error contract, structured logging with correlation ids, API versioning, generated OpenAPI and health endpoints. Acceptance: OpenAPI generated, error contract documented, health endpoints respond, tests pass. |
| 4 — Authentication and authorization | Backlog | Registration, login, password hashing, tokens, revocation, roles and security tests. |
| 5 — User and profile management | Backlog | Seeker profile, education, skills, experience, preferences and safe media upload. |
| 6 — Organization management | Backlog | Organization membership/profile, verification submission and admin workflow. |
| 7 — Opportunity management | Backlog | Extensible categories, organization authoring, moderation, publication and expiration. |
| 8 — Discovery and search | Backlog | Search/filter/sort/pagination, saved items, comparison and related results. |
| 9 — Application management | Backlog | Internal profile/form applications, external destinations, status history and ownership. |
| 10 — Notifications and communication | Backlog | Event-based in-app notifications, preferences and optional configured email. |
| 11 — Administration and moderation | Backlog | Protected moderation, reports, analytics, audit and platform management. |
| 12 — Design system and public website | Backlog | Responsive public experience, forms, discovery and details pages. |
| 13 — Role-based dashboards | Backlog | Seeker, organization and admin workspaces backed by real APIs. |
| 14 — Localization and accessibility | Backlog | Complete English/Swahili UI, keyboard and accessibility review. |
| 15 — Intelligent discovery | Backlog / post-MVP | Explainable profile/opportunity matching and advanced integrations after core release. |
| 16 — Quality assurance | Backlog | Unit, integration, security, component, end-to-end, performance and accessibility coverage. |
| 17 — Production readiness | Backlog | Production configuration, HTTPS, backups, monitoring, CI/CD and rollback. |
| 18 — Handover | Backlog | Final technical review, release notes, known limits and verified handover. |

## Repository publication

- The local repository was initialized on `main`; the owner-approved Phase 0 baseline was committed and pushed to GitHub.
- Both `main` and `develop` were verified on the remote at the same initial Phase 0 commit. The local `main` branch tracks `origin/main`; `develop` tracks `origin/develop`.
- The initial direct push to `main` was an explicit empty-repository bootstrap exception. Future work follows the pull-request workflow in [CONTRIBUTING.md](../CONTRIBUTING.md).
- A live GitHub Projects board exists at [TDOP — Development Roadmap](https://github.com/users/felix202422/projects/3), linked to this repository, with one card per roadmap phase. `gh auth status` still reports no interactive login for this session; board provisioning was performed with the repository owner's stored Git credentials via the GitHub API, and the resulting project, Status field options and cards were verified by reading them back from the API.
- Phase 1 was started with owner approval; Phase 2 was authorized by the owner on 2026-11-04 (D-016) while Phase 1 remains incomplete in Testing. Later phases remain gated.

## Live Kanban board provisioning

- **Project:** [TDOP — Development Roadmap](https://github.com/users/felix202422/projects/3), created on 2026-11-04 with the repository owner's stored Git credentials through the GitHub GraphQL/REST API and linked to `felix202422/Tanzanian_Opportunities`.
- **Workflow statuses:** Backlog → To Do → In Progress → Code Review → Testing → Done, configured as the single-select `Status` field's options in that order.
- **Cards:** one issue per roadmap phase, issues #2–#20 (Phases 0–18), each carrying scope, acceptance criteria, the workflow rule and a pointer to this document.
- **Verified state:** read back from the API on 2026-11-04 (19 items: Phase 0 Done, Phase 1 Testing, Phase 2 In Progress, Phases 3–18 Backlog) and updated through the workflow since: Phase 2 advanced In Progress → Code Review → Testing → Done with evidence comments on [issue #4](https://github.com/felix202422/Tanzanian_Opportunities/issues/4), and Phase 3 advanced Backlog → To Do → In Progress on [issue #5](https://github.com/felix202422/Tanzanian_Opportunities/issues/5) with its start comment. Phase 0 stays Done, Phase 1 stays Testing (D-015), Phases 4–18 stay Backlog.
- **Reproducibility:** the provisioning used temporary scripts outside the repository; the board itself is the persistent artifact. Re-running provisioning would create duplicates, so treat the live project as the source of truth.

## Phase 0 issue plan and acceptance

Phase 0 consisted of local planning work items:

1. **Inspect repository and access** — record local workspace state and verify remote access read-only.
2. **Establish product boundary** — define audiences, 12 categories, MVP and deferred capabilities.
3. **Record architecture and technology choices** — document modular architecture, data/security strategy and source-verified candidates in `VERSIONS.md` and `docs/DECISIONS.md`.
4. **Prepare project foundation documents** — add required Phase 0 files and this Kanban/roadmap definition.
5. **Publish approved Phase 0 baseline** — owner-authorized initial Git bootstrap and publication to `main`/`develop`; do not create a live board or issues.

Phase 0 acceptance requires these documents to agree, exact version claims to have sources/date, MVP boundaries to be explicit, and no claim of unimplemented functionality or fabricated test results. The owner has accepted Phase 0 complete, and the separately approved live board has since been provisioned and verified. Phase 1 is owner-approved and merged with CI green but explicitly incomplete in Testing (D-015); the owner authorized starting Phase 2 without waiving that outstanding check (D-016).

## Phase gate rules

- Work on one phase at a time; do not start a later phase before acceptance and owner approval.
- Each phase defines scope, deliverables, objective acceptance criteria, dependencies and verification.
- A phase is not Done until changes are inspected, relevant checks pass, documentation is updated and evidence is reported.
- Keep remote GitHub changes (branch/board/issue creation, commits and pushes) separate from local work and obtain authorization before making them.
- Rebaseline versions from official release sources when Phase 1 initializes dependencies; do not blindly reuse old “latest” values.

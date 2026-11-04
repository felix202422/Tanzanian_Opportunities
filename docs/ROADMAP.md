# TDOP Roadmap and Kanban

**Last updated:** 2026-11-04. Phase 0 planning artifacts have been prepared and validated locally. The project owner must approve Phase 0 before Phase 1 starts.

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

An item must not skip required states or be marked Done because work was merely written. Record failed checks and blockers in the item. The owner has authorized a live GitHub Project, but it has not yet been created because this session is unauthenticated; the local workflow remains defined here until provisioning succeeds.

## Phase status

| Phase | Status | Gate / outcome |
| --- | --- | --- |
| 0 — Foundation and planning | **Done and published; Phase 1 awaits approval** | Requirements, MVP, architecture, version candidates, policies, decisions and roadmap documented; no application code. Baseline is on `main` and `develop`. |
| 1 — Repository and development environment | Backlog / approval-gated | Monorepo, wrappers, frontend/backend scaffolds, linting, environment templates and local services. |
| 2 — Database architecture | Backlog | ER design, PostgreSQL connectivity, Flyway migrations, seed profile and integrity tests. |
| 3 — Backend core architecture | Backlog | Layers, DTOs, validation, errors, logging, API versioning, OpenAPI and health endpoints. |
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
- No live GitHub Projects board or issue cards have been created. The owner has now approved board setup, but `gh auth status` reports no authenticated GitHub account and the browser redirects to sign-in. Provisioning is blocked until an authenticated session is available.
- Phase 1 remains blocked until separate owner approval.

## Live Kanban board provisioning

- **Requested project:** `TDOP — Development Roadmap`, linked to this repository.
- **Required workflow statuses:** Backlog → To Do → In Progress → Code Review → Testing → Done.
- **Planned initial cards:** one phase issue per roadmap phase, with scope, dependencies and verifiable acceptance criteria; Phase 0 Done, Phase 1 awaiting owner approval, later phases Backlog.
- **Current status:** owner-approved, not provisioned. GitHub Project creation and issue seeding require authentication unavailable in this session. Do not report the board as live until the remote Project and its fields/cards are verified.

## Phase 0 issue plan and acceptance

Phase 0 consisted of local planning work items:

1. **Inspect repository and access** — record local workspace state and verify remote access read-only.
2. **Establish product boundary** — define audiences, 12 categories, MVP and deferred capabilities.
3. **Record architecture and technology choices** — document modular architecture, data/security strategy and source-verified candidates in `VERSIONS.md` and `docs/DECISIONS.md`.
4. **Prepare project foundation documents** — add required Phase 0 files and this Kanban/roadmap definition.
5. **Publish approved Phase 0 baseline** — owner-authorized initial Git bootstrap and publication to `main`/`develop`; do not create a live board or issues.

Phase 0 acceptance requires these documents to agree, exact version claims to have sources/date, MVP boundaries to be explicit, and no claim of unimplemented functionality or fabricated test results. Publication is a separate owner-authorized task and its actual state is reported in the completion report. Phase 1 remains blocked until explicit owner approval.

## Phase gate rules

- Work on one phase at a time; do not start a later phase before acceptance and owner approval.
- Each phase defines scope, deliverables, objective acceptance criteria, dependencies and verification.
- A phase is not Done until changes are inspected, relevant checks pass, documentation is updated and evidence is reported.
- Keep remote GitHub changes (branch/board/issue creation, commits and pushes) separate from local work and obtain authorization before making them.
- Rebaseline versions from official release sources when Phase 1 initializes dependencies; do not blindly reuse old “latest” values.

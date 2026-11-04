# Contributing to TDOP

Thank you for helping improve the Tanzania Digital Opportunity Platform. TDOP is currently in Phase 0: architecture and policy documentation are being prepared, and application code has not yet been initialized.

## Before contributing

- Read the [Code of Conduct](CODE_OF_CONDUCT.md) and [Security Policy](SECURITY.md).
- Check the [roadmap](docs/ROADMAP.md) and agree on the issue’s acceptance criteria before starting work.
- Do not submit real personal data, production records, credentials or private verification documents.
- Do not begin Phase 1 or later work until the project owner approves the preceding phase.

## Planned workflow

The documented branch model is:

- `main`: reviewed, release-ready state.
- `develop`: integration branch for approved work.
- `feature/<short-name>`: feature work, branching from `develop`.
- `fix/<short-name>`: bug fixes, branching from `develop` unless a release fix is explicitly approved.
- `docs/<short-name>`: documentation work, branching from `develop`.

Open a pull request into `develop`, describe the change and its acceptance criteria, link the issue, and include tests and documentation updates where relevant. Releases are reviewed and merged from `develop` to `main`. The owner explicitly approved the one-time initial Phase 0 bootstrap directly to `main`; this is not a pattern for subsequent work. Never push directly to `main` outside an explicitly approved empty-repository bootstrap.

The repository was empty at initial inspection. The Phase 0 baseline has been published to `main` and `develop`. The owner has approved creating a live GitHub Projects board, but setup is pending GitHub authentication; no board or issue cards exist yet. See [the roadmap](docs/ROADMAP.md) for status.

## Kanban workflow

Project work follows **Backlog → To Do → In Progress → Code Review → Testing → Done**. A card moves forward only when its entry criteria are satisfied:

| Column | Entry/exit expectation |
| --- | --- |
| Backlog | Idea or requirement captured; not yet ready to schedule. |
| To Do | Scope, owner, dependencies and verifiable acceptance criteria are clear. |
| In Progress | Work is authorized, assigned and actively being implemented. |
| Code Review | Implementation is complete, changes are reviewable and the PR is open. |
| Testing | Review is approved; required automated and manual checks are running. |
| Done | Acceptance criteria are verified, required docs updated, and changes merged through the approved workflow. |

No issue or phase is Done based only on implementation claims. Record blockers and failed checks rather than moving work forward.

## Commit messages

Use the Conventional Commits style, for example:

- `feat(auth): add secure registration`
- `fix(applications): reject duplicate submissions`
- `docs(architecture): record token lifecycle decision`
- `test(security): cover organization ownership checks`

Keep commits focused. Do not commit generated build output, local secrets, `.env` files, user uploads or test data derived from real people.

## Engineering expectations

- Match the approved architecture and keep changes within the authorized phase.
- Use backend authorization and validation; frontend checks are not security boundaries.
- Add or update tests for behavior and access-control changes.
- Keep API communication DTO-based; do not expose persistence entities.
- Preserve accessible, mobile-first behavior and English/Swahili localization requirements.
- Document non-obvious tradeoffs in `docs/DECISIONS.md`.
- Run the relevant tests and builds before requesting review, and report the commands and actual results.

## Development setup

The application is not scaffolded yet, so setup and test commands are not available. Phase 1 will add verified, cross-platform instructions to the README and development guide.

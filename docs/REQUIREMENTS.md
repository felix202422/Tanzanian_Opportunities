# TDOP Requirements

**Status:** Product and MVP requirements baseline for Phase 0; implementation is not yet started. Later phases may refine these requirements through documented decisions and owner approval.

## Scope

TDOP serves opportunity seekers, opportunity-providing organizations and administrators. It supports English and Swahili, focuses first on Tanzania, and is designed for future expansion. The initial release is limited to core trusted discovery and application workflows; see the MVP boundary below.

## Roles and authorization

### Seeker

- Register, authenticate, manage account settings and choose a preferred language.
- Maintain personal, education, skill, experience and interest information.
- Discover, search, filter, sort, save and compare opportunities.
- Apply internally when an opportunity offers TDOP applications; track status and withdraw where allowed.
- Follow the official application destination for external opportunities; TDOP must not claim to have submitted those applications.
- Receive and manage notifications; report suspicious opportunities.

### Organization

- Register an account and maintain its organization profile.
- Submit verification information/documents and view verification outcomes.
- Create and manage opportunities only within its organization and permissions.
- Publish to the public catalogue only after backend-enforced verification and moderation rules pass.
- Review eligible applications, record supported status transitions and view actual organization analytics.

### Administrator

- Manage users, organizations, organization verifications, opportunities, reports, categories, announcements and platform settings according to least privilege.
- Approve, reject, suspend or restore records through explicit workflows.
- View actual platform analytics and audit history.
- Perform sensitive operations only through backend-protected endpoints; frontend role checks are never authorization controls.

A future super-administrator must be addable through a server-side authority model without granting elevated access by default.

## Opportunity model requirements

Support the 12 types listed in [Project Overview](PROJECT_OVERVIEW.md) and allow categories to be introduced as data/configuration rather than redesigning the application. Every opportunity must support common fields for title, description, category, provider organization, location, application mode, source/provenance, verification state, publication/moderation state and publication timestamps. Opening/closing dates, eligibility, qualifications, required skills and application instructions must be representable, but may be optional or conditionally required according to the opportunity and its category. External mode requires a validated official application destination; internal mode requires a valid TDOP application form. Category-specific fields must follow a versioned server-side schema and must not become unvalidated arbitrary user data.

Core discovery must expose only published, eligible-to-display opportunities. Expired, suspended, rejected and draft content must not leak through public APIs. Dates must have well-defined timezone handling and deadlines must be visible.

## Application requirements

- Support `INTERNAL` and `EXTERNAL` modes per opportunity.
- External mode links to the provider’s official application destination and clearly leaves submission/tracking to that destination.
- Internal mode creates one application per seeker/opportunity, using profile-backed data and structured form responses. Do not accept application-document uploads in the initial release.
- Organization members may access only applications for opportunities they are permitted to manage; seekers may access only their own applications.
- Enforce valid transitions among `SUBMITTED`, `UNDER_REVIEW`, `SHORTLISTED`, `INTERVIEW`, `ACCEPTED`, `REJECTED` and `WITHDRAWN`; preserve history for relevant transitions.
- A failed or incomplete submission must not be presented as submitted.

## Organization trust and moderation

- Model organization verification as a traceable workflow with submitted, pending, verified, rejected and suspended states (final vocabulary to be confirmed in Phase 6).
- Do not allow unverified organizations to publish publicly.
- Opportunity moderation and publication status must be distinct enough to prevent approval from bypassing verification or expiration rules.
- Reports must have ownership, status and review history; sensitive administrative actions must be audited.

## Search, saved items and notifications

- Combine keyword, category, location, opportunity-type, deadline and organization filters; support sorting and pagination.
- Allow seekers to save/unsave items and compare selected opportunities without exposing another seeker’s saved list.
- Generate basic in-app notifications from actual events (verification outcomes, internal application changes and configured deadline reminders). Respect user preferences and ownership.
- SMS, WhatsApp, push and external opportunity feeds are outside the initial release.

## MVP / initial release acceptance boundary

The MVP is a useful end-to-end service, not a static landing page. It includes:

1. Seeker and organization account workflows, profile management and backend authorization.
2. Organization verification and server-enforced eligibility to submit/publish opportunities.
3. Admin moderation and reporting workflows with audit history.
4. Public opportunity details, search, filters, sorting, pagination and saved items.
5. Internal applications with profile/form data only, duplicate prevention and status tracking, plus truthful external redirects.
6. Core in-app notifications, responsive dashboards, English/Swahili interfaces and accessibility review.
7. Real database-backed statistics only; no seeded/fabricated production analytics.

Advanced recommendations, integrations, applicant file uploads, external notification channels, advanced analytics and international-specific legal/deployment needs are deferred. Development seed data is separate from production data.

## Non-functional requirements

- **Security:** Spring Security, adaptive password hashing, short-lived access JWTs, rotating/revocable refresh tokens, resource ownership checks, validation, rate limiting, secure headers, allowlisted CORS, safe upload controls and audit logging.
- **Privacy:** data minimization, explicit purposes, access restriction, protected document storage, retention/deletion rules, no sensitive data in logs and legal/privacy review before production use.
- **Reliability:** transaction boundaries for multi-record writes, health checks, structured logs, backup/restore and migration safety before production.
- **Performance:** paginate large lists, index measured query paths, avoid N+1 access and measure search/dashboard behavior with representative non-personal data.
- **Accessibility:** target WCAG 2.2 AA where applicable, semantic HTML, keyboard access, visible focus, labels, accessible errors and sufficient contrast.
- **Compatibility:** mobile-first support for phones, tablets and desktop; supported browser baseline to be recorded during Phase 12.
- **Maintainability:** layered/modular code, DTO APIs, schema-managed migrations, version-controlled documentation and automated quality checks.
- **Localization:** English and Swahili UI strings, locale-aware date/number formatting and persistent language preference; user-submitted opportunity content may be in the provider’s language and must be identified honestly.

## Quality and acceptance

Each feature must have verifiable acceptance criteria, automated tests appropriate to its risk and phase, error/loading/empty states in the UI, updated docs and demonstrated ownership/security behavior. Platform statistics and project reports must state their data source. No feature is considered complete solely because it compiles.

## Out of scope for Phase 0

No backend/frontend scaffold, API implementation, schema or migrations, dependencies, Docker services, live GitHub board/issues or Phase 1 implementation are part of Phase 0. The owner authorized, and the project has completed, a local Git bootstrap and publication of only the approved Phase 0 artifacts to `main` and `develop`; this does not authorize other remote changes.

# TDOP — Master Architecture Specification

| | |
|---|---|
| **Document** | `Docs/TDOP_MASTER_SPEC.md` |
| **Purpose** | Architecture bridge between product requirements and implementation |
| **Status** | Active |

---

## 1. TDOP Purpose

TDOP is a trusted opportunity discovery and progress platform for Tanzania. This document specifies the architectural contracts, domain boundaries, implementation status, and target architecture.

**Source code is the implementation evidence.** This document never claims functionality that the codebase does not support.

---

## 2. Product Vision

> A trusted digital layer between people and opportunities.

TDOP connects seekers with providers through structured opportunity management, evidence-based trust, intelligent discovery, and actionable outcomes.

---

## 3. Product Principles

1. **Trust over speed** — verification requires evidence
2. **Discovery over listing** — match relevant opportunities, don't just display them
3. **Outcome over application** — track progress, not just submissions
4. **Evidence over assumption** — code is the source of truth
5. **Reuse over rebuild** — extend existing functionality
6. **Domain clarity** — clear boundaries between identity, trust, opportunity, action, governance

---

## 4. Ecosystem Model

```text
OPPORTUNITY SEEKERS
        ↓
    DISCOVER
        ↓
    UNDERSTAND
        ↓
    APPLY / ACT
        ↓
    TRACK
        ↓
    OUTCOME
        ↑
OPPORTUNITY PROVIDERS
        ↓
    VERIFY
        ↓
    PUBLISH
        ↓
    MANAGE
```

---

## 5. User and Organization Roles

### 5.1 Role Definitions

| Role | Responsibility | Scope |
|---|---|---|
| `SEEKER` | Browse, search, save, apply, track | Own profile, own applications |
| `ORGANIZATION` | Create profile, post opportunities, manage applications | Own organization |
| `ORGANIZATION_ADMIN` | Full org management, team, settings, publishing | Own organization |
| `ORGANIZATION_MEMBER` | Limited org access based on assignment | Assigned resources only |
| `VERIFICATION_OFFICER` | Review org verification requests | Verification queue |
| `MODERATOR` | Review opportunities for quality/policy | Moderation queue |
| `ADMIN` | Platform operations and management | Platform-wide |
| `SUPER_ADMIN` | Platform governance and control | Platform-wide |

### 5.2 Role Boundary: ADMIN vs SUPER_ADMIN

| ADMIN (Operations) | SUPER_ADMIN (Governance) |
|---|---|
| Operate, Manage, Review, Resolve, Monitor | Govern, Configure, Secure, Control, Audit |
| User management | Roles & Permissions |
| Organization oversight | Access governance |
| Moderation, reports | Security center |
| Analytics, configuration | Taxonomy governance |
| | Platform configuration |
| | Feature controls |
| | Integrations, background jobs |

---

## 6. Core Platform Engines

### 6.1 Engine Implementation Status

| Engine | Status | Evidence |
|---|---|---|
| **Discovery Engine** | Partial | `OpportunityController` search/filter, `BrowseOpportunitiesPage`. SQL LIKE only. |
| **Trust Engine** | Implemented | `VerificationOfficerService`, `ModerationService`, `ReportInvestigationService`, `EscalationService`, `AppealService` |
| **Access Engine** | Partial | `SecurityConfig` URL-level rules, `RbacService` exists but unused by controllers |
| **Opportunity Engine** | Implemented | `OpportunityLifecycleService` — full state machine with status history |
| **Action/Application Engine** | Implemented | `ApplicationService` — CRUD, status tracking, history, withdrawal |
| **Outcome Engine** | Planned | No implementation. Target: track post-application outcomes. |
| **Intelligence Engine** | Partial | `OpportunityMatchingService` — basic rule-based scoring (skill +10, interest +5, education +3) |
| **Governance Engine** | Partial | `RbacService` exists with full CRUD but not wired to controllers |
| **Event & Job Layer** | Partial | `DeadlineEngineService` — `@Scheduled(fixedRate=3600000)` hourly. No job queue. |
| **Data Quality Layer** | Partial | `AntiFraudService` — duplicate title, shortened URL, unverified org, repeated reports |
| **Observability** | Partial | `AnalyticsService` — basic counts. No monitoring, no health checks beyond `/public/health`. |
| **Distribution Engine** | Partial | `NotificationService` (in-app), `EmailService` (SMTP). No push, no SMS, no WhatsApp. |
| **Subscription Engine** | Planned | No implementation. Target: user preference-based opportunity delivery. |
| **Communication Center** | Planned | No implementation. Target: multi-channel notification routing. |
| **Integration Hub** | Planned | No implementation. Target: external API connections. |
| **Source/Ingestion Engine** | Planned | No implementation. Target: opportunity import from external sources. |
| **Freshness Engine** | Planned | No implementation. Target: stale opportunity detection. |
| **Deadline Intelligence** | Implemented | `DeadlineEngineService` — expire, closing_soon, reminders. Configurable via `platform_config`. |
| **Watchlist** | Partial | `SavedOpportunityService` — save/unsave. No alert notifications. |
| **Personalized Feed** | Partial | `OpportunityMatchingService` — basic scoring. No collaborative filtering. |
| **Opportunity Comparison** | Implemented | `CompareController`, `ComparePage` |

---

## 7. Opportunity Architecture

### 7.1 Current Implementation

**Entity:** `Opportunity` (22 fields)
**Lifecycle states:** `DRAFT`, `SUBMITTED`, `VERIFIED`, `APPROVED`, `PUBLISHED`, `CLOSING_SOON`, `EXPIRED`, `SUSPENDED`, `ARCHIVED`, `REJECTED`, `UNDER_REVIEW`
**Status history:** `OpportunityStatusHistory` entity — records every transition

**Service:** `OpportunityLifecycleService`
- `createDraft`, `submitForReview`, `verifyOpportunity`, `moderateOpportunity`
- `publish`, `suspend`, `archive`, `restore`
- `markExpired`, `markClosingSoon` (called by `DeadlineEngineService`)

### 7.2 Current Data Model

| Field | Type | Status |
|---|---|---|
| title | String | Implemented |
| description | TEXT | Implemented |
| requirements | TEXT | Implemented |
| benefits | TEXT | Implemented |
| location | String | Implemented |
| type | OpportunityType enum | Implemented |
| category | String | Implemented |
| salaryRange | String | Implemented |
| tags | String | Implemented |
| deadline | LocalDateTime | Implemented |
| status | OpportunityStatus enum | Implemented |
| sourceUrl | String | Implemented |
| applicationUrl | String | Implemented |
| workMode | String | Implemented |
| educationLevel | String | Implemented |
| experienceLevel | String | Implemented |
| fundingInfo | String | Implemented |
| eligibility | TEXT | Implemented |
| requiredDocuments | TEXT | Implemented |
| verified | boolean | Implemented |
| moderated | boolean | Implemented |
| riskScore | String | Implemented |
| viewCount | Long | Implemented |
| saveCount | Long | Implemented |
| applicationCount | Long | Implemented |

### 7.3 Target Data Model (Not Yet Implemented)

| Field | Purpose |
|---|---|
| sourceType | MANUAL, IMPORTED, API, RSS, PARTNER |
| sourceName | Original publisher name |
| sourceReferenceId | External ID from source |
| sourceVerified | Whether source verified |
| sourceLastChecked | When source last verified |
| sourceCredibility | UNVERIFIED, BASIC, VERIFIED, TRUSTED |
| structuredEligibility | JSON rules for eligibility evaluation |

---

## 8. Opportunity Type Contract

### 8.1 Contract Structure

```
Opportunity Type
      ↓
Required Fields → title, description, deadline, category, location
      ↓
Optional Fields → requirements, benefits, salaryRange, eligibility
      ↓
Eligibility Rules → education, skills, experience, location, age
      ↓
Allowed Actions → apply, register, submit, prepare
      ↓
Lifecycle → standard lifecycle + type-specific states
      ↓
Required Documents → CV, certificate, proposal, bid
      ↓
Deadline Rules → standard + type-specific (e.g., tender submission window)
      ↓
Trust Requirements → org verification level, opportunity verification
      ↓
Action Mechanism → internal apply, external URL, email, registration form
      ↓
Notification Events → type-specific notifications
      ↓
Outcome States → accepted, rejected, shortlisted, awarded, completed
      ↓
Analytics Events → type-specific tracking
```

### 8.2 Current Implementation

The `OpportunityType` enum currently supports: `FULL_TIME`, `PART_TIME`, `INTERNSHIP`, `CONTRACT`, `VOLUNTEER`, `SCHOLARSHIP`.

The architecture is extensible — new types can be added to the enum without structural changes. However, type-specific field validation, eligibility rules, and action mechanisms are not yet implemented.

---

## 9. Action Engine

### 9.1 Current Implementation

The `Application` entity and `ApplicationService` implement a single action type: **Apply**.

```
User → Apply → Application Created → Status Tracking → Outcome
```

### 9.2 Target Architecture

TDOP should support type-specific actions:

| Type | Actions |
|---|---|
| JOB | Apply → Track → Outcome |
| SCHOLARSHIP | Apply → Track → Decision |
| TENDER | Review → Prepare Bid → Submit → Track → Award |
| TRAINING | Register → Attend → Complete |
| EVENT | Register → Attend |
| RESEARCH | Prepare Proposal → Submit → Track → Outcome |

The `Action Engine` concept replaces the `Application Engine` to accommodate non-application action types.

---

## 10. Outcome Model

### 10.1 Current Status: **PLANNED**

No outcome tracking exists. After an application is accepted/rejected, the trail ends.

### 10.2 Target Architecture

```
Application Accepted
      ↓
Outcome Tracking
├── Onboarding Started
├── Onboarding Completed
├── Employment Started
├── Employment Ended
├── Training Completed
├── Scholarship Awarded
├── Tender Awarded
└── Other Outcome
```

Outcomes should be:
- Voluntary (user reports)
- Verifiable (where possible)
- Aggregated (platform-level success metrics)
- Privacy-respecting

---

## 11. Trust Engine

### 11.1 Current Implementation

| Component | Service | Status |
|---|---|---|
| Organization Verification | `VerificationOfficerService` | **Implemented** |
| Opportunity Moderation | `ModerationService` | **Implemented** |
| Report Investigation | `ReportInvestigationService` | **Implemented** |
| Escalation | `EscalationService` | **Implemented** |
| Appeals | `AppealService` | **Implemented** |
| Anti-Fraud Detection | `AntiFraudService` | **Implemented** — basic |
| Risk Signals | `RiskSignal` entity | **Implemented** |

### 11.2 Verification Flow

```
Organization → Submit Evidence → Verification Queue → Review
→ Request Information → Approve/Reject → Re-verify → Suspend/Expire
```

### 11.3 Moderation Flow

```
Opportunity → Moderation Queue → Review
→ Approve/Reject/Suspend/Archive/Request Information
```

### 11.4 Report Flow

```
Report → Queue → Assign → Investigate → Resolve/Dismiss → Audit
```

### 11.5 Anti-Fraud Signals

| Signal | Implementation |
|---|---|
| Duplicate opportunity titles | `findByTitleContainingIgnoreCase` — check count |
| Shortened URLs | Check for bit.ly, tinyurl, t.co |
| Unverified organization | Check `createdBy.isVerified()` |
| Repeated reports | Count reports ≥ 3 → HIGH risk |

### 11.6 Missing Trust Capabilities

- Source credibility tracking
- Stale opportunity detection
- Fuzzy duplicate detection
- Behavioral anomaly detection
- Reputation scoring
- External source verification

---

## 12. Discovery Architecture

### 12.1 Current Implementation

| Component | Implementation |
|---|---|
| Search | SQL `LIKE` queries on title, description, tags, category |
| Filter | By type, category, location, status |
| Sort | By deadline, created date |
| Pagination | Page/limit parameters |
| Saved | `SavedOpportunityService` — save/unsave |
| Comparison | `CompareController` — compare by IDs |
| Recommendations | `OpportunityMatchingService` — basic scoring |

### 12.2 Matching Algorithm (Current)

```
Score = Skill Match (+10 per match)
      + Interest Match (+5)
      + Education Match (+3)
      + Location Presence (+1)
```

### 12.3 Target Discovery Stack

```
Elasticsearch / pg_trgm
      ↓
Full-text search with ranking
      ↓
Faceted filters
      ↓
Personalized ranking
      ↓
Recommendations with explanations
```

---

## 13. Notifications

### 13.1 Current Implementation

**In-App:** `NotificationService` — create, list, mark read
**Email:** `EmailService` — verification email, application notification (via JavaMailSender SMTP)

### 13.2 Notification Events (Current)

| Event | Trigger | Channel |
|---|---|---|
| Account verification | Registration | Email |
| New application | Application created | Email (to org) |
| Application status change | Status update | In-app |
| Deadline reminder | DeadlineEngineService | DB record (email not sent) |

### 13.3 Target Notification Events

| Event | Channel |
|---|---|
| New opportunity matching interests | In-app, Email, Push |
| Deadline approaching | In-app, Email, SMS |
| Opportunity updated | In-app |
| Application submitted | In-app, Email |
| Application status changed | In-app, Email |
| Information requested | In-app, Email |
| Decision available | In-app, Email |
| Verification decision | In-app, Email |
| Trust/report update | In-app |
| Security notification | In-app, Email |
| Platform announcement | In-app |

---

## 14. Subscriptions

### 14.1 Current Status: **PLANNED**

No subscription system exists. Users cannot configure what types of opportunities they want to receive.

### 14.2 Target Architecture

```text
User Subscription
├── Types: Jobs, Internships, Scholarships, Tenders, Training
├── Categories: ICT, Business, etc.
├── Location: Tanzania, Remote, International
├── Frequency: Instant, Daily Digest, Weekly
└── Channels: In-App, Email, SMS, WhatsApp, Push
```

---

## 15. Distribution Channels

### 15.1 Current Implementation

| Channel | Status |
|---|---|
| In-App notifications | **Implemented** |
| Email (SMTP) | **Partial** — verification + application only |
| SMS | **Planned** |
| WhatsApp | **Planned** |
| Push (Web) | **Planned** |
| Push (Mobile) | **Future** |

### 15.2 WhatsApp Architecture (Target)

WhatsApp is a **distribution channel**, not a separate platform:

```
TDOP EVENT
     ↓
NOTIFICATION / DISTRIBUTION ENGINE
     ↓
CHANNEL ROUTER
├── In-App
├── Email
├── SMS
├── WhatsApp
└── Push
```

All distribution must respect: consent, subscriptions, notification preferences, privacy, permissions, delivery failures, audit requirements.

---

## 16. Organization Workspace

### 16.1 Current Implementation

| Capability | Status |
|---|---|
| Organization profile CRUD | **Implemented** |
| Team management (invite, remove, roles) | **Implemented** |
| Opportunity creation (draft → publish) | **Implemented** |
| Application management | **Implemented** |
| Verification submission | **Implemented** |
| Organization analytics | **Partial** — basic counts |

### 16.2 Team Roles

| Role | Opportunities | Applications | Team | Settings |
|---|---|---|---|---|
| ORG_ADMIN | Full | Full | Full | Full |
| ORG_MEMBER | Assigned only | Assigned only | View | View |

---

## 17. Moderator / Verification Workspace

### 17.1 Current Implementation

The Trust workspace (`/trust/*`) provides:

| Page | Purpose |
|---|---|
| TrustAttentionPage | Attention items (pending verifications, moderation, reports, fraud) |
| TrustWorkQueuePage | Combined work queue |
| TrustVerificationReviewPage | Review org verification requests |
| TrustOpportunityReviewPage | Review opportunities for moderation |
| TrustReportReviewPage | Investigate reports |
| TrustActivityPage | Activity log |
| TrustOverviewPage | Overview statistics |
| TrustEscalationPage | Manage escalations |
| TrustAppealPage | Manage appeals |

### 17.2 Required Roles

`VERIFICATION_OFFICER`, `MODERATOR`, `ADMIN`, `SUPER_ADMIN`

---

## 18. Admin Workspace

### 18.1 Current Implementation

| Page | Purpose |
|---|---|
| AdminDashboardPage | Overview statistics |
| UserManagementPage | User list, suspend, role changes |
| AdminOrganizationsPage | Organization list, stats |
| OpportunityModerationPage | Moderation queue |
| ModerationPage | Moderation actions |
| VerificationOfficerPage | Verification queue |
| ReportsPage | Report investigation |
| AuditLogPage | Audit log viewer |
| AnalyticsPage | Platform analytics |
| PlatformConfigPage | Configuration management |

### 18.2 Admin Boundaries

Admin manages operations. Admin does NOT:
- Change system roles or permissions
- Manage platform-level security policies
- Configure integrations or background jobs
- Access governance-level controls

---

## 19. Super Admin Workspace

### 19.1 Current Implementation

18 pages under `/super-admin/*`:

| Page | Purpose |
|---|---|
| SuperAdminOverviewPage | Platform overview |
| SuperAdminAttentionPage | Platform attention items |
| SuperAdminPulsePage | Platform metrics |
| SuperAdminHealthPage | System health |
| SuperAdminRolesPage | Role management |
| SuperAdminAccessPage | Access governance |
| SuperAdminOrgGovernancePage | Organization governance |
| SuperAdminTrustGovernancePage | Trust governance |
| SuperAdminIntelligencePage | Ecosystem intelligence |
| SuperAdminSecurityPage | Security overview |
| SuperAdminAuditPage | Governance audit log |
| SuperAdminConfigPage | Platform configuration |
| SuperAdminTaxonomyPage | Taxonomy governance |
| SuperAdminFeatureControlsPage | Feature flags |
| SuperAdminSessionControlPage | Session management |
| SuperAdminNotificationConfigPage | Notification configuration |
| SuperAdminIntegrationConfigPage | Integration settings |
| SuperAdminBackgroundJobsPage | Background job monitoring |

### 19.2 Super Admin Boundaries

Super Admin governs. Super Admin does NOT:
- Perform routine moderation (that's Moderator/Admin)
- Process individual verification requests (that's Verification Officer)
- Manage individual user accounts (that's Admin)

---

## 20. Authentication & Authorization

### 20.1 Authentication Flow

```
Register → BCrypt hash → Save User → Generate JWT + Refresh Token
Login → AuthenticationManager.authenticate → Generate JWT + Refresh Token
JWT Validation → JwtAuthenticationFilter → Extract email → Load UserDetails → Set SecurityContext
Token Refresh → Extract email from refresh token → Generate new JWT
Logout → Add token to revokedTokens (in-memory) → Audit log
```

### 20.2 JWT Configuration

- **Secret:** `${JWT_SECRET}` with hardcoded fallback (SECURITY RISK)
- **Access Token TTL:** 24 hours (86400000ms)
- **Refresh Token TTL:** 7 days (604800000ms)
- **Claims:** subject (email), role (UserRole.name())

### 20.3 Authorization Layers

| Layer | Implementation | Status |
|---|---|---|
| URL-level | `SecurityConfig` requestMatchers | **Implemented** |
| Method-level | `@PreAuthorize` | **Partial** — 4 controllers only |
| RBAC Service | `RbacService.hasPermission()` | **Exists but unused** |
| Ownership | `getAndValidateOwnership()` | **Partial** — Opportunity only |

### 20.4 Security Configuration

```java
/api/v1/auth/**         → permitAll
/api/v1/public/**       → permitAll
/api/v1/admin/**        → ADMIN, SUPER_ADMIN
/api/v1/organization/** → ORGANIZATION, ORGANIZATION_ADMIN, ORGANIZATION_OWNER, ADMIN, SUPER_ADMIN
/api/v1/verify/**       → ORGANIZATION, ORGANIZATION_ADMIN, ORGANIZATION_OWNER, ADMIN, SUPER_ADMIN
/api/v1/verification-officer/** → VERIFICATION_OFFICER, ADMIN, SUPER_ADMIN
/api/v1/moderation/**   → MODERATOR, ADMIN, SUPER_ADMIN
/api/v1/trust/**        → VERIFICATION_OFFICER, MODERATOR, ADMIN, SUPER_ADMIN
/**                     → authenticated
```

---

## 21. Security Principles

### 21.1 Current Implementation

| Principle | Status |
|---|---|
| JWT authentication | **Implemented** |
| BCrypt password hashing | **Implemented** |
| Stateless sessions | **Implemented** |
| CORS configuration | **Implemented** — localhost only |
| CSRF disabled | **Implemented** — acceptable for stateless JWT |
| HTTPS enforcement | **NOT implemented** |
| Rate limiting | **NOT implemented** |
| Account lockout | **NOT implemented** |
| Security headers | **NOT implemented** |
| Token revocation | **Partial** — in-memory only |

### 21.2 Known Security Issues

| Issue | Severity | Location |
|---|---|---|
| Hardcoded JWT secret fallback | CRITICAL | `application.yml:24` |
| In-memory token revocation | HIGH | `AuthService.java:41` |
| In-memory password reset tokens (no expiry) | HIGH | `AuthService.java:42` |
| AccessDeniedException → 500 | HIGH | `GlobalExceptionHandler` |
| No rate limiting on login | MEDIUM | — |
| No account lockout | MEDIUM | — |
| No security headers | MEDIUM | — |
| Email verification link never validated | HIGH | `AuthService.java:78` |
| File upload path traversal risk | MEDIUM | `FileStorageService` |
| Seed passwords known | HIGH | `V2__seed_data.sql` comments |

---

## 22. Data Model Principles

### 22.1 Current Implementation

- **ORM:** JPA/Hibernate with Lombok
- **Database:** PostgreSQL with Flyway migrations (V1-V9, V4 missing)
- **Schema management:** Flyway + `ddl-auto: update` (CONFLICT — should be `none`)
- **ID strategy:** BIGSERIAL (sequential)
- **Cascade:** Hard delete with CASCADE
- **Soft delete:** NOT implemented
- **Optimistic locking:** NOT implemented
- **Audit:** `AuditLog` entity (limited usage)

### 22.2 Database Tables (24 total)

V1: users, seeker_profiles, organization_profiles, skills, education, interests, opportunities, applications, saved_opportunities, audit_logs, notifications, reports, verification_requests

V3: experiences, career_goals

V5: user_documents

V6: (ALTER) seeker_profiles — profile_visibility, notification_preference

V7: permissions, roles, role_permissions, user_roles, organization_members, organization_invitations, opportunity_status_history, application_status_history, moderation_actions, verification_documents, deadline_reminders, risk_signals, platform_config

V8: (seed) 2 more organizations

V9: escalations, appeals

---

## 23. Event / Job Architecture

### 23.1 Current Implementation

| Job | Implementation | Schedule |
|---|---|---|
| Deadline processing | `DeadlineEngineService.processDeadlines()` | Every hour (`fixedRate=3600000`) |
| Mark expired | Called by deadline engine | Hourly |
| Mark closing_soon | Called by deadline engine | Hourly |
| Send deadline reminders | Creates `DeadlineReminder` records | Hourly |

### 23.2 Missing Capabilities

- No job queue (Redis/RabbitMQ)
- No retry logic
- No dead-letter handling
- No job failure alerts
- No idempotency guarantees
- No distributed job execution

---

## 24. Data Quality

### 24.1 Current Implementation

| Capability | Status |
|---|---|
| Duplicate title detection | **Partial** — `findByTitleContainingIgnoreCase` |
| Shortened URL detection | **Implemented** — bit.ly, tinyurl, t.co |
| Unverified org flagging | **Implemented** |
| Repeated report flagging | **Implemented** — count ≥ 3 |
| Missing eligibility detection | **NOT implemented** |
| Stale opportunity detection | **NOT implemented** |
| Content quality scoring | **NOT implemented** |

---

## 25. Freshness / Deadline Intelligence

### 25.1 Current Implementation

- `DeadlineEngineService` runs hourly
- Marks opportunities as EXPIRED when deadline passes
- Marks opportunities as CLOSING_SOON when within threshold (default 7 days)
- Creates `DeadlineReminder` records for configured intervals (30,14,7,3,1 days)
- Configuration stored in `platform_config` table

### 25.2 Missing Capabilities

- Deadline reminder emails not sent (only DB records created)
- No stale opportunity detection (90-day threshold)
- No source freshness tracking
- No re-verification triggers

---

## 26. Observability

### 26.1 Current Implementation

| Capability | Status |
|---|---|
| Basic analytics | **Implemented** — `AnalyticsService` |
| Dashboard stats | **Implemented** — user/opp/app counts |
| Opportunity analytics | **Implemented** — by status |
| Report analytics | **Implemented** — by status |
| Platform activity (7-day) | **Implemented** — new users/opp/apps |
| Health endpoint | **Implemented** — `/public/health` |
| Application monitoring | **NOT implemented** |
| Log aggregation | **NOT implemented** |
| Metrics collection | **NOT implemented** |
| Alerting | **NOT implemented** |

---

## 27. Integration Architecture

### 27.1 Current Integrations

| Integration | Status |
|---|---|
| PostgreSQL | **Implemented** |
| SMTP Email | **Partial** — verification + application only |
| Local file storage | **Implemented** |
| JWT authentication | **Implemented** |

### 27.2 Planned Integrations

| Integration | Status |
|---|---|
| Redis | **Planned** — caching, token revocation, sessions |
| Elasticsearch | **Planned** — full-text search |
| Firebase Cloud Messaging | **Planned** — web push |
| Twilio (SMS) | **Planned** |
| WhatsApp Business API | **Planned** |
| Stripe/Paystack | **Planned** — payments |
| S3/Cloud Storage | **Planned** — file storage |

---

## 28. Frontend Architecture

### 28.1 Current Implementation

- **Framework:** React 18 + TypeScript
- **Build:** Vite
- **Styling:** Tailwind CSS
- **Routing:** React Router v6
- **State:** React Context (AuthContext, ThemeContext, NotificationContext)
- **API:** Axios with interceptors
- **i18n:** Manual (EN/SW partial)

### 28.2 Route Structure

| Route Group | Count | Auth |
|---|---|---|
| Public | 14 routes | No |
| Seeker | 11 routes | Yes |
| Organization | 7 routes | Yes + role gate |
| Admin | 10 routes | Yes + role gate |
| Trust | 9 routes | Yes + role gate |
| Super Admin | 18 routes | Yes + role gate |

### 28.3 Component Library

| Component | Purpose |
|---|---|
| Badge | Status indicators |
| Button | Actions |
| Card | Content containers |
| ConfirmDialog | Confirmation modals |
| RejectDialog | Rejection with reason |
| InputDialog | Text input modals |
| Pagination | Page navigation |
| PageStates | Loading, empty, error states |
| ProtectedRoute | Auth guard |
| RoleGate | Role-based route protection |
| Layout, Sidebar, TrustLayout, SuperAdminLayout | Navigation shells |

---

## 29. Backend Architecture

### 29.1 Package Structure

```
tdop/
├── entity/           22 JPA entities
├── entity/enums/     11 enums
├── repository/       18+ repositories
├── service/          21 services
├── controller/       32 controllers (165 endpoints)
├── config/           SecurityConfig, JwtUtil, JwtAuthenticationFilter, etc.
├── dto/              Request/Response DTOs
├── exception/        GlobalExceptionHandler, custom exceptions
├── rbac/             RbacService
├── audit/            AuditLogService
├── matching/         OpportunityMatchingService
├── notification/     NotificationService, email/EmailService
├── audit/            AuditLogService
```

### 29.2 Key Services

| Service | Responsibility |
|---|---|
| AuthService | Login, register, refresh, logout, password reset |
| UserService | User CRUD, suspend, role change |
| OpportunityService | Opportunity CRUD |
| OpportunityLifecycleService | State machine, transitions, history |
| ApplicationService | Application CRUD, status tracking |
| ModerationService | Moderation actions |
| VerificationOfficerService | Verification review |
| ReportInvestigationService | Report workflow |
| EscalationService | Escalation management |
| AppealService | Appeal management |
| AntiFraudService | Risk signal detection |
| DeadlineEngineService | Scheduled deadline processing |
| AnalyticsService | Platform metrics |
| PlatformConfigService | Configuration CRUD |
| DashboardService | Seeker dashboard data |
| RbacService | Role/permission management |
| NotificationService | In-app notifications |
| EmailService | SMTP email sending |

---

## 30. Database Principles

### 30.1 Current State

- PostgreSQL 16
- Flyway migrations (V1-V9)
- `ddl-auto: update` alongside Flyway (CONFLICT)
- No connection pool configuration
- No backup strategy documented

### 30.2 Target State

- `ddl-auto: none` — Flyway exclusively manages schema
- Configured HikariCP pool
- Automated backups
- Point-in-time recovery
- Read replicas (future)

---

## 31. Current vs Target Architecture

### 31.1 Authentication

| Aspect | Current | Target |
|---|---|---|
| Token storage | In-memory | Redis |
| Token revocation | In-memory | Redis/DB |
| Password reset | In-memory, no expiry | DB with TTL |
| Rate limiting | None | bucket4j/spring-cloud |
| Account lockout | None | After 5 failures |
| Email verification | Link generated, never validated | Full flow with DB tokens |

### 31.2 Search

| Aspect | Current | Target |
|---|---|---|
| Engine | SQL LIKE | Elasticsearch / pg_trgm |
| Ranking | None | Relevance + recency + popularity |
| Facets | Basic filters | Full faceted search |
| Autocomplete | None | Trie-based |

### 31.3 Notifications

| Aspect | Current | Target |
|---|---|---|
| In-app | Implemented | Implemented |
| Email | Partial | Full |
| SMS | None | Twilio |
| WhatsApp | None | WhatsApp Business API |
| Push | None | Firebase Cloud Messaging |

### 31.4 Matching

| Aspect | Current | Target |
|---|---|---|
| Algorithm | Rule-based scoring | ML-based + collaborative filtering |
| Factors | Skills, interests, education | + behavior, popularity, recency |
| Explainability | None | "Why am I seeing this?" |
| Feedback | None | Relevant/irrelevant marking |

---

## 32. Deferred/Future Capabilities

| Capability | Status |
|---|---|
| Outcome tracking | Deferred |
| Subscription engine | Deferred |
| WhatsApp integration | Deferred |
| SMS notifications | Deferred |
| Push notifications | Deferred |
| Payment processing | Deferred |
| Elasticsearch | Deferred |
| Redis caching | Deferred |
| ML matching | Deferred |
| Collaborative filtering | Deferred |
| Rate limiting | Deferred |
| E2E testing | Deferred |
| Load testing | Deferred |
| Full accessibility (WCAG 2.2 AA) | Deferred |
| Mobile app | Future |

---

## 33. Implementation Governance

### 33.1 Development Principles

```
UNDERSTAND → AUDIT → PLAN → IMPLEMENT → TEST → VERIFY → HARDEN → DOCUMENT
```

Never:
```
GUESS → REWRITE → BREAK → PATCH
```

### 33.2 Shared Areas Requiring Coordination

- Database schema and migrations
- Authentication architecture
- RBAC
- User, Organization, Opportunity, Application models
- Notification model
- Audit model
- Core API contracts
- Global routing
- Shared UI components
- Security architecture

### 33.3 Priority System

| Priority | Meaning |
|---|---|
| P0 | Core / Blocking — platform cannot launch without it |
| P1 | Important Production — required for serious production |
| P2 | Enhancement — important but not blocking initial production |
| P3 | Future — growth, advanced intelligence, expansion |

---

## 34. Recommended Next Implementation Phase

Based on repository reality, the logical implementation order:

### Phase 1 — Security Hardening (P0)
1. Remove hardcoded JWT secret fallback
2. Set `ddl-auto: none`
3. Handle `AccessDeniedException` → 403
4. Add account lockout after failed attempts
5. Add email verification validation
6. Add password reset token expiration
7. Sanitize file upload filenames
8. Add rate limiting

### Phase 2 — Authorization Tightening (P0)
1. Add ownership checks to Application, Report controllers
2. Wire up RbacService for permission-based checks
3. Add @PreAuthorize to all Admin controllers

### Phase 3 — Infrastructure (P0)
1. Add Redis for token revocation + caching
2. Add HTTPS enforcement
3. Add security headers
4. Add health check with DB ping

### Phase 4 — Search & Discovery (P1)
1. Add pg_trgm or Elasticsearch
2. Implement full-text search
3. Add search ranking

### Phase 5 — Matching & Recommendations (P1)
1. Improve matching algorithm
2. Add popularity-based recommendations
3. Add recommendation explanations

### Phase 6 — Notifications (P1)
1. Add deadline reminder emails
2. Add application status change emails
3. Add escalation alert emails

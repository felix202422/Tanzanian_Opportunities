# TDOP — Product Requirements Document

## Tanzania Digital Opportunity Platform

| | |
|---|---|
| **Project** | TDOP — Tanzania Digital Opportunity Platform |
| **Document** | `README_PRD.md` |
| **Status** | Active Development |

---

## 1. Product Definition

### 1.1 What is TDOP?

TDOP is a **trusted opportunity discovery and progress platform** for Tanzania.

It is not a job board. It is a structured ecosystem connecting opportunity seekers with opportunity providers, built around the core journey:

```
PROFILE → DISCOVER → UNDERSTAND → MATCH → TRUST → APPLY → TRACK → OUTCOME
```

### 1.2 The Problem

Opportunity information in Tanzania is fragmented across government websites, company sites, NGOs, universities, social media, WhatsApp groups, job boards, and recruitment agencies. This creates:

- **Discoverability gaps** — suitable opportunities missed
- **Trust deficits** — legitimacy uncertain
- **Information quality issues** — incomplete, outdated, misleading listings
- **Relevance problems** — poor matching to skills, education, location
- **Application complexity** — unclear eligibility, requirements, methods
- **Deadline misses** — legitimate opportunities lost

### 1.3 The Solution

TDOP provides:

- Structured opportunity listings with eligibility, requirements, and deadlines
- Organization verification with evidence-based trust signals
- Profile-based matching and recommendations
- Application tracking and status management
- Moderation and anti-fraud protections
- Multi-channel notifications (in-app, email; SMS, WhatsApp, push planned)

---

## 2. Target Users

### 2.1 Opportunity Seekers

| Group | Needs |
|---|---|
| Students | Scholarships, internships, competitions, training |
| Graduates / Job Seekers | Jobs, graduate programs, fellowships |
| Professionals | Employment, certifications, training, conferences |
| Entrepreneurs | Grants, financing, competitions, accelerators |
| SMEs | Tenders, procurement, training, partnerships |
| Researchers | Research grants, fellowships, innovation challenges |

### 2.2 Opportunity Providers

| Type | Examples |
|---|---|
| Companies | Private sector employers |
| NGOs | Development organizations |
| Government | Public institutions, government programs |
| Universities / Colleges | Academic programs, research |
| Financial Institutions | Grants, loans, funding |
| Startups | Incubators, accelerators |
| Investors | Investment opportunities |

---

## 3. Roles and Responsibilities

### 3.1 Platform Roles

| Role | Description |
|---|---|
| `SEEKER` | Individual looking for opportunities. Can browse, search, save, apply, track. |
| `ORGANIZATION` | Organization account. Can create profiles, post opportunities, manage applications. |
| `ORGANIZATION_ADMIN` | Organization owner/admin. Full org management including team, settings, publishing. |
| `ORGANIZATION_MEMBER` | Organization team member. Limited access based on assigned responsibilities. |
| `VERIFICATION_OFFICER` | Reviews organization verification requests. Approves/rejects with evidence. |
| `MODERATOR` | Reviews opportunities for quality, policy compliance. Approves/rejects/suspends. |
| `ADMIN` | Platform operations. Manages users, organizations, reports, analytics, configuration. |
| `SUPER_ADMIN` | Platform governance. Roles, permissions, security, taxonomy, integrations, system health. |

### 3.2 Role Boundaries

**ADMIN** = Platform Operations & Management Center
- Operate, Manage, Review, Resolve, Monitor
- User management, organization oversight, moderation, reports, analytics, configuration

**SUPER_ADMIN** = Platform Control Center
- Govern, Configure, Secure, Control, Audit
- Roles & permissions, access governance, security, taxonomy, integrations, system health

**ADMIN does NOT equal SUPER_ADMIN.** Admin operates the platform. Super Admin governs it.

---

## 4. Opportunity Ecosystem

### 4.1 Supported Types

TDOP uses a **common opportunity foundation** with type-specific contracts:

**Employment**
- Jobs, Internships, Graduate opportunities, Apprenticeships, Part-time, Remote, International

**Education**
- Scholarships, Fellowships, Training, Certifications, Courses, Academic programs

**Entrepreneurship & Finance**
- Grants, Loans, Financing, Investment, Startup programs, Accelerators, Incubators

**Public & Development**
- Government programs, NGO opportunities, Youth programs, Volunteer opportunities

**Professional & Innovation**
- Research, Innovation challenges, Competitions, Conferences, Events, ICT opportunities

**Business**
- Tenders, Procurement, Partnerships, Supplier opportunities

### 4.2 Opportunity Type Contract

Each opportunity type defines:

```
Opportunity Type
      ↓
Required Fields
      ↓
Optional Fields
      ↓
Eligibility Rules
      ↓
Allowed Actions
      ↓
Lifecycle
      ↓
Required Documents
      ↓
Deadline Rules
      ↓
Trust Requirements
      ↓
Application/Action Mechanism
      ↓
Notification Events
      ↓
Outcome States
      ↓
Analytics Events
```

This allows TDOP to expand without architectural rewrites for every category.

### 4.3 Common Opportunity Model

```
Opportunity
├── Organization
├── Title
├── Type
├── Category
├── Description
├── Eligibility
├── Audience
├── Geography
├── Deadline
├── Source
├── Provenance
├── Documents
├── Action Method
├── Verification Status
├── Status
├── Created At
└── Updated At
```

### 4.4 Action Engine

Not every opportunity is an "application." TDOP supports type-specific actions:

| Type | Action Flow |
|---|---|
| JOB | Apply → Track → Outcome |
| SCHOLARSHIP | Apply → Track → Decision |
| TENDER | Review Requirements → Prepare Bid → Submit → Track → Award |
| TRAINING | Register → Attend → Complete |
| EVENT | Register → Attend |
| RESEARCH | Prepare Proposal → Submit → Track → Outcome |

---

## 5. Core User Journeys

### 5.1 Seeker Journey

```
REGISTER → COMPLETE PROFILE → DISCOVER → SEARCH → VIEW → CHECK ELIGIBILITY
→ SAVE → APPLY → TRACK → RECEIVE NOTIFICATIONS → OUTCOME → DISCOVER AGAIN
```

### 5.2 Organization Journey

```
REGISTER → CREATE PROFILE → SUBMIT VERIFICATION → CREATE OPPORTUNITY
→ DRAFT → SUBMIT FOR REVIEW → PUBLISHED → MANAGE APPLICATIONS → TEAM
```

### 5.3 Trust Journey

```
LOGIN → ATTEND TO QUEUE → REVIEW VERIFICATION/MODERATION/REPORTS
→ TAKE ACTION → ESCALATE IF NEEDED → APPEALS
```

### 5.4 Admin Journey

```
LOGIN → DASHBOARD → MANAGE USERS → MANAGE ORGANIZATIONS → MODERATE
→ REVIEW REPORTS → CONFIGURE PLATFORM → ANALYTICS → AUDIT
```

### 5.5 Super Admin Journey

```
LOGIN → OVERVIEW → ATTEND TO PLATFORM → GOVERN ROLES → MANAGE ACCESS
→ SECURITY → TAXONOMY → CONFIGURE → INTELLIGENCE → AUDIT
```

---

## 6. Platform Engines

### 6.1 Engine Inventory

| Engine | Purpose | Status |
|---|---|---|
| Discovery Engine | Search, filter, sort, browse | **Partial** — SQL LIKE search only |
| Trust Engine | Organization verification, evidence, audit | **Implemented** |
| Access Engine | RBAC, permissions, authorization | **Partial** — URL-level only, RBAC service unused |
| Opportunity Engine | Lifecycle management, publishing, expiry | **Implemented** |
| Action/Application Engine | Applications, status tracking, history | **Implemented** |
| Outcome Engine | Post-application outcomes, success tracking | **Planned** |
| Intelligence Engine | Matching, recommendations, eligibility scoring | **Partial** — basic rule-based matching |
| Governance Engine | Roles, permissions, platform policies | **Partial** — RBAC service exists but not wired |
| Event & Job Layer | Scheduled tasks, background processing | **Partial** — deadline engine only |
| Data Quality Layer | Validation, duplicate detection, completeness | **Partial** — basic anti-fraud only |
| Observability | Monitoring, health checks, metrics | **Partial** — basic analytics only |
| Distribution Engine | Notifications, email, channels | **Partial** — in-app + email only |
| Subscription Engine | User preference-based opportunity delivery | **Planned** |
| Communication Center | Multi-channel notification routing | **Planned** |
| Integration Hub | External API connections | **Planned** |
| Source/Ingestion Engine | Opportunity import from external sources | **Planned** |
| Freshness Engine | Stale opportunity detection, re-verification | **Planned** |
| Deadline Intelligence | Deadline reminders, expiry management | **Implemented** |
| Watchlist | Saved opportunity tracking with alerts | **Partial** — save exists, alerts planned |
| Personalized Feed | Profile-based opportunity recommendations | **Partial** — basic matching only |
| Opportunity Comparison | Side-by-side comparison | **Implemented** |

### 6.2 Implementation Status Legend

| Status | Meaning |
|---|---|
| **Implemented** | Working in production codebase |
| **Partial** | Exists but incomplete or limited |
| **Planned** | Designed but not yet coded |
| **Future** | Visionary, no design yet |

---

## 7. Trust Model

### 7.1 Trust Principles

- Evidence-based verification (not self-declaration)
- Auditable decisions
- Provenance tracking
- Source credibility assessment
- Duplicate detection
- Suspicious content flagging
- Appeal mechanisms

### 7.2 Organization Verification Flow

```
ORGANIZATION
      ↓
SUBMIT EVIDENCE (documents)
      ↓
REVIEW (verification officer)
      ↓
REQUEST INFORMATION (if needed)
      ↓
APPROVE / REJECT
      ↓
RE-VERIFY (annual / trigger-based)
      ↓
SUSPEND / EXPIRE when necessary
```

### 7.3 Opportunity Trust Signals

Every opportunity should answer:
- WHO published it?
- IS the organization verified?
- WHEN was it last checked?
- WHEN does it close?
- HOW do I apply?
- WHAT evidence supports it?

---

## 8. Discovery Architecture

### 8.1 Discovery Components

| Component | Status |
|---|---|
| Search (keyword) | **Partial** — SQL LIKE |
| Filter (category, location, type) | **Implemented** |
| Sort | **Implemented** |
| Pagination | **Implemented** |
| Saved Opportunities | **Implemented** |
| Comparison | **Implemented** |
| Recommendations | **Partial** — basic matching |
| Deadline Awareness | **Implemented** |
| Personalized Feed | **Partial** — basic matching |

### 8.2 Search Filters

- Keyword, Category, Location, Deadline
- Education level, Skills, Experience
- Organization, Remote/On-site, Funding
- Opportunity type

---

## 9. Notifications

### 9.1 Notification Events

| Event | Status |
|---|---|
| Application submitted | **Implemented** |
| Application status changed | **Implemented** |
| Deadline approaching | **Partial** — DB record created, email not sent |
| New opportunity matching interests | **Planned** |
| Verification result | **Implemented** |
| Moderation action | **Implemented** |
| Security notification | **Planned** |
| Platform announcement | **Planned** |

### 9.2 Distribution Channels

| Channel | Status |
|---|---|
| In-App | **Implemented** |
| Email (SMTP) | **Partial** — verification + application only |
| SMS | **Planned** |
| WhatsApp | **Planned** |
| Push (Web) | **Planned** |
| Push (Mobile) | **Future** |

### 9.3 Subscription Model (Target)

```text
Types: Jobs, Internships, Scholarships, Tenders, Training
Categories: ICT, Business, etc.
Location: Tanzania, Remote, International
Frequency: Instant, Daily Digest, Weekly
Channels: In-App, Email, SMS, WhatsApp, Push
```

---

## 10. Admin Workspace

ADMIN = **Platform Operations & Management Center**

Operational areas:

- Overview / Dashboard
- Users (list, suspend, role changes)
- Organizations (list, stats, verification status)
- Opportunities (moderation queue, publish/reject/suspend)
- Applications / Actions
- Reports (investigate, resolve, dismiss)
- Moderation (approve, reject, suspend, request info)
- Verification (review org verification requests)
- Analytics (dashboard stats, opportunity analytics, report analytics)
- Platform Health (configuration, settings)
- Audit Log (security events, role changes, actions)

---

## 11. Super Admin Workspace

SUPER_ADMIN = **Platform Control Center**

Focus areas:

- Roles & Permissions (create, assign, manage)
- Access Governance (privileged access, user-role assignments)
- Platform Policies (feature flags, session control)
- Organization Governance (taxonomy, categories)
- Trust Governance (verification/moderation oversight)
- Security Center (security overview, audit, sessions)
- Platform Configuration (notification config, integration config)
- System Operations (background jobs, health)
- Ecosystem Intelligence (analytics, activity, trends)

---

## 12. Architectural Principles

### 12.1 Backend Authoritative

Frontend is not a security boundary. All authorization enforced server-side.

### 12.2 RBAC Model

```
ROLE → PERMISSION → RESOURCE → ACTION → SCOPE
```

### 12.3 Organization Isolation

Organization A must never access Organization B's protected resources.

### 12.4 Evidence-Based Trust

Verification supported by evidence and auditable decisions.

### 12.5 No Fabricated Data

Production UI must not display fabricated metrics, testimonials, organizations, or analytics.

### 12.6 One Source of Truth

Avoid duplicate APIs, dashboards, domain models, business rules, workflows, routes, services.

### 12.7 Reuse Before Rebuild

Existing working functionality should be reused unless there is a demonstrated architectural reason to replace it.

### 12.8 Domain-Driven Architecture

Clear separation: Identity, Organization, Trust, Opportunity, Action, Outcome, Discovery, Communication, Governance, Intelligence.

---

## 13. Documentation Status Model

| Status | Meaning |
|---|---|
| **CURRENT** | Exists in current repository implementation |
| **PARTIAL** | Partially implemented, needs completion |
| **PLANNED** | Designed but not yet coded |
| **FUTURE** | Visionary, no design yet |
| **DEFERRED** | Intentionally postponed |

Where useful, wording such as "target architecture" and "current repository implementation" distinguishes future from present.

---

## 14. Related Documents

| Document | Purpose |
|---|---|
| `README.md` | Repository overview, quick start |
| `Docs/TDOP_MASTER_SPEC.md` | Architecture and implementation specification |
| `Docs/IMPLEMENTATION & FUTURE ROADMAP.md` | Detailed roadmap and future expansion guide |
